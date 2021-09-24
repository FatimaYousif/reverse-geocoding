package com.example.location_broadcast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {

    Button b,broadcast;
    TextView location;
    Double longitude, latitude;

    //CLIENT -- location service provider
    FusedLocationProviderClient fusedLocationProviderClient;


    //broadcast receiver
    Broadcast broadcastreceiver=new Broadcast();

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter=new IntentFilter("com.example.location_broadcast.broadcast");
        registerReceiver(broadcastreceiver,intentFilter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b = (Button) findViewById(R.id.click);
        broadcast = (Button) findViewById(R.id.broadcast);

        location = (TextView) findViewById(R.id.location);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        fusedLocationProviderClient.getLastLocation().
                                addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                                    @Override
                                    public void onSuccess(Location location1) {
                                        if (location1 != null) {
                                             latitude = location1.getLatitude();
                                             longitude = location1.getLongitude();
                                            location.setText(latitude + " " + longitude);
                                            Toast.makeText(MainActivity.this, "success", Toast.LENGTH_LONG).show();
                                        } else {
                                            location.setText("null");
                                        }
                                    }
                                });
                    }
                    else
                    {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
                    }
                }
            }});



        //for broadcast btn
        broadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent i = new Intent("com.example.location_broadcast.broadcast");
                Bundle params = new Bundle();
                params.putDouble("longitude", longitude);
                params.putDouble("latitude", latitude);
                i.putExtras(params);
                    sendBroadcast(i);

            }
        });
    }

}