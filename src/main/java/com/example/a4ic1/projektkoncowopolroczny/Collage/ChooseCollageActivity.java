package com.example.a4ic1.projektkoncowopolroczny.Collage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.a4ic1.projektkoncowopolroczny.Prefs;
import com.example.a4ic1.projektkoncowopolroczny.R;

import java.util.ArrayList;

public class ChooseCollageActivity extends AppCompatActivity {

    private LinearLayout kolaz;
    private ArrayList<ImageData> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_collage);
        getSupportActionBar().hide();

        kolaz= (LinearLayout) findViewById(R.id.kolaz);
        for(int i=0;i<kolaz.getChildCount();i++){
            kolaz.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list.clear();
                    int id=kolaz.indexOfChild(view);
                    switch(id){
                        case 0:
                            list.add(new ImageData(0,0,1,1/3f));
                            list.add(new ImageData(0,1/3f,1,1/3f));
                            list.add(new ImageData(0,2/3f,1,1/3f));
                            break;
                        case 1:
                            list.add(new ImageData(0,0,1/2f,1/3f));
                            list.add(new ImageData(1/2f,0,1/2f,1/3f));
                            list.add(new ImageData(0,1/3f,1,2/3f));
                            break;
                        case 2:
                            list.add(new ImageData(0,0,1/4f,1/3f));
                            list.add(new ImageData(1/4f,0,1/2f,1/3f));
                            list.add(new ImageData(3/4f,0,1/4f,1/3f));
                            list.add(new ImageData(0,1/3f,3/4f,1/3f));
                            list.add(new ImageData(3/4f,1/3f,1/4f,1/3f));
                            list.add(new ImageData(0,2/3f,1/2f,1/3f));
                            list.add(new ImageData(1/2f,2/3f,1/2f,1/3f));
                            break;
                    }
                    Intent intent = new Intent(ChooseCollageActivity.this,CollageActivity.class);
                    Prefs.setImageKolaz(list);
                    startActivity(intent);
                }
            });
        }
    }
}
