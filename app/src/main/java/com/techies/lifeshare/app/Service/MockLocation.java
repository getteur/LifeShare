package com.techies.lifeshare.app.Service;

import android.annotation.TargetApi;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;

/**
 * Created by Techies on 18/07/2014.
 */
public class MockLocation {

    String providerName;
    Context ctx;

    public MockLocation(String name, Context ctx) {
        this.providerName = name;
        this.ctx = ctx;
        LocationManager lm = (LocationManager) ctx.getSystemService(
                Context.LOCATION_SERVICE);
        lm.addTestProvider(providerName, false, false, false, false, false,
                true, true, 0, 5);
        lm.setTestProviderEnabled(providerName, true);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void pushLocation(double lat, double lon,double alt) {
        LocationManager lm = (LocationManager) ctx.getSystemService(
                Context.LOCATION_SERVICE);

        Location mockLocation = new Location(providerName);
        mockLocation.setLatitude(lat);
        mockLocation.setLongitude(lon);
        mockLocation.setAltitude(alt);
        mockLocation.setAccuracy(0);
        mockLocation.setTime(System.currentTimeMillis());
        mockLocation.setElapsedRealtimeNanos(5);
        try {
            lm.setTestProviderLocation(providerName, mockLocation);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void shutdown() {
        LocationManager lm = (LocationManager) ctx.getSystemService(
                Context.LOCATION_SERVICE);
        lm.removeTestProvider(providerName);
    }



}
