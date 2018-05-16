package com.example.a4ic1.projektkoncowopolroczny;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CollageActivity extends AppCompatActivity {
    private FrameLayout frameLayout;
    private ImageView saveKolaz;
    private ImageView rotateKolaz;
    private ImageView flipKolaz;
    private LinearLayout linearLayout;
    private int id;
    private ArrayList<Bitmap> bitmapList = new ArrayList<>();
    private Bitmap bitmap;
    private int number;
    private int x;
    private int y;
    private boolean draw=false;


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Display display = getWindowManager().getDefaultDisplay();
        x = display.getWidth();
        y = display.getHeight() - linearLayout.getHeight();
        Log.e("heh","Rysowanie");
        ArrayList<ImageData> list = Prefs.getImageKolaz();
        if(!draw) {
            for (int i = 0; i < list.size(); i++) {
                ImageView imageView = new ImageView(CollageActivity.this);
                imageView.setX(list.get(i).getX() * x);
                imageView.setY(list.get(i).getY() * y);
                float w = list.get(i).getW() * x;
                float h = list.get(i).getH() * y;
                imageView.setLayoutParams(new FrameLayout.LayoutParams((int) w, (int) h));
                imageView.setImageResource(R.drawable.camera);
                bitmapList.add(null);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        id = frameLayout.indexOfChild(v);
                    }
                });

                imageView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        id = frameLayout.indexOfChild(v);
                        AlertDialog.Builder alert = new AlertDialog.Builder(CollageActivity.this);
                        alert.setTitle("Uwaga!");
                        String[] opcje = {"Galeria", "Kamera Systemowa", "Kamera własna"};
                        alert.setItems(opcje, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                number = which;
                                switch (which) {
                                    case 0:
                                        //galeria
                                        Intent intent = new Intent(Intent.ACTION_PICK);
                                        intent.setType("image/*");
                                        startActivityForResult(intent, 100);

                                        break;
                                    case 1:
                                        //Kamera własna
                                        Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        //jesli jest dostepna zewnetrzny aparat
                                        if (intent2.resolveActivity(getPackageManager()) != null) {
                                            startActivityForResult(intent2, 200); // 200 - jw
                                        }
                                        break;
                                    case 2:
                                        //Kamera systemowa
                                        Intent intent3 = new Intent(CollageActivity.this, CollageCameraActivity.class);
                                        startActivityForResult(intent3, 300);
                                        break;
                                }
                            }
                        });
                        alert.show();
                        return false;
                    }
                });
                frameLayout.addView(imageView);
            }
            draw=true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (number) {
            case 0:
                Uri imgData = data.getData();
                InputStream stream = null;
                try {
                    stream = getContentResolver().openInputStream(imgData);
                    Bitmap b = BitmapFactory.decodeStream(stream);
                    bitmapList.set(id,b);
                    SetBitmap(b);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case 1:
                Bundle extras = data.getExtras();
                Bitmap b = (Bitmap) extras.get("data");
                bitmapList.set(id,b);
                SetBitmap(b);
                break;
            case 2:
                Bundle extras2 = data.getExtras();
                byte[] xdata = (byte[]) extras2.get("fotodata");
                Bitmap b2 = Imaging.RotateBitmap(Imaging.ConvertToBitmap(xdata), 90);
                bitmapList.set(id,b2);
                SetBitmap(b2);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collage);
        getSupportActionBar().hide();

        frameLayout = (FrameLayout) findViewById(R.id.frameLayout2);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        saveKolaz = (ImageView) findViewById(R.id.saveKolaz);
        rotateKolaz = (ImageView) findViewById(R.id.rotate);
        flipKolaz = (ImageView) findViewById(R.id.flip);

        saveKolaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap b = frameLayout.getDrawingCache(true);
                b = Imaging.RotateBitmap(b, -90);
                MainActivity.MakeNewFile("Kolaz", b);
                Log.e("heh", "zapisano");
            }
        });

        rotateKolaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bitmapList.get(id)!=null) {
                    bitmapList.set(id, Imaging.RotateBitmap(bitmapList.get(id), 90));
                    SetBitmap(bitmapList.get(id));
                }
            }
        });

        flipKolaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bitmapList.get(id)!=null) {
                    Matrix matrix = new Matrix();
                    matrix.postScale(-1.0f,1.0f);
                    Bitmap oryginal = bitmapList.get(id);
                    bitmapList.set(id, Bitmap.createBitmap(oryginal, 0, 0, oryginal.getWidth(), oryginal.getHeight(), matrix, true));
                    SetBitmap(bitmapList.get(id));
                }
            }
        });
        frameLayout.setDrawingCacheEnabled(true);

    }

    private void SetBitmap(Bitmap b) {
        if (!(frameLayout.getChildAt(id) instanceof ImageView)) {
            return;
        }
        ImageView image = (ImageView) frameLayout.getChildAt(id);
        image.setImageResource(0);
        image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        image.setImageBitmap(b);

    }
}
