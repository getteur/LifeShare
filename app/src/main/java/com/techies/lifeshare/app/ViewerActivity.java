package com.techies.lifeshare.app;

import com.techies.lifeshare.app.PhotoUtils.PhotoMetaReader;
import com.techies.lifeshare.app.Service.PhotoService;
import com.techies.lifeshare.app.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class ViewerActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewer);



    }


    public void buttonOnClick(View v){
        Intent intent = new Intent(v.getContext(),PhotoActivity.class);

        startActivity(intent);
    }



    public void run(){
        Date date = PhotoMetaReader.getPhotoDate(PhotoService.getMockDrawable(this));

    }

}
