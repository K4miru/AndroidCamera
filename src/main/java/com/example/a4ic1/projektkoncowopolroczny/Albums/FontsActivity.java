package com.example.a4ic1.projektkoncowopolroczny.Albums;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.opengl.Visibility;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.a4ic1.projektkoncowopolroczny.R;

import java.io.IOException;
import java.io.Serializable;

public class FontsActivity extends AppCompatActivity {

    private LinearLayout layout;
    private LinearLayout colorlayout;
    private LinearLayout picker;
    private RelativeLayout relativeLayout;
    private EditText editText;
    private String[] lista;
    private ImageView check;
    private ImageView colorPicker;
    private ImageView delPicker;
    private ImageView checkPicker;
    private ImageView border;
    private ImageView background;
    private PreviewText previewText;
    private Typeface tf;

    private int x;
    private int id=0;
    private int color1= Color.RED;
    private int color2=Color.BLACK;
    private boolean colorNumber=false;
    private int kolor=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fonts);
        getSupportActionBar().hide();

        Display display = getWindowManager().getDefaultDisplay();
        x = display.getWidth();

        layout = (LinearLayout) findViewById(R.id.linearLayout2);
        colorlayout = (LinearLayout) findViewById(R.id.colorlayout);
        picker = (LinearLayout) findViewById(R.id.picker);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        editText = (EditText) findViewById(R.id.editText);
        check = (ImageView) findViewById(R.id.check);
        colorPicker = (ImageView) findViewById(R.id.colorpicker);
        checkPicker = (ImageView) findViewById(R.id.checkPicker);
        delPicker = (ImageView) findViewById(R.id.cancel);
        border = (ImageView) findViewById(R.id.border);
        background = (ImageView) findViewById(R.id.background);


        picker.setVisibility(View.INVISIBLE);

        border.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picker.setVisibility(View.VISIBLE);
                colorNumber=false;
            }
        });

        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picker.setVisibility(View.VISIBLE);
                colorNumber=true;
            }
        });

        checkPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picker.setVisibility(View.INVISIBLE);
                if(colorNumber)
                    color2=kolor;
                else
                    color1=kolor;
                RefreshRelative(editText.getText().toString());
            }
        });

        delPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picker.setVisibility(View.INVISIBLE);
            }
        });
        colorPicker.setDrawingCacheEnabled(true);

        colorPicker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_MOVE:
                        Bitmap bmp = v.getDrawingCache();
                        Log.e("heh", event.getX() + " " + event.getY());


                        try {
                             kolor= bmp.getPixel((int) event.getX(), (int) event.getY());
                        }catch(Exception ex) {

                        }
                        if(kolor!=-1) {
                            colorlayout.setBackgroundColor(kolor);

                        }
                        break;

                }

                return true;
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(picker.getVisibility()== View.INVISIBLE) {
                    Intent intent = new Intent();
                    setResult(400, intent);
                    intent.putExtra("tekst", editText.getText().toString());
                    intent.putExtra("font", lista[id]);
                    intent.putExtra("color1", color1);
                    intent.putExtra("color2", color2);
                    intent.putExtra("height", previewText.getRectHeight());
                    intent.putExtra("width", previewText.getRectWidth());
                    finish();
                }
            }
        });

        AssetManager assetManager = getAssets();
        lista = null;
        try {
            lista = assetManager.list("fonts");
            for(int i=0;i<lista.length;i++){
                TextView textView = new TextView(FontsActivity.this);
                Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/"+lista[i]);
                textView.setTypeface(tf);
                textView.setText("Zażółć gęślą jaźń. 1234567890");
                textView.setTextSize(20);
                textView.setWidth(x);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        id=layout.indexOfChild(v);
                        RefreshRelative(editText.getText().toString());
                    }
                });
                layout.addView(textView);
                //Log.e("heh",lista[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("heh",e.toString());
        }

        TextWatcher textWatcher = new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                RefreshRelative(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        };

        editText.addTextChangedListener(textWatcher );
    }

    private void RefreshRelative(String s){
        tf = Typeface.createFromAsset(getAssets(),"fonts/"+lista[id]);
        previewText = new PreviewText(FontsActivity.this,tf,s,color1,color2);
        relativeLayout.removeAllViews();
        relativeLayout.addView (previewText);
    }
}
