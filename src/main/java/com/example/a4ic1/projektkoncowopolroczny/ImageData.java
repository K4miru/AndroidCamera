package com.example.a4ic1.projektkoncowopolroczny;

import java.io.Serializable;

/**
 * Created by Mirus on 31.10.2016.
 */

public class ImageData implements Serializable {
    private float X;
    private float Y;
    private float W;
    private float H;

    public ImageData(float x, float y, float w, float h) {
        X = x;
        Y = y;
        W = w;
        H = h;
    }

    public float getH() {
        return H;
    }

    public float getX() {
        return X;
    }

    public float getY() {
        return Y;
    }

    public float getW() {
        return W;
    }

}
