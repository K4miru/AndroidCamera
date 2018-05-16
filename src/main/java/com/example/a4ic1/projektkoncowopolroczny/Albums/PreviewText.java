package com.example.a4ic1.projektkoncowopolroczny.Albums;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;

/**
 * Created by Mirus on 22.11.2016.
 */
public class PreviewText extends View implements Serializable {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private String tekst;
    private int color1;
    private int color2;
    private float x=0;
    private float y=75;
    private int height;
    private int width;

    public int getRectHeight(){
        return  height;
    }

    public int getRectWidth(){
        return width;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public float getYY() {
        return y;
    }

    public void setYY(float y) {
        this.y = y;
        invalidate();
    }

    public float getXX() {
        return x;
    }

    public void setXX(float x) {
        this.x = x;
        invalidate();
    }



    public PreviewText(Context context, Typeface tf, String tekst, int color1, int color2) {
        super(context);
        this.tekst=tekst;
        this.color1=color1;
        this.color2=color2;
        paint.reset();            // czyszczenie
        paint.setAntiAlias(true);    // wygładzanie
        paint.setTextSize(100);        // wielkość fonta
        paint.setTypeface(tf);  // czcionka
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect rect = new Rect();
        paint.getTextBounds(tekst, 0, tekst.length(), rect);

        height = rect.height();
        width = rect.width();

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color1);
        canvas.drawText(tekst, x, y, paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setColor(color2);
        canvas.drawText(tekst, x, y, paint);

    }
}
