package com.example.ticketeventapp.ui.main.fragment.mngevents.components;

import android.app.Activity;

import com.example.ticketeventapp.R;
import com.google.android.material.snackbar.Snackbar;

public class PermissionDialog {

    private Activity activity;

    public PermissionDialog(Activity activity){
        this.activity = activity;
    }

    public void showInfoDeniedPermissionGPS(){
        Snackbar snackbar  =  Snackbar.make(activity.findViewById(R.id.fragment_container_view),
                R.string.gps_features_disabled,
                Snackbar.ANIMATION_MODE_SLIDE);
                snackbar.show();
    }


}
