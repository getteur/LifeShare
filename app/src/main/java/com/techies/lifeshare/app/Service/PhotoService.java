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
import android.os.Bundle;
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
    private OrientationService orientationService;

    private static final String TAG = "PhotoService";

    public PhotoService(Context ctx){

        context = ctx;
        orientationService = new OrientationService(context);//lancer les sensor listeners avant de récupérer les valeurs d'orientation de l'appareil
        //il faudra aussi lancer les location listeners lorsqu'on n'aura plus besoin de mockLocation...
    }

    public void savePhoto(Bitmap photo){
        String photoPath = getPhotoPath(photo);
        postProcess(photoPath);
        Log.e("","");
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
            Date photoDate = getDate();
            double[] photoCoordinates = getGPSCoordinates();
            float[] photoOrientation = getOrientation();
            Bundle metaBundle = new Bundle();
            metaBundle.putString("photoDate",photoDate.toString());
            metaBundle.putDoubleArray("photoCoordinates", photoCoordinates);
            metaBundle.putFloatArray("photoOrientation",photoOrientation);
            PhotoMetaWriter photoMetaWriter = new PhotoMetaWriter();
            photoMetaWriter.metaWrite(photoFile,metaBundle);
            Log.e("","");
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
        double[] coordinates = new double[3];
        if(currentLocation != null){
            coordinates[0] = currentLocation.getLatitude();
            coordinates[1] = currentLocation.getLongitude();
            coordinates[2] = currentLocation.getAltitude();
        }
        return coordinates;
    }

    private float[] getOrientation(){

        return orientationService.getOrientationValues();

    }


}
