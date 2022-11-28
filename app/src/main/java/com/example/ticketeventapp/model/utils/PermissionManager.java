package com.example.ticketeventapp.model.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

public class PermissionManager {

    private ActivityResultLauncher<String> requestPermissionLauncher;
    private Fragment fragment;
    private Activity activity;
    private String PERMISSION_REQUESTED = Manifest.permission.ACCESS_FINE_LOCATION;

    public PermissionManager(Activity activity, Fragment fragment){
        this.activity = activity;
        this.fragment = fragment;

        this.requestPermissionLauncher = fragment.registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) { //result ci indica se l'utente ha dato o meno il permesso
                if(result){ //se ha acconsentito possiamo iniziare a prendere la posizione
                    //startLocationUpdate(activity);
                    Log.d("ADD_FRAGMENT","PERMISSION GRANTED");
                }
                else{ //se ha rifiutato
                    //showDialog(activity);
                    Log.d("ADD_FRAGMENT","PERMISSION DENIED");
                }
            }
        });
    }

    public boolean isPermissionGPSAllowed(){
        return ActivityCompat.checkSelfPermission(activity, PERMISSION_REQUESTED) == PackageManager.PERMISSION_GRANTED;
    }

    public void launchPermissionRequestGPS(){
        requestPermissionLauncher.launch(PERMISSION_REQUESTED);
    }


}
