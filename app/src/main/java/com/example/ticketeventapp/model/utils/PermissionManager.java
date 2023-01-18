package com.example.ticketeventapp.model.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.ticketeventapp.ui.main.fragment.mngevents.components.PermissionDialog;
import com.example.ticketeventapp.viewmodel.mng_events.AddEventViewModel;

public class PermissionManager {

    private ActivityResultLauncher<String> requestGpsPermissionLauncher;
    private ActivityResultLauncher<String> requestCameraPermissionLauncher;
    private ActivityResultLauncher<String> requestStoragePermissionLauncher;
    private Fragment fragment;
    private Activity activity;
    private String PERMISSION_GPS_REQUESTED = Manifest.permission.ACCESS_FINE_LOCATION;
    private String PERMISSION_CAMERA_REQUESTED = Manifest.permission.CAMERA;
    private String PERMISSION_STORAGE_REQUESTED = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    private Boolean alreadyAskedPermissionGPS;
    private Boolean alreadyAskedPermissionStorage;

    private PermissionDialog permissionDialog;

    public PermissionManager(Activity activity, Fragment fragment){
        this.activity = activity;
        this.fragment = fragment;
        this.alreadyAskedPermissionGPS = false;
        this.alreadyAskedPermissionStorage = false;
        this.permissionDialog = new PermissionDialog(activity);

        this.requestGpsPermissionLauncher = fragment.registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) { //result ci indica se l'utente ha dato o meno il permesso
                if(result){ //permission granted
                    // Update boolean variable in ViewModel

                }
                else{ //permission denied
                     alreadyAskedPermissionGPS = true;
                     permissionDialog.showInfoDeniedPermissionGPS();
                }
                AddEventViewModel addEventViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(AddEventViewModel.class);
                addEventViewModel.setIsPermissionGPSAllowed(result);
            }
        });

        this.requestCameraPermissionLauncher = fragment.registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if(result){ //permission granted
                    // Update boolean variable in ViewModel
                }
                else{ //permission denied
                    permissionDialog.showInfoDeniedPermissionCamera();
                }

            }
        });

        this.requestStoragePermissionLauncher = fragment.registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if(result){

                } else {
                    alreadyAskedPermissionStorage = true;
                    permissionDialog.showInfoDeniedPermissionStorage();
                }
            }
        });
    }

    public boolean isPermissionGPSAllowed(){
        return ActivityCompat.checkSelfPermission(activity, PERMISSION_GPS_REQUESTED) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean isPermissionCameraAllowed(){
        return ActivityCompat.checkSelfPermission(activity, PERMISSION_CAMERA_REQUESTED) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean isPermissionStorageAllowed(){
        return ActivityCompat.checkSelfPermission(activity, PERMISSION_STORAGE_REQUESTED) == PackageManager.PERMISSION_GRANTED;
    }

    public void launchPermissionRequestGPS(){
        if(!this.alreadyAskedPermissionGPS){ //Ask only if the user has not deny yet
            requestGpsPermissionLauncher.launch(PERMISSION_GPS_REQUESTED);
        } else {
            permissionDialog.showInfoDeniedPermissionGPS();
        }
    }

    public void launchPermissionRequestCamera(){
        requestCameraPermissionLauncher.launch(PERMISSION_CAMERA_REQUESTED);
    }

    public void launchPermissionRequestStorage(){
        if(!this.alreadyAskedPermissionStorage){
            requestStoragePermissionLauncher.launch(PERMISSION_STORAGE_REQUESTED);
        } else {
            permissionDialog.showInfoDeniedPermissionStorage();
        }
    }




}
