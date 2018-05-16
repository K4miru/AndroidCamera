package com.example.a4ic1.projektkoncowopolroczny.Collage;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.a4ic1.projektkoncowopolroczny.Camera.CameraPreview;
import com.example.a4ic1.projektkoncowopolroczny.R;

import java.util.ArrayList;
import java.util.List;

public class CollageCameraActivity extends AppCompatActivity {

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
    private byte[] bytetab;
    private boolean photoMade=false;
    private boolean photoSave=true;
    private Camera.Parameters camParams;
    private boolean animation =true;
    private OrientationEventListener orientationEventListener;
    private int obrot =0;
    private int oldobrot =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collage_camera);
        getSupportActionBar().hide();
        makePhoto = (ImageView) findViewById(R.id.makePhotoKolaz);
        savePhoto = (ImageView) findViewById(R.id.savePhotoKolaz);
        bottomLayout = (LinearLayout) findViewById(R.id.bottomLayoutKolaz);
        topLayout = (LinearLayout) findViewById(R.id.topLayoutKolaz);
        whiteBalance = (ImageView) findViewById(R.id.whiteBalanceKolaz);
        pictureSizes = (ImageView) findViewById(R.id.pictureSizesKolaz);
        exposureCompensation = (ImageView) findViewById(R.id.exposureCompensationKolaz);
        colorEffects = (ImageView) findViewById(R.id.colorEffectsKolaz);

        initCamera();
        initPreview();
        camParams = camera.getParameters();
        orientationEventListener = new OrientationEventListener(CollageCameraActivity.this) {
            @Override
            public void onOrientationChanged(int i) {

                //showToast(""+i);
                oldobrot = obrot;
                if (i > 55 && i < 125) {
                    obrot = 0;
                } else if (i > 145 && i < 215) {
                    obrot = 3;
                } else if (i > 235 && i < 305) {
                    obrot = 2;
                } else if (i > 325 || i < 35) {
                    obrot = 1;
                }
                if (oldobrot != obrot) {
                    Toast.makeText(CollageCameraActivity.this, "" + obrot, Toast.LENGTH_SHORT).show();
                    for (int j = 2; j < _frameLayout.getChildCount(); j++) {
                        ObjectAnimator.ofFloat(_frameLayout.getChildAt(j), View.ROTATION, (obrot + 1) * 90, (obrot + 2) * 90 + 90)
                                .setDuration(300)
                                .start();
                    }
                    for (int j = 0; j < bottomLayout.getChildCount(); j++) {
                        ObjectAnimator.ofFloat(bottomLayout.getChildAt(j), View.ROTATION, (obrot + 1) * 90, (obrot + 2) * 90 + 90)
                                .setDuration(300)
                                .start();
                    }
                    for (int j = 0; j < topLayout.getChildCount(); j++) {
                        ObjectAnimator.ofFloat(topLayout.getChildAt(j), View.ROTATION, (obrot + 1) * 90, (obrot + 2) * 90 + 90)
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
                camera.takePicture(null, null, camPictureCallback);
                photoMade = true;
            }
        });

        savePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(photoMade) {
                    Intent intent = new Intent();
                    intent.putExtra("fotodata", bytetab);
                    setResult(300, intent);   // 300 - jw
                    finish();
                }
            }
        });

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

                final AlertDialog.Builder alert = new AlertDialog.Builder(CollageCameraActivity.this);
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
                AlertDialog.Builder alert = new AlertDialog.Builder(CollageCameraActivity.this);
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
                AlertDialog.Builder alert = new AlertDialog.Builder(CollageCameraActivity.this);
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

                AlertDialog.Builder alert = new AlertDialog.Builder(CollageCameraActivity.this);
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
        _cameraPreview = new CameraPreview(CollageCameraActivity.this, camera);
        _frameLayout = (FrameLayout) findViewById(R.id.frameLayoutKolaz);
        _frameLayout.addView(_cameraPreview);
    }

    private Camera.PictureCallback camPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            bytetab = data;
            camera.startPreview();
        }
    };
}
