package com.techies.lifeshare.app.Service;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.techies.lifeshare.app.R;

/**
 * Created by Techies on 23/07/2014.
 */
public class LocationService implements LocationListener {
    protected LocationManager locationManager;
    protected LocationListener locationListener;

    private static Location currentLocation = null;
    private static LocationService instance = null;
    private static Context currentContext = null;

    public static LocationService getInstance(Context context){
        currentContext = context;
        if(instance == null)
            instance = new LocationService();
        return instance;
    }

    private LocationService(){
        locationManager = (LocationManager) currentContext.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        MockLocation mock = new MockLocation(LocationManager.GPS_PROVIDER, currentContext);
        mock.pushLocation(-12.34, 23.45);
    }

    public Location getCurrentLocation(){
        return currentLocation;
    }

    public void onLocationChanged(Location location) {
        currentLocation = location;
    }

    public void onProviderDisabled(String provider) {
        Log.d("Latitude", "disable");
    }


    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");

    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }
}
