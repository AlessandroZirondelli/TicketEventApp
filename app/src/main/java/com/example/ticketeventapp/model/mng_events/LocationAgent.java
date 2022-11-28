package com.example.ticketeventapp.model.mng_events;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.viewmodel.mng_events.AddEventViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.snackbar.Snackbar;

public class LocationAgent {

    Boolean isNetworkConnected;
    public  Boolean requestingLocationUpdates;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;

    private VolleyAgent volleyAgent;
    private NetworkCallback networkCallback;

    private ActivityResultLauncher<String> requestPermissionLauncher;

    private Snackbar snackbar;
    private Activity activity;

    private AddEventViewModel addEventViewModel;

    public LocationAgent(Activity activity, Boolean isNetworkConnected, NetworkCallback networkCallback, Boolean req, Fragment fragment, AddEventViewModel addEventViewModel){

        this.addEventViewModel = addEventViewModel;
        this.activity = activity;
        initSnackbar();
        this.requestingLocationUpdates = req;
        this.networkCallback = networkCallback;
        this.volleyAgent = new VolleyAgent(activity,networkCallback,addEventViewModel);

        this.isNetworkConnected = isNetworkConnected;
        Log.e("AddEventFragment","Inizializzo isNetworkConnected a: "+isNetworkConnected);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity);
        locationRequest = LocationRequest.create();
        //Set the desired interval for active location updates
        locationRequest.setInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);

                //Update UI with the location data
                Location location = locationResult.getLastLocation();
                addEventViewModel.setLocation(location);
                /*String text = location.getLatitude() + ", " + location.getLongitude();
                placeTIET.setText(text);*/

                if (isNetworkConnected) {
                    volleyAgent.sendVolleyRequest(String.valueOf(location.getLatitude()),
                            String.valueOf(location.getLongitude()));

                    requestingLocationUpdates = false;
                    stopLocationUpdates();
                } else {
                    snackbar.show();
                    Log.e("AddEventFragment","isNetworkFragment false LocationAgent");
                }
            }
        };

        this.requestPermissionLauncher = fragment.registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean result) {
                        if (result) {
                            startLocationUpdates(activity);
                            Log.e("LAB-ADDFRAGMENT", "PERMISSION GRANTED");
                        } else {
                            Log.e("LAB-ADDFRAGMENT", "PERMISSION NOT GRANTED");
                            showDialog(activity);
                            Log.e("AddEventFragment","isNetworkFragment false location agent");
                        }
                    }
                });



    }


    private void stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }


    public void startLocationUpdates(Activity activity) {
        final String PERMISSION_REQUESTED = Manifest.permission.ACCESS_FINE_LOCATION;
        //permission granted
        if (ActivityCompat.checkSelfPermission(activity, PERMISSION_REQUESTED)
                == PackageManager.PERMISSION_GRANTED) {
            this.checkStatusGPS(activity);
            fusedLocationProviderClient.requestLocationUpdates(locationRequest,
                    locationCallback,
                    Looper.getMainLooper());
        } else if (ActivityCompat
                .shouldShowRequestPermissionRationale(activity, PERMISSION_REQUESTED)) {
            //permission denied before
            showDialog(activity);
        } else {
            //ask for the permission
            requestPermissionLauncher.launch(PERMISSION_REQUESTED);
        }

    }

    private void checkStatusGPS(Activity activity) {
        final LocationManager locationManager =
                (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        //if gps is off, show the alert message
        if (locationManager != null && !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            new AlertDialog.Builder(activity)
                    .setMessage("Your GPS is off, do you want to enable it?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", ((dialogInterface, i) ->
                            activity.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))))
                    .setNegativeButton("No", (dialogInterface, i) -> dialogInterface.cancel())
                    .create()
                    .show();
        }
    }

    private void initSnackbar(){
        snackbar = Snackbar.make(activity.findViewById(R.id.fragment_container_view),
                        "No Internet Available", Snackbar.LENGTH_INDEFINITE)
                .setAction("Settings", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_WIRELESS_SETTINGS);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivity(intent);
                    }
                });
    }

    private void showDialog(Activity activity) {
        new AlertDialog.Builder(activity)
                .setMessage("Permission denied, but needed for gps functionality.")
                .setCancelable(false)
                .setPositiveButton("OK", ((dialogInterface, i) ->
                        activity.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))))
                .setNegativeButton("Cancel", ((dialogInterface, i) -> dialogInterface.cancel()))
                .create()
                .show();
    }

    public VolleyAgent getVolleyAgent(){
        return this.volleyAgent;
    }

}