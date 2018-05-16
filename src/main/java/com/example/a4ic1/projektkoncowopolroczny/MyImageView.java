package com.example.a4ic1.projektkoncowopolroczny;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Mirus on 21.09.2016.
 */
public class MyImageView extends ImageView {
    private String path;

    public MyImageView(Context context) {
        super(context);
    }

    public String getPath(){
        return path;
    }

    public void setPath(String pathstr){
        path=pathstr;
    }


}
