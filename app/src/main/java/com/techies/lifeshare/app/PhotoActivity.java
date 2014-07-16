package com.techies.lifeshare.app;

import com.techies.lifeshare.app.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

import com.techies.lifeshare.app.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class PhotoActivity extends Activity {

    private Preview mPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide the window title.
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Create our Preview view and set it as the content of our activity.
        mPreview = new Preview(this);
        setContentView(mPreview);

    }


}
