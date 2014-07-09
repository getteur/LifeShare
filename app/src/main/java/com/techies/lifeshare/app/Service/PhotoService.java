package com.techies.lifeshare.app.Service;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.techies.lifeshare.app.R;

/**
 * Created by Techies on 08/07/2014.
 */
public class PhotoService {

    public static Drawable getMockDrawable(Context currentContext){
        return currentContext.getResources().getDrawable(R.drawable.mock_photo);
    }

}
