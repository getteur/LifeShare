package com.techies.lifeshare.app.PhotoUtils;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.media.ExifInterface;

import com.techies.lifeshare.app.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Techies on 08/07/2014.
 */
public class PhotoMetaReader {

    public static  void getPhotoCoordinates(Drawable photo){

    }

    public static Date getPhotoDate(Drawable photo){
        //String imageUri = "drawable://" + R.drawable.mock_photo;
       // String imageUri = "C:\\Users\\Techies\\.AndroidStudioBeta\\LifeShare\\app\\src\\main\\res\\drawable-hdpi\\mock_photo.jpg";
        String imageUri = "C:\\Users\\Techies\\.AndroidStudioBeta\\LifeShare\\app\\src\\main\\res\\drawable-hdpi\\mock_photo.jpg";
        ExifInterface exif = null;
        try{
            exif = new ExifInterface(imageUri);
            SimpleDateFormat  format = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm");
            Object obj = exif.TAG_DATETIME;
            Date date = format.parse(exif.TAG_DATETIME);
            System.out.println(date);
            return date;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
