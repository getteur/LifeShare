package com.techies.lifeshare.app;

import com.techies.lifeshare.app.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class DisplayActivity extends Activity {

    private Preview mPreview;


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hide the window title.
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // CAMERA PREVIEW
        mPreview = new Preview(this);

        FrameLayout previewLayout = new FrameLayout(this);


        // Create camera layout params
        LinearLayout.LayoutParams previewLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, Gravity.LEFT);


        previewLayout.addView(mPreview, 0);

        Bitmap myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(myBitmap);
        previewLayout.addView(imageView, 50, 50);
        imageView.setTranslationX(100);
        imageView.setTranslationY(100);


        //Add previewLayout to main layout
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.addView(previewLayout, previewLayoutParams);

        setContentView(linearLayout);


    }


}