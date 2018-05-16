package com.example.a4ic1.projektkoncowopolroczny.Albums;


import android.app.Application;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.a4ic1.projektkoncowopolroczny.MainActivity;
import com.example.a4ic1.projektkoncowopolroczny.NetworkActivity;
import com.example.a4ic1.projektkoncowopolroczny.Prefs;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


/**
 * Created by Mirus on 14.12.2016.
 */
public class LoadImages extends AsyncTask {
    private Drawable loadedImage;
    private LinearLayout viewPager;
    private ImageView imageView;
    private TextView textView;
    private String date;
    private String size;
    private String url;

    public LoadImages(LinearLayout viewPager) {
        this.viewPager=viewPager;
        Log.e("heh", "Dzieci przed dodaniem elementu: "+viewPager.getChildCount());
        imageView = new ImageView(this.viewPager.getContext());
        textView = new TextView(this.viewPager.getContext());

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Object[] params) {
        Log.e("heh",""+params.length);
        Log.e("heh",""+params[0].toString());
        try {
            Log.e("heh","pobieranko");
            loadedImage = LoadImageFromWeb(params[0].toString());
            Prefs.listDrawable.add(loadedImage);
            date = params[1].toString();
            size=params[3].toString();
            url = params[2].toString();
        } catch (IOException e) {
            Log.e("heh",e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(Prefs.getX(),Prefs.getY()/3);
        LinearLayout.LayoutParams lparams2 = new LinearLayout.LayoutParams(Prefs.getX(),Prefs.getY()/6);


        //main
        RelativeLayout linear = new RelativeLayout(viewPager.getContext());
        linear.setLayoutParams(lparams);

        imageView.setImageDrawable(loadedImage);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(lparams);
        if(Prefs.isNetworkActivity()){
            imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Intent intent = new Intent(viewPager.getContext(), MainActivity.class);
                    return true;
                }


            });
        }
        linear.addView(imageView);

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
        viewPager.addView(linear);
        viewPager.setBackgroundColor(Color.RED);
        viewPager.invalidate();

    }

    public Drawable LoadImageFromWeb(String url) throws IOException {
        InputStream inputStream = (InputStream) new URL(url).getContent();
        return Drawable.createFromStream(inputStream,"srcName");
    }
}
