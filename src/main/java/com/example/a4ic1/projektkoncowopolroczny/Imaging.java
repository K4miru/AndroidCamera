package com.example.a4ic1.projektkoncowopolroczny;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.ByteArrayOutputStream;

/**
 * Created by 4ic1 on 2016-10-07.
 */
public class Imaging {

    static public Bitmap RotateBitmap(Bitmap bitmap,int degress){
        Matrix matrix = new Matrix();
        matrix.postRotate(degress);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return rotatedBitmap;
    }

    static public Bitmap ConvertToBitmap(byte[] bytetab){

        Bitmap bitmap = BitmapFactory.decodeByteArray(bytetab, 0, bytetab.length);
        return bitmap;
    }

    static public Bitmap ConvertToSmallerBitmap(Bitmap inputBitmap,int width,int height){
        Bitmap smallBmp = Bitmap.createScaledBitmap(inputBitmap, width, height, false);
        return smallBmp;
    }

    static public byte[] ConvertToBytetab(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bytes = stream.toByteArray();
        return bytes;
    }
}
