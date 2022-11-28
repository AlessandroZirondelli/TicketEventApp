package com.example.ticketeventapp.ui.mngevents.components;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.viewmodel.mng_events.AddEventViewModel;
import com.google.android.material.snackbar.Snackbar;

public class EnablerDialog {

    private Activity activity;


    public EnablerDialog(Activity activity){
        this.activity = activity;
    }

    public void askTurnOnGPS(){
        new AlertDialog.Builder(activity).setMessage(R.string.ask_turn_on_gps)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        activity.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)); //Open localization settings
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).setCancelable(false).create().show();
    }

    public void showInfoTurnedOnGPS(){
        Snackbar snackbar  =  Snackbar.make(activity.findViewById(R.id.fragment_container_view),
                R.string.gps_features_cannot_run,
                Snackbar.ANIMATION_MODE_SLIDE);
        snackbar.show();
    }

}
