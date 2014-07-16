package com.techies.lifeshare.app;

import android.content.Context;
import android.hardware.Camera;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Techies on 09/07/2014.
 */
public class Preview extends SurfaceView implements  SurfaceHolder.Callback{
    static final String TAG = "Preview";

    SurfaceHolder mHolder;
    Camera mCamera;
    protected static final int MEDIA_TYPE_IMAGE = 0;
    Context currentContext;

    private Camera.ShutterCallback shutterCallback =
            new Camera.ShutterCallback() {
                public void onShutter() {
                    // handle shutter done
                    // ...
                }
            };

    private Camera.PictureCallback rawCallback =
            new Camera.PictureCallback() {
                public void onPictureTaken(byte[] data, Camera c) {
                    // work with raw data
                    // ...
                }
            };

    private Camera.PictureCallback jpegCallback =
            new Camera.PictureCallback() {
                public void onPictureTaken(byte[] data, Camera c) {
                    try {

                        File file = new File(getAlbumStorageDir("LifeShare"), "testPhoto.jpg");

                        file.createNewFile();

                        FileOutputStream outputStream = new FileOutputStream(file);
                        outputStream.write(data);
                        outputStream.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            };
    /**
     * Ensure it is supported by adding
     * android.hardware.camera.autofocus feature
     * to the application manifest
     */
    private Camera.AutoFocusCallback autoFocusCallback =
            new Camera.AutoFocusCallback() {
                public void onAutoFocus(boolean success, Camera camera) {
                    // handle focus done
                    // you can choose to take a picture
                    // after auto focus is completed
                    camera.takePicture(shutterCallback, rawCallback, jpegCallback);
                }
            };

    Preview(Context context) {
        super(context);
        currentContext = context;

        mHolder = getHolder();
        mHolder.addCallback(this);
    }

    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), albumName);
        if (!file.mkdirs()) {
            Log.e(TAG, "Directory not created");
        }
        return file;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mCamera = Camera.open();
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException exception) {
            mCamera.release();
            mCamera = null;

        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.stopPreview();
        mCamera = null;
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setPreviewSize(w, h);
//        mCamera.setParameters(parameters);
        mCamera.startPreview();
    }

    public void takePhoto() {
        // take a photo :
        // 1 - auto focus
        // 2 - take the picture in the auto focus callback
        mCamera.autoFocus(autoFocusCallback);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        takePhoto();
        return true;
    }
}
