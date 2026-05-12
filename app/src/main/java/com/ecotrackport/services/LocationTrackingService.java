package com.ecotrackport.services;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class LocationTrackingService extends Service {
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private static final int UPDATE_INTERVAL = 10000; // 10 seconds

    @Override
    public void onCreate() {
        super.onCreate();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        setupLocationUpdates();
    }

    private void setupLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                for (Location location : locationResult.getLocations()) {
                    // Broadcast location update
                    Intent intent = new Intent("location_update");
                    intent.putExtra("latitude", location.getLatitude());
                    intent.putExtra("longitude", location.getLongitude());
                    intent.putExtra("accuracy", location.getAccuracy());
                    sendBroadcast(intent);
                }
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startLocationUpdates();
        return START_STICKY;
    }

    private void startLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);

        try {
            fusedLocationProviderClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    Looper.getMainLooper()
            );
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (fusedLocationProviderClient != null && locationCallback != null) {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
