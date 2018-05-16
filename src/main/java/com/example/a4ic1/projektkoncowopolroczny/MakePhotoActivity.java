package com.example.a4ic1.projektkoncowopolroczny;

import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.LocaleList;
import android.os.Process;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.hardware.Camera;
import android.util.Log;
import android.view.Display;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


public class MakePhotoActivity extends AppCompatActivity {


    private Camera camera;
    private int cameraId = -1;
    private CameraPreview _cameraPreview;
    private FrameLayout _frameLayout;
    private ImageView makePhoto;
    private ImageView savePhoto;
    private LinearLayout bottomLayout;
    private LinearLayout topLayout;
    private ImageView pictureSizes;
    private ImageView whiteBalance;
    private ImageView colorEffects;
    private ImageView exposureCompensation;
    private List<MiniPhoto> photoList;
    private byte[] bytetab;
    private boolean photoMade=false;
    private boolean photoSave=true;
    private Camera.Parameters camParams;
    private boolean animation =true;
    private boolean touched = false;
    private int photoCount=0;
    private OrientationEventListener orientationEventListener;
    private Toast zmienna;
    private int obrot =0;
    private int oldobrot =0;
    private int x;
    private int r;
    private int y;
    private ArrayList<byte[]> listabyte= new ArrayList<byte[]>();
    private int zmienna2=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_photo);
        getSupportActionBar().hide();
        makePhoto = (ImageView) findViewById(R.id.makePhoto);
        savePhoto = (ImageView) findViewById(R.id.savePhoto);
        bottomLayout = (LinearLayout) findViewById(R.id.bottomLayout);
        topLayout = (LinearLayout) findViewById(R.id.topLayout);
        whiteBalance = (ImageView) findViewById(R.id.whiteBalance);
        pictureSizes = (ImageView) findViewById(R.id.pictureSizes);
        exposureCompensation = (ImageView) findViewById(R.id.exposureCompensation);
        colorEffects = (ImageView) findViewById(R.id.colorEffects);

        //-----------------
        initCamera();
        initPreview();
        camParams = camera.getParameters();
        //-----------------
        Display display = getWindowManager().getDefaultDisplay();
        x=display.getWidth()/2;
        y=display.getHeight()/2;
        r=display.getHeight()/4;
        Kolo kolo = new Kolo(MakePhotoActivity.this,x,y,r);
        _frameLayout.addView(kolo);
        //-----------------
        orientationEventListener = new OrientationEventListener(MakePhotoActivity.this) {
            @Override
            public void onOrientationChanged(int i) {

                //showToast(""+i);
                oldobrot=obrot;
                if(i>55&&i<125) {
                    obrot=0;
                }else if(i>145&&i<215){
                    obrot=3;
                }else if(i>235&&i<305){
                    obrot=2;
                }else if(i>325||i<35){
                    obrot=1;
                }
                if(oldobrot!=obrot) {
                    Toast.makeText(MakePhotoActivity.this, "" + obrot, Toast.LENGTH_SHORT).show();
                    for (int j = 2; j < _frameLayout.getChildCount(); j++) {
                        ObjectAnimator.ofFloat(_frameLayout.getChildAt(j), View.ROTATION, (obrot+1)*90, (obrot+2)*90+90)
                                .setDuration(300)
                                .start();
                    }
                    for (int j = 0; j < bottomLayout.getChildCount(); j++) {
                        ObjectAnimator.ofFloat(bottomLayout.getChildAt(j), View.ROTATION, (obrot+1)*90, (obrot+2)*90+90)
                                .setDuration(300)
                                .start();
                    }
                    for (int j = 0; j < topLayout.getChildCount(); j++) {
                        ObjectAnimator.ofFloat(topLayout.getChildAt(j), View.ROTATION, (obrot+1)*90, (obrot+2)*90+90)
                                .setDuration(300)
                                .start();
                    }
                }
            }
        };

        _frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(animation) {
                    ObjectAnimator anim = ObjectAnimator.ofFloat(bottomLayout, View.TRANSLATION_Y, 120);
                    anim.setDuration(300);
                    anim.start();
                    ObjectAnimator anim2 = ObjectAnimator.ofFloat(topLayout, View.TRANSLATION_Y, -120);
                    anim2.setDuration(300);
                    anim2.start();
                    animation=false;
                }else{
                    ObjectAnimator anim = ObjectAnimator.ofFloat(bottomLayout, View.TRANSLATION_Y, 0);
                    anim.setDuration(300);
                    anim.start();
                    ObjectAnimator anim2 = ObjectAnimator.ofFloat(topLayout, View.TRANSLATION_Y, 0);
                    anim2.setDuration(300);
                    anim2.start();
                    animation=true;
                }
            }
        });
        makePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(photoSave) {
                    camera.takePicture(null, null, camPictureCallback);
                    photoMade = true;

                    //photoSave=false;
                //}
            }
        });

        savePhoto.setVisibility(View.INVISIBLE);
        savePhoto.setActivated(false);

        pictureSizes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final List<Camera.Size> picturesSizesList = camParams.getSupportedPictureSizes();
                /*for(int i=0;i<picturesSizesList.size();i++){
                    Log.e("heh",picturesSizesList.get(i).width+" "+picturesSizesList.get(i).height);
                }*/

                ArrayList<String> lista = new ArrayList<String>();
                for(int i=0;i<picturesSizesList.size();i++){
                    lista.add(""+picturesSizesList.get(i).width+"x"+picturesSizesList.get(i).height);
                }

                final AlertDialog.Builder alert = new AlertDialog.Builder(MakePhotoActivity.this);
                alert.setTitle("Uwaga!");
                final String[] opcje = lista.toArray(new String[0]);
                alert.setItems(opcje, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        int zm=opcje[which].toString().indexOf("x");
                        String width = opcje[which].substring(0,zm);
                        String height = opcje[which].substring(zm+1,opcje[which].length());
                        Log.e("heh",""+width+" "+height);
                        camParams.setPictureSize(Integer.parseInt(width),Integer.parseInt(height));
                        camera.setParameters(camParams);
                    }
                });
                alert.show();
            }
        });

        whiteBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> whiteBalanceList = camParams.getSupportedWhiteBalance();
                /*for(int i=0;i<whiteBalanceList.size();i++){
                    Log.e("heh",whiteBalanceList.get(i));
                }*/
                AlertDialog.Builder alert = new AlertDialog.Builder(MakePhotoActivity.this);
                alert.setTitle("Uwaga!");

                final String[] opcje = whiteBalanceList.toArray(new String[0]);
                alert.setItems(opcje, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        camParams.setWhiteBalance(opcje[which]);
                        camera.setParameters(camParams);
                    }
                });
                alert.show();
            }
        });

        colorEffects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> colorEffectsList = camParams.getSupportedColorEffects();
                /*for(int i=0;i<colorEffectsList.size();i++){
                    Log.e("heh",colorEffectsList.get(i));
                }*/
                AlertDialog.Builder alert = new AlertDialog.Builder(MakePhotoActivity.this);
                alert.setTitle("Uwaga!");
                final String[] opcje = colorEffectsList.toArray(new String[0]);
                alert.setItems(opcje, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        camParams.setColorEffect(opcje[which]);
                        camera.setParameters(camParams);
                    }
                });
                alert.show();
            }
        });

        exposureCompensation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.e("heh",""+camParams.getMinExposureCompensation());
                //Log.e("heh",""+camParams.getMaxExposureCompensation());

                ArrayList<String> lista = new ArrayList<String>();
                for(int i=camParams.getMinExposureCompensation();i<=camParams.getMaxExposureCompensation();i++){
                    lista.add(""+i);
                }

                AlertDialog.Builder alert = new AlertDialog.Builder(MakePhotoActivity.this);
                alert.setTitle("Uwaga!");
                final String[] opcje = lista.toArray(new String[0]);
                alert.setItems(opcje, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        int zmienna = Integer.parseInt(opcje[which]);
                        camParams.setExposureCompensation(zmienna);
                        camera.setParameters(camParams);
                    }
                });
                alert.show();
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        if (camera != null) {
            camera.stopPreview();
            _cameraPreview.getHolder().removeCallback(_cameraPreview);
            camera.release();
            camera = null;
        }
        orientationEventListener.disable();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (camera == null) {
            initCamera();
            initPreview();
        }
        if (orientationEventListener.canDetectOrientation()) {
            orientationEventListener.enable();
            Log.e("heh","ok");
        } else {
            Log.e("heh","nie ok");
        }
    }

    public void showToast(String tekst){
        if (zmienna != null) {
            zmienna.cancel();
        }
        zmienna = Toast.makeText(MakePhotoActivity.this,tekst,Toast.LENGTH_SHORT);
        zmienna.show();
    }

    public void initCamera(){
        boolean cam = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
        if (!cam) {
            Log.e("heh","BRak kamery");
        } else {
            cameraId = getCameraId();
            //Log.e("heh","jakaś kamera");
            if (cameraId < 0) {
                Log.e("heh","BRak kamery z przodu");
            } else if (cameraId >= 0) {
                camera = Camera.open(cameraId);
            }
        }
    }

    public int getCameraId(){
        int cid = 0;
        int camerasCount = Camera.getNumberOfCameras(); // gdy więcej niż jedna kamera

        for (int i = 0; i < camerasCount; i++) {
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(i, cameraInfo);

            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                cid = i;
            }
	    /*
            if (cameraInfo.facing == CameraInfo.CAMERA_FACING_FRONT) {
                cid = i;
            }
	    */
        }

        return cid;
    }

    public void initPreview(){
        _cameraPreview = new CameraPreview(MakePhotoActivity.this, camera);
        _frameLayout = (FrameLayout) findViewById(R.id.frameLayout1);
        _frameLayout.addView(_cameraPreview);
    }

    private Camera.PictureCallback camPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            
            bytetab= data;
            listabyte.add(bytetab);
            MiniPhoto miniPhoto = new MiniPhoto(MakePhotoActivity.this,200,200,Imaging.RotateBitmap(Imaging.ConvertToBitmap(bytetab),90));
            photoCount++;

            _frameLayout.addView(miniPhoto);

            for (int j = 2; j < _frameLayout.getChildCount(); j++) {
                double a=Math.PI/photoCount*(j-2)*2;
                double w=-1*Math.sin(a)*r+y-100;
                double z=-1*Math.cos(a)*r+x-100;

                //swipe
                _frameLayout.getChildAt(j).setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        final int index = _frameLayout.indexOfChild(view)-2;

                        float fX = _frameLayout.getChildAt(index+2).getX();
                        float fY = _frameLayout.getChildAt(index+2).getY();
                        float rX = motionEvent.getRawX();
                        float rY = motionEvent.getRawY();
                        //Log.e("heh", "pos x: "+rX + " frameX: "+ fX);
                        //Log.e("heh", "pos y: "+rY + " frameY: "+ fY);

                        if(!touched) {
                            if (fX - rX < -170 || fX - rX > 170 || fY - rY < -170 || fY - rY > 170) {
                                touched=true;
                                Log.e("heh",""+touched);
                                switch (motionEvent.getAction()) {
                                    case MotionEvent.ACTION_DOWN:
                                        Log.e("heh", "down");
                                        Delete(index);
                                        break;
                                    case MotionEvent.ACTION_MOVE:
                                        Log.e("heh", "move");
                                        Delete(index);
                                        break;
                                    case MotionEvent.ACTION_UP:
                                        Log.e("heh", "up");
                                        Delete(index);
                                        break;
                                }
                            }
                        }
                        return false;
                    }
                });
                //Hold
                _frameLayout.getChildAt(j).setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(final View view) {

                        AlertDialog.Builder alert = new AlertDialog.Builder(MakePhotoActivity.this);
                        alert.setTitle("Uwaga!");
                        final String[] opcje = {"Podgląd zdjęcia","Zapisz zdjęcie","Usuń zdjęcie","Zapisz wszystkie","Usuń wszystkie"};
                        alert.setItems(opcje, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                final int index = _frameLayout.indexOfChild(view)-2;
                                final RelativeLayout relativelayout = (RelativeLayout) findViewById(R.id.RL);
                                switch(which){
                                    case 0:
                                        //Podgląd

                                        final LinearLayout layout4 = new LinearLayout(MakePhotoActivity.this);
                                        LinearLayout.LayoutParams lparams4 = new LinearLayout.LayoutParams(x*2,y*2);
                                        layout4.setLayoutParams(lparams4);
                                        layout4.setBackgroundColor(Color.parseColor("#AAFFFFFF"));
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                relativelayout.removeView(layout4);
                                                Log.e("heh","cancel");


                                        //
                                        final LinearLayout layout = new LinearLayout(MakePhotoActivity.this);
                                        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(x*2,y*2);
                                        layout.setLayoutParams(lparams);
                                        final ImageView Imagenew= new ImageView(MakePhotoActivity.this);
                                        Bitmap newimage = Imaging.RotateBitmap(Imaging.ConvertToBitmap(listabyte.get(index)),90);
                                        Imagenew.setImageBitmap(newimage);
                                        Imagenew.setLayoutParams(lparams);
                                        layout.addView(Imagenew);
                                        layout.setX(0);
                                        layout.setY(-50);
                                        relativelayout.addView(layout);
                                        savePhoto.setVisibility(View.VISIBLE);
                                        savePhoto.setActivated(true);
                                        touched=true;
                                        savePhoto.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                int id=relativelayout.indexOfChild(layout);
                                                relativelayout.removeView(relativelayout.getChildAt(id));
                                                savePhoto.setVisibility(View.INVISIBLE);
                                                savePhoto.setActivated(false);
                                                touched=false;
                                            }
                                        });
                                            }
                                        }, 1);
                                        relativelayout.addView(layout4);
                                        break;
                                    case 1:
                                        //Zapisz bieżące
                                        final LinearLayout layout2 = new LinearLayout(MakePhotoActivity.this);
                                        LinearLayout.LayoutParams lparams2 = new LinearLayout.LayoutParams(x*2,y*2);
                                        layout2.setLayoutParams(lparams2);
                                        layout2.setBackgroundColor(Color.parseColor("#AAFFFFFF"));
                                        Handler handler2 = new Handler();
                                        handler2.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                savePhoto(listabyte.get(index));
                                                relativelayout.removeView(layout2);
                                                Log.e("heh","cancel");
                                            }
                                        }, 1);
                                        relativelayout.addView(layout2);
                                        break;
                                    case 2:
                                        //Usuń bieżące
                                        _frameLayout.removeView(view);
                                        listabyte.remove(index);
                                        photoCount--;
                                        break;
                                    case 3:
                                        //Zapisz wszystkie
                                        final LinearLayout layout3 = new LinearLayout(MakePhotoActivity.this);
                                        LinearLayout.LayoutParams lparams3 = new LinearLayout.LayoutParams(x*2,y*2);
                                        layout3.setLayoutParams(lparams3);
                                        layout3.setBackgroundColor(Color.parseColor("#AAFFFFFF"));

                                            Handler handlerall = new Handler();
                                            handlerall.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    SaveAllHandler(relativelayout,layout3);
                                                    Log.e("heh", "Done handler");
                                                }
                                            }, 1);
                                        relativelayout.addView(layout3);
                                        break;
                                    case 4:
                                        //Usuń wszystkie
                                        for (int j = 2; j < _frameLayout.getChildCount();){
                                            _frameLayout.removeView(_frameLayout.getChildAt(j));
                                            listabyte.remove(j-2);
                                            photoCount=0;
                                        }
                                        break;
                                }
                            }
                        });
                        alert.show();
                        return false;
                    }
                });
                _frameLayout.getChildAt(j).setX(Float.parseFloat("" + Math.floor(z)));
                _frameLayout.getChildAt(j).setY(Float.parseFloat("" + Math.floor(w)));
                //_frameLayout.addView(_frameLayout.getChildAt(j));
            }
            camera.startPreview();

        }
    };

    private void savePhoto(byte[] photobytetab){
        MainActivity newmain = new MainActivity();
        Bitmap photobitmap = Imaging.ConvertToBitmap(photobytetab);
        newmain.MakeNewFile(Prefs.getCheckedFolder(), photobitmap);
        Log.e("heh","zapisano");
    }
    private void Delete(int id){
        _frameLayout.removeView(_frameLayout.getChildAt(id+2));
        listabyte.remove(id);
        photoCount--;

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                touched=false;
                Log.e("heh",""+touched);
            }
        }, 1);
    }

    private void SaveAllHandler(RelativeLayout _relativeLayout, LinearLayout _linearLayout){
        final RelativeLayout relativeLayout = _relativeLayout;
        final LinearLayout linearLayout = _linearLayout;
        if(zmienna2>=_frameLayout.getChildCount()-2){
            int index = relativeLayout.indexOfChild(linearLayout);
            relativeLayout.removeView(relativeLayout.getChildAt(index));
            zmienna2=0;
        }else {
            savePhoto(listabyte.get(zmienna2));
            zmienna2++;
            Handler handlerall = new Handler();
            handlerall.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.e("heh", "Try"+zmienna2+" "+_frameLayout.getChildCount());
                    SaveAllHandler(relativeLayout,linearLayout);
                    Log.e("heh", "Done handler");
                }
            }, 1);
        }
    }
}


