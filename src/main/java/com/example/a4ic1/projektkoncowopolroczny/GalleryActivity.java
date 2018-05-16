package com.example.a4ic1.projektkoncowopolroczny;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {

    private LinearLayout galleryLayoutLeft;
    private LinearLayout galleryLayoutRight;
    private LinearLayout linearLayoutMain;
    private LinearLayout.LayoutParams lparams;
    private LinearLayout.LayoutParams lparams2;
    private LinearLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        getSupportActionBar().hide();
        Display display = getWindowManager().getDefaultDisplay();
        //Log.e("heh",""+display.getWidth());
        //Log.e("heh",""+display.getHeight());
        //galleryLayoutLeft = (LinearLayout) findViewById(R.id.linearLayoutGalleryLeft);
        //galleryLayoutRight = (LinearLayout) findViewById(R.id.linearLayoutGalleryRight);
        linearLayoutMain = (LinearLayout) findViewById(R.id.linearLayoutMain);
        final ArrayList<String> photos = Prefs.getPhotoPaths();

        int height = display.getHeight();
        int width = display.getWidth();

        lparams = new LinearLayout.LayoutParams(width/3,height/6);
        lparams2 = new LinearLayout.LayoutParams(width/3*2,height/6);


        for (int i=0;i<photos.size();i++) {
            //Log.e("heh","Działa1");

            final MyImageView myImageView = new MyImageView(GalleryActivity.this);
            Bitmap bmp = betterImageDecode(photos.get(i));
            //Log.e("heh","Działa2");
            myImageView.setImageBitmap(bmp);
            myImageView.setPath(photos.get(i));

            if(((i+1)/2)%2==0) {
                myImageView.setLayoutParams(lparams);
            }else{
                myImageView.setLayoutParams(lparams2);
            }


            myImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            myImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GalleryActivity.this, FullScreenPhotoActivity.class);
                    intent.putExtra("String", myImageView.getPath());
                    startActivity(intent);
                }
            });
            if(i%2==0) {
                layout = new LinearLayout(GalleryActivity.this);
                layout.addView(myImageView);
                linearLayoutMain.addView(layout);
            }else {
                layout.addView(myImageView);
            }
            //Log.e("heh","Działa4");
        }
    }

    private Bitmap betterImageDecode(String filePath) {
        Bitmap myBitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();    //opcje przekształcania bitmapy
        options.inSampleSize = 4; // zmniejszenie jakości bitmapy 4x
        //
        myBitmap = BitmapFactory.decodeFile(filePath, options);
        return myBitmap;
    }
}
