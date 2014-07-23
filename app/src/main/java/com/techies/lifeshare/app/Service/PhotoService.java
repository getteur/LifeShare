package com.techies.lifeshare.app.Service;

import android.content.Context;
import android.graphics.drawable.Drawable;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.techies.lifeshare.app.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Techies on 08/07/2014.
 */
public class PhotoService extends Activity{

    protected Context context;

    private static final String TAG = "PhotoService";

    public PhotoService(Context ctx){
        context = ctx;
    }

    public void savePhoto(Bitmap photo){
        String photoPath = getPhotoPath(photo);
        postProcess(photoPath);
    }

    public static Drawable getMockDrawable(Context currentContext){
        return currentContext.getResources().getDrawable(R.drawable.mock_photo);
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

    private void postProcess(String photoFilePath){
        File photoFile = new File(photoFilePath);
        if(photoFile.exists()) {
            Date datePhoto = getDate();
            double[] photoCoordinates = getGPSCoordinates();
            float[] photoOrientation = getOrientation();
            try {
                ExifInterface exifPhoto = new ExifInterface(photoFile.getName());
                exifPhoto.setAttribute(ExifInterface.TAG_DATETIME,datePhoto.toString());
                exifPhoto.setAttribute(ExifInterface.TAG_GPS_LATITUDE,String.valueOf(photoCoordinates[0]));
                exifPhoto.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, String.valueOf(photoCoordinates[1]));
                exifPhoto.saveAttributes();

                File tempFile = new File(getAlbumStorageDir("LifeShare").getAbsolutePath()+"/"+photoFile.getName());
                photoFile.renameTo(tempFile);

                Log.e("", "");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getPhotoPath(Bitmap photo){
        // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
        Uri tempUri = getImageUri(context, photo);
        // CALL THIS METHOD TO GET THE ACTUAL PATH
        String photoPath = getRealPathFromURI(tempUri);
        return photoPath;
    }

    private Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private String getRealPathFromURI(Uri uri) {
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    private Date getDate(){
        Date date = new Date();
        return date;
    }

    private double[] getGPSCoordinates() {
        Location currentLocation = LocationService.getInstance(context).getCurrentLocation();
        double[] coordinates = new double[2];
        if(currentLocation != null){
            coordinates[0] = currentLocation.getLatitude();
            coordinates[1] = currentLocation.getLongitude();
        }
        return coordinates;
    }

    private float[] getOrientation(){
        SensorManager sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);

        final float[] mValuesMagnet      = new float[3];
        final float[] mValuesAccel       = new float[3];
        final float[] mValuesOrientation = new float[3];
        final float[] mRotationMatrix    = new float[9];

        final SensorEventListener mEventListener = new SensorEventListener() {
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }

            public void onSensorChanged(SensorEvent event) {
                // Handle the events for which we registered
                switch (event.sensor.getType()) {
                    case Sensor.TYPE_ACCELEROMETER:
                        System.arraycopy(event.values, 0, mValuesAccel, 0, 3);
                        break;

                    case Sensor.TYPE_MAGNETIC_FIELD:
                        System.arraycopy(event.values, 0, mValuesMagnet, 0, 3);
                        break;
                }
            };
        };

        setListeners(sensorManager, mEventListener);

        SensorManager.getRotationMatrix(mRotationMatrix, null, mValuesAccel, mValuesMagnet);
        SensorManager.getOrientation(mRotationMatrix, mValuesOrientation);

        return mValuesOrientation;
    }

    private void setListeners(SensorManager sensorManager, SensorEventListener mEventListener)
    {
        sensorManager.registerListener(mEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(mEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_NORMAL);
    }
}
