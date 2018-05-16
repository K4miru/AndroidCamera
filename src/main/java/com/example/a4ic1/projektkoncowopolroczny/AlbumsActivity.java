package com.example.a4ic1.projektkoncowopolroczny;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AlbumsActivity extends AppCompatActivity {


    private GridView gridView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        Bundle bundle = getIntent().getExtras();
        int len = bundle.getInt("key");
        final ArrayList<String>lista = new ArrayList<String>();
        for(int i=0;i<len;i++) {
            String wartosc = bundle.getString("key" + i + "");
            lista.add(wartosc);
        }

        setContentView(R.layout.activity_albums);

        gridView = (GridView) findViewById(R.id.GridView1);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                AlbumsActivity.this,     // Context
                R.layout.plik,     // nazwa pliku xml naszej komórki
                R.id.text1,         // id pola txt w komórce
                lista );         // tablica przechowująca dane

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MainActivity newmain = new MainActivity();
                Prefs.setFolderDir(lista.get(i));
                Prefs.setPhotoPaths(newmain.GetFiles(Prefs.getFolderDir()));
                /*for(int j=0;j<listaplikow.size();j++){
                    Log.e("heh",listaplikow.get(j));
                }*/
                Intent intent = new Intent(AlbumsActivity.this,GalleryActivity.class);
                startActivity(intent);
            }
        });
        gridView.setAdapter(adapter);



    }
}
