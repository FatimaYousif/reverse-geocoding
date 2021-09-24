package com.example.location_broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Broadcast extends BroadcastReceiver {

    Geocoder geocoder;
    double longitude, latitude;
    @Override
    public void onReceive(Context context, Intent intent) {
        if("com.example.location_broadcast.broadcast".equals(intent.getAction())) {
                Bundle params = intent.getExtras();
                if  (params != null)
                {
                    longitude = params.getDouble("longitude");
                    latitude = params.getDouble("latitude");
                }

            try {
                geocoder = new Geocoder(context, Locale.getDefault());
               List<Address> addressList= geocoder.getFromLocation(latitude, longitude, 1);
               Address address=addressList.get(0);
                Toast.makeText(context, "address= "+address.toString(), Toast.LENGTH_LONG).show();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
