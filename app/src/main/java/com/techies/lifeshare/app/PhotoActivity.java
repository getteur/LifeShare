package com.techies.lifeshare.app;

import com.techies.lifeshare.app.Service.OrientationService;
import com.techies.lifeshare.app.Service.PhotoService;
import com.techies.lifeshare.app.util.SystemUiHider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Window;

import java.io.ByteArrayOutputStream;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class PhotoActivity extends Activity {

    private static final String TAG = "PhotoActivity";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private PhotoService photoService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide the window title.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Create our Preview view and set it as the content of our activity.


        photoService = new PhotoService(this);

        Intent mediaChooser =  new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(mediaChooser, REQUEST_IMAGE_CAPTURE);
        setContentView(R.layout.activity_photo);

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            try {
                Bitmap photo = (Bitmap) extras.get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                photoService.savePhoto(photo);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


}
