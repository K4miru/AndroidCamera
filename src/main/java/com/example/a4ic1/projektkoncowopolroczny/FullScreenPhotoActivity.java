package com.example.a4ic1.projektkoncowopolroczny;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class FullScreenPhotoActivity extends AppCompatActivity {

    private ImageView fullScreen;
    private ImageView delPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_photo);
        getSupportActionBar().hide();
        Bundle bundle = getIntent().getExtras();
        final String imgPath = bundle.getString("String");
        fullScreen = (ImageView) findViewById(R.id.fullScreen);
        delPhoto = (ImageView) findViewById(R.id.deletePhoto);
        fullScreen.setImageDrawable(Drawable.createFromPath(imgPath));
        delPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(FullScreenPhotoActivity.this);
                alert.setTitle("Uwaga!");
                alert.setMessage("Usunąć zdjęcie?");
//ok
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //wyswietl which
                        MainActivity newmain = new MainActivity();
                        newmain.DeleteFile(imgPath);
                    }

                });

//no
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        //wyswietl which
                    }
                });
//
                alert.show();



            }
        });
    }
}
