package com.example.a4ic1.projektkoncowopolroczny;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.function.Function;


public class CameraActivity extends AppCompatActivity {

    private GridView gridView;
    private ImageView checkBoxImg;
    private ImageView acceptImg;
    private EditText editText;
    private String old="";
    private boolean checked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        getSupportActionBar().hide();

        Bundle bundle = getIntent().getExtras();
        int len = bundle.getInt("key");
        final ArrayList<String> lista = new ArrayList<String>();
        for(int i=0;i<len;i++) {
            String wartosc = bundle.getString("key" + i);
            lista.add(wartosc);
        }

        setContentView(R.layout.activity_camera);

        gridView = (GridView) findViewById(R.id.GridView2);
        checkBoxImg = (ImageView) findViewById(R.id.ImageViewCheck2);
        acceptImg = (ImageView) findViewById(R.id.ImageViewCheck1);
        editText =(EditText) findViewById(R.id.editText);

        checkBoxImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checked) {
                    checked = false;
                    checkBoxImg.setImageResource(R.drawable.box);
                }else {
                    checked = true;
                    checkBoxImg.setImageResource(R.drawable.checkedbox);
                }
            }
        });

        acceptImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Prefs.getCheckedFolder()!="") {
                    if (checked) {
                        Prefs.setMyCheckBool(true);
                        Intent intent = new Intent(CameraActivity.this, MakePhotoActivity.class);
                        startActivity(intent);
                    } else {
                        Prefs.setMyCheckBool(false);
                        Intent intent = new Intent(CameraActivity.this, MakePhotoActivity.class);
                        startActivity(intent);
                    }
                }/*if(old!=""){
                    Log.e("heh",editText.getText().toString());
                    MainActivity newmain = new MainActivity();
                    newmain.Rename(old,editText.getText().toString());
                }else{
                    Log.e("heh",editText.getText().toString());
                    MainActivity newmain = new MainActivity();
                    newmain.MakeNewFolder(editText.getText().toString());
                }*/
            }
        });

        MyArrayAdapter adapter = new MyArrayAdapter(
                CameraActivity.this,
                R.layout.camerapliki,
                lista);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                editText.setText(lista.get(i));
                old=lista.get(i);
                Prefs.setCheckedFolder(old);
            }
        });

        gridView.setAdapter(adapter);

    }


}
