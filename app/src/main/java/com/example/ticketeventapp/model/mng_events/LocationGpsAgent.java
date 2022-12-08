package com.example.ticketeventapp.model.mng_events;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.room.Ignore;

import com.example.ticketeventapp.model.utils.PermissionManager;
import com.example.ticketeventapp.viewmodel.mng_events.AddEventViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class LocationGpsAgent {

    private PermissionManager permissionManager;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;

    private Activity activity;


    public LocationGpsAgent(Activity activity, PermissionManager permissionManager) {
        this.activity = activity;
        this.permissionManager = permissionManager;
        initLocationCallBack();
        initLocationRequest();
        this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity);
    }


    private void initLocationCallBack() {
        this.locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);

                AddEventViewModel addEventViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(AddEventViewModel.class);
                //Update UI with the location data
                Location location = locationResult.getLastLocation();
                //update view model
                addEventViewModel.setPosition(location);

                //here i can send volley request to translate

                stopLocationUpdates();

            }
        };
    }

    private void initLocationRequest() {
        this.locationRequest = LocationRequest.create();
        //Set the desired interval for active location updates
        this.locationRequest.setInterval(1000);
        this.locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @SuppressLint("MissingPermission") // It's used to ignore error to do check permissions. I've already checked with isPermissionGPSAllowed
    public void startLocationUpdates() {
        if (permissionManager.isPermissionGPSAllowed()) {
            if(this.isTurnedOnGPS() && this.permissionManager.isPermissionGPSAllowed()){
                fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
            }
        }
    }

    public void stopLocationUpdates(){
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    public boolean isTurnedOnGPS(){
        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        return (locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER));
    }


    public static double getDistanceKmBetweenLocations(Location firstLocation, Location secondLocation){
        return (firstLocation.distanceTo(secondLocation))/1000;
    }






}
