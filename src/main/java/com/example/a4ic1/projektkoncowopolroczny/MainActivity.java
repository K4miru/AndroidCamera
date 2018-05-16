package com.example.a4ic1.projektkoncowopolroczny;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Network;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.a4ic1.projektkoncowopolroczny.Albums.ChooseAlbumsActivity;
import com.example.a4ic1.projektkoncowopolroczny.Albums.DownloadFoto;
import com.example.a4ic1.projektkoncowopolroczny.Albums.GetElement;
import com.example.a4ic1.projektkoncowopolroczny.Albums.UploadFoto;
import com.example.a4ic1.projektkoncowopolroczny.Camera.CameraActivity;
import com.example.a4ic1.projektkoncowopolroczny.Camera.MakePhotoActivity;
import com.example.a4ic1.projektkoncowopolroczny.Collage.ChooseCollageActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private LinearLayout viewPager;
    private LinearLayout scrollView;
    private RelativeLayout relativeLayout;
    File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

    @Override
    public void onResume() {
        super.onResume();
        boolean networkStatus = Networking.NetworkStatus(MainActivity.this);
        if(networkStatus){
            Prefs.listDrawable.clear();
            viewPager.removeAllViews();
            Prefs.setNetworkActivity(false);
            new DownloadFoto(viewPager).execute();
        }else{
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setTitle("Uwaga!");
            alert.setCancelable(false);
            alert.setMessage("Nie działa ci internet");
            alert.setNeutralButton("OK, nie działa", null).show();
        }

        // Always call the superclass method first
        //Log.e("hehe","onResume");
    }
    @Override
    public void onStop() {
        super.onStop();  // Always call the superclass method first
        //Log.e("hehe","onStop");
    }
    @Override
    public void onRestart() {
        super.onRestart();  // Always call the superclass method first
        //Log.e("hehe","onRestart");
    }
    @Override
    public void onStart() {
        super.onStart();  // Always call the superclass method first
        //Log.e("hehe","onStart");
    }
    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first
        //Log.e("hehe","onPause");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        Display display = getWindowManager().getDefaultDisplay();
        Prefs.setX(display.getWidth());
        Prefs.setY(display.getHeight());

        setContentView(R.layout.activity_main);
        Random ran = new Random();
        int x = ran.nextInt(6);
        Log.e("heh",""+x);

        final File dir = new File(file, "Kamil Tracz 2");
        File dir1 = new File(dir, "Miejsca");
        File dir2 = new File(dir, "Osoby");
        File dir3 = new File(dir, "Rzeczy");
        if (!dir.exists())
            dir.mkdir();
        if(!dir1.exists())
            dir1.mkdir();
        if(!dir2.exists())
            dir2.mkdir();
        if(!dir3.exists())
            dir3.mkdir();

        Prefs.setMyPref(dir);


        imageView1 =(ImageView) findViewById(R.id.imageView1);
        imageView2 =(ImageView) findViewById(R.id.imageView2);
        imageView3 =(ImageView) findViewById(R.id.imageView3);
        imageView4 =(ImageView) findViewById(R.id.imageView4);
        viewPager = (LinearLayout) findViewById(R.id.viewPager);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        scrollView = (LinearLayout) findViewById(R.id.scrollViewNetwork);
        relativeLayout.setVisibility(View.INVISIBLE);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.e("heh",file.getPath());
                File[] files = ListFolders(dir);
                Intent intent = new Intent(MainActivity.this,ChooseAlbumsActivity.class);
                intent.putExtra("key", files.length);
                for(int i=0;i<files.length;i++){
                    //Log.e("heh", String.valueOf(files[i]));
                    intent.putExtra("key"+i+"", String.valueOf(files[i].getName()));
                }
                startActivity(intent);

            }
        });

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.e("heh","działa");
                Log.e("heh",Prefs.getCheckedFolder());
                if(!Prefs.getMyCheckBool()) {
                    File[] files = ListFolders(dir);
                    Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                    intent.putExtra("key", files.length);
                    for (int i = 0; i < files.length; i++) {
                        //Log.e("heh", String.valueOf(files[i]));
                        intent.putExtra("key" + i + "", String.valueOf(files[i].getName()));
                    }
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(MainActivity.this, MakePhotoActivity.class);
                    startActivity(intent);
                }

            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ChooseCollageActivity.class);
                startActivity(intent);
            }
        });

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,NetworkActivity.class);
                Log.e("heh","Dzieci w layoutcie"+scrollView.getChildCount());
                startActivity(intent);
            }
        });


    }

    public File[] ListFolders(File dir){
        File[] files = dir.listFiles();
        Arrays.sort(files);
        return files;
    }

    public ArrayList<String> GetFiles(File dir){
        ArrayList<String> pliki = new ArrayList<>();
        File[] files = dir.listFiles();
        Arrays.sort(files);
        for(int i=0;i<files.length;i++){
            //Log.e("heh",files[i].getName());
            if(files[i].isFile()) {
                //Log.e("heh",files[i].getName());
                pliki.add(files[i].getPath());
            }
        }
        return pliki;
    }


    static public void MakeNewFile(String name, Bitmap bitmap){
        SimpleDateFormat dFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String d = dFormat.format(new Date());
        File file2 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File newdir2 = new File(file2, "Kamil Tracz 2/"+name+"");
        if(!newdir2.exists())
            newdir2.mkdir();

        File newdir = new File(file2, "Kamil Tracz 2/"+name+"/Photo"+d.toString()+".jpg");
        try {
            FileOutputStream fs = new FileOutputStream(newdir);
            bitmap = Imaging.RotateBitmap(bitmap,90);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fs);
            fs.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Rename(String oldName, String newName){
        File olddir = new File(file, "Kamil Tracz 2/"+oldName);
        File newdir = new File(file, "Kamil Tracz 2/"+newName);
        olddir.renameTo(newdir);
        olddir.delete();
    }

    public void DeleteFolder(String name){
        File dir = new File(file, "Kamil Tracz 2/"+name);
        Log.e("heh",dir.getPath());
        for(File file : dir.listFiles()){
            Log.e("heh",file.getName());
            file.delete();
        }
        dir.delete();
        Log.e("heh",""+dir.exists());
    }

    public void DeleteFile(String file){
        File delfile = new File(file);
        delfile.delete();
    }



}
