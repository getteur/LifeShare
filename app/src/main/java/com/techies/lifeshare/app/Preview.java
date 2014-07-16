package com.techies.lifeshare.app;

import android.content.Context;
import android.hardware.Camera;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.IOException;

/**
 * Created by Techies on 09/07/2014.
 */
public class Preview extends SurfaceView implements  SurfaceHolder.Callback{

    SurfaceHolder mHolder;
    Camera mCamera;

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
                    // start the camera preview

                    // work with the jpeg data
                    // ...
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

        mHolder = getHolder();
        mHolder.addCallback(this);
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
        mCamera.setParameters(parameters);
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
