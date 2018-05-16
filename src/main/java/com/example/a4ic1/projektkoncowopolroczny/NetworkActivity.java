package com.example.a4ic1.projektkoncowopolroczny;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.a4ic1.projektkoncowopolroczny.Albums.DownloadFoto;

public class NetworkActivity extends AppCompatActivity {

    private LinearLayout scrollView;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
        getSupportActionBar().hide();
        scrollView = (LinearLayout) findViewById(R.id.scrollViewNetwork);

        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(Prefs.getX(),Prefs.getY()/3);
        LinearLayout.LayoutParams lparams2 = new LinearLayout.LayoutParams(Prefs.getX(),Prefs.getY()/6);


        Log.e("heh",""+Prefs.getListDataArrayList().size());
        for(int i =0;i<Prefs.getListDataArrayList().size();i++) {
            final int J = i;
            final RelativeLayout linear = new RelativeLayout(NetworkActivity.this);
            linear.setLayoutParams(lparams);
            ImageView imageView= new ImageView(NetworkActivity.this);
            TextView textView= new TextView(NetworkActivity.this);;
            imageView.setImageDrawable(Prefs.listDrawable.get(i));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(lparams);
                imageView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Log.e("heh","start");
                        Log.e("heh",""+Prefs.getListDataArrayList2().get(J).getUrl());
                        Log.e("heh",""+Prefs.getListDataArrayList2().size());
                        Log.e("heh",""+Prefs.getListDataArrayList().size());
                        Log.e("heh",""+J);
                        Intent intent = new Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(Prefs.getListDataArrayList2().get(J).getUrl()));
                        startActivity(intent);
                        return true;
                    }


                });

            linear.addView(imageView);
            String date = Prefs.getListDataArrayList().get(i).getPublicId();
            String size = ""+Prefs.getListDataArrayList2().get(i).getBytes();
            String filename = date.substring(1,date.indexOf('-'));
            String filename2 = date.substring((date.indexOf('-')+1),date.length());
            filename=filename.replaceAll("_",".");
            filename2=filename2.replaceAll("_",":");
            date=filename+" "+filename2;
            textView.setText("Nazwa: "+date+"\nWielkość: "+Math.round(Float.parseFloat(size)/1024)+"KB ");
            textView.setTextSize(20f);
            textView.setLayoutParams(lparams2);
            textView.setBottom(1);
            linear.addView(textView);
            scrollView.addView(linear);
            scrollView.setBackgroundColor(Color.RED);
            scrollView.invalidate();
        }
    }


}
