package com.example.a4ic1.projektkoncowopolroczny.Camera;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.hardware.Camera;
import android.widget.FrameLayout;

import java.io.IOException;

/**
 * Created by 4ic1 on 2016-09-23.
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private android.hardware.Camera _camera;
    private SurfaceHolder _surfaceHolder;

    public CameraPreview(Context context, Camera camera) {
        super(context);
        this._surfaceHolder = _surfaceHolder;
        _camera = camera;
        _surfaceHolder = this.getHolder();
        _surfaceHolder.addCallback((Callback) this);
    }



    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {
            _camera.setPreviewDisplay(_surfaceHolder);
            _camera.setDisplayOrientation(90);
            _camera.startPreview();
        }catch(Exception ex){

        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        try {
            _camera.setPreviewDisplay(_surfaceHolder);
            _camera.startPreview();
        }catch(Exception ex){

        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
}
