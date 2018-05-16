package com.example.a4ic1.projektkoncowopolroczny;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by 4ic1 on 2016-10-07.
 */
public class MiniPhoto extends ImageView {
    private Bitmap smallbitmap;
    private int _x;
    private int _y;
    public MiniPhoto(Context context, int x,int y, Bitmap bitmap) {
        super(context);
        _x=x;
        _y=y;
        smallbitmap=Imaging.ConvertToSmallerBitmap(bitmap,x,y);
        this.setImageBitmap(smallbitmap);
        this.setLayoutParams(new LinearLayout.LayoutParams(x,y));
        this.setBackgroundColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(6);
        paint.setColor(Color.argb(200, 255, 255, 255));
        canvas.drawRect(0,0,_x,_y,paint);
    }
}
