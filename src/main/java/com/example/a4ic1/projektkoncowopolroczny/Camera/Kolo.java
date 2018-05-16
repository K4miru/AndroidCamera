package com.example.a4ic1.projektkoncowopolroczny.Camera;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by 4ic1 on 2016-10-07.
 */
public class Kolo extends View {
    private int X;
    private int Y;
    private int R;

    public Kolo(Context context, int x, int y,int r) {
        super(context);
        X=x;
        Y=y;
        R=r;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        paint.setColor(Color.argb(200, 255, 255, 255));
        canvas.drawCircle(X, Y, R, paint);
    }


}
