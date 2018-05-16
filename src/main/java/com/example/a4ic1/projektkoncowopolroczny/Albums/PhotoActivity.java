package com.example.a4ic1.projektkoncowopolroczny.Albums;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.util.Pair;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.example.a4ic1.projektkoncowopolroczny.Imaging;
import com.example.a4ic1.projektkoncowopolroczny.MainActivity;
import com.example.a4ic1.projektkoncowopolroczny.Networking;
import com.example.a4ic1.projektkoncowopolroczny.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class PhotoActivity extends AppCompatActivity {

    private ImageView fullScreen;
    private ImageView delPhoto;
    private ImageView edit;
    private ListView listView;
    private DrawerLayout drawerLayout;
    private RelativeLayout relativeLayout;
    private RelativeLayout linearLayout;
    private PreviewText previewText;
    private PreviewText oldpreviewText;
    private int oldx=0;
    private int oldy=0;
    private int x=0;
    private int y=0;
    private File newfile = null;

    private ArrayList<String> lista = new ArrayList<>();
    private ArrayList<Integer> listaint = new ArrayList<>();
    private ArrayList<Pair<Integer,Integer>> xy = new ArrayList<>();


    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(newfile!=null)
            newfile.delete();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data.getExtras().containsKey("font")) {
            String tfname = data.getExtras().getString("font");
            Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/" + tfname);
            String tekst = data.getExtras().getString("tekst");
            int color1 = data.getExtras().getInt("color1");
            int color2 = data.getExtras().getInt("color2");
            int height = data.getExtras().getInt("height");
            int width = data.getExtras().getInt("width");
            Log.e("heh",""+height);
            Log.e("heh",""+width);
            previewText = new PreviewText(PhotoActivity.this, tf, tekst,color1,color2);
            previewText.setBackgroundColor(Color.TRANSPARENT);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width,height);
            previewText.setLayoutParams(layoutParams);
            previewText.setSelected(false);
            linearLayout.addView(previewText);
            xy.add(new Pair<>(0,0));
            Log.e("heh",""+linearLayout.getChildCount());

            previewText.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    switch (event.getAction()){

                        case MotionEvent.ACTION_DOWN:
                            oldx = Math.round(event.getX());
                            oldy = Math.round(event.getY());
                            break;
                        case MotionEvent.ACTION_MOVE:
                            v.setBackgroundColor(Color.WHITE);
                            x=Math.round(event.getX());
                            y=Math.round(event.getY());
                            v.offsetLeftAndRight(x-oldx);
                            v.offsetTopAndBottom(y-oldy);
                            break;
                        case MotionEvent.ACTION_UP:
                            //xy.remove(linearLayout.indexOfChild(v)-2);
                            //xy.add(linearLayout.indexOfChild(v)-2,new Pair<Integer, Integer>(Math.round(v.getX()),Math.round(v.getY())));
                            v.setBackgroundColor(Color.TRANSPARENT);
                            break;


                    }
                    return true;
                }
            });



        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_photo);
        getSupportActionBar().hide();

        Bundle bundle = getIntent().getExtras();
        final String imgPath = bundle.getString("String");



        listView = (ListView) findViewById(R.id.listView);
        drawerLayout =(DrawerLayout) findViewById(R.id.drawer_layout);
        fullScreen = (ImageView) findViewById(R.id.fullScreen);
        delPhoto = (ImageView) findViewById(R.id.deletePhoto);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        edit = (ImageView) findViewById(R.id.edit);
        linearLayout = (RelativeLayout) findViewById(R.id.linearLayout);
        linearLayout.setDrawingCacheEnabled(true);
        fullScreen.setScaleType(ImageView.ScaleType.CENTER_CROP);
        fullScreen.setImageDrawable(Drawable.createFromPath(imgPath));

        delPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(PhotoActivity.this);
                alert.setTitle("Uwaga!");
                alert.setMessage("Usunąć zdjęcie?");
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //wyswietl which
                        MainActivity newmain = new MainActivity();
                        newmain.DeleteFile(imgPath);
                    }

                });

                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        //wyswietl which
                    }
                });
                alert.show();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        lista.add("jeden");
        lista.add("dwa");
        lista.add("trzy");

        listaint.add(R.drawable.box);
        listaint.add(R.drawable.edit);
        listaint.add(R.drawable.checkedbox);

        DrawerList adapter = new DrawerList(
                PhotoActivity.this,
                R.layout.drawer_list,
                lista,
                listaint);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Intent intent = new Intent(PhotoActivity.this,FontsActivity.class);
                        startActivityForResult(intent,400);
                        break;
                    case 1:
                        boolean networkStatus = Networking.NetworkStatus(PhotoActivity.this);
                        if(networkStatus){
                            Bitmap bitmap = linearLayout.getDrawingCache(true);
                            byte[] bytes = Imaging.ConvertToBytetab(bitmap);
                            new UploadFoto(PhotoActivity.this,bytes).execute();
                        }else{
                            AlertDialog.Builder alert = new AlertDialog.Builder(PhotoActivity.this);
                            alert.setTitle("Uwaga!");
                            alert.setCancelable(false);
                            alert.setMessage("Nie działa ci internet");
                            alert.setNeutralButton("OK, nie działa", null).show();
                        }
                        break;
                    case 2:
                        Intent share = new Intent(Intent.ACTION_SEND);
                        share.setType("image/jpeg");
                        Date date = new Date();
                        Log.e("heh",""+date.getDate());
                        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                        Bitmap bitmap = linearLayout.getDrawingCache(true);
                        //Bitmap bitmap = ((BitmapDrawable) fullScreen.getDrawable()).getBitmap();

                        try {
                            newfile = File.createTempFile("tymczasowy",".jpg",file);
                            FileOutputStream fs = new FileOutputStream(newfile);
                            byte[] bytes = Imaging.ConvertToBytetab(bitmap);
                            fs.write(bytes);
                            fs.close();
                            Log.e("heh",newfile.getPath());
                            share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+newfile.getPath())); //pobierz plik i podziel się nim:
                            startActivity(Intent.createChooser(share, "Podziel się plikiem!"));

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }



                        break;
                }
            }
        });
    }
}
