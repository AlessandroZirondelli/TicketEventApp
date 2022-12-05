package com.example.ticketeventapp.ui.main.mngevents.components;

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

    /*public void askToTurnOnInternetConnection(){
        Snackbar snackbar = Snackbar.make(activity.findViewById(R.id.fragment_container_view),
                "No internet available",
                Snackbar.LENGTH_INDEFINITE).setAction("Settings", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_WIRELESS_SETTINGS);
                //mi si apre la scehrmata al di fuori della nostra applicazione. Nel caso del settings gps, mi si apriva ma in alto a sinistra avevo il tasto indietro per poter tornare all'app. In questo caso qui è proprio un apertura esterna.
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
            }
        });

    }*/

    public void askToTurnOnInternetConnection(){
        new AlertDialog.Builder(activity).setMessage("The Internet connection is off. Do you want to enable it?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_WIRELESS_SETTINGS);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivity(intent);
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

    public void showInfoTurnedOnInternetConnection(){
        Snackbar snackbar  =  Snackbar.make(activity.findViewById(R.id.fragment_container_view),
                "Some features can't run because your Internet connection is off",
                Snackbar.ANIMATION_MODE_SLIDE);
        snackbar.show();
    }

}
