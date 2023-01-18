package com.example.ticketeventapp.ui.main.fragment.mngevents.components;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;
import android.view.View;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.model.utils.AppPreferences;
import com.example.ticketeventapp.ui.main.activity.MainActivity;
import com.example.ticketeventapp.ui.reglog.activity.RegLogActivity;
import com.example.ticketeventapp.viewmodel.mng_events.AddEventViewModel;
import com.example.ticketeventapp.viewmodel.mng_events.EventListViewModel;
import com.google.android.material.snackbar.Snackbar;

public class EnablerDialog {

    private Activity activity;
    private AppPreferences appPreferences;

    public EnablerDialog(Activity activity){
        this.activity = activity;
        this.appPreferences = new AppPreferences(activity);
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
                        showInfoTurnedOnGPS();
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

    public void showInfoAcquisitionPosition(){
        Snackbar snackbar  =  Snackbar.make(activity.findViewById(R.id.fragment_container_view),
                R.string.load_position,
                Snackbar.ANIMATION_MODE_SLIDE);
        snackbar.show();
    }

    public void askToLogout(){
        new AlertDialog.Builder(activity).setMessage("Do you really want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        appPreferences.logoutUser();
                        Intent intent = new Intent(activity, RegLogActivity.class); //esplicitiamo la classe che andiamo a richiamare
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        activity.startActivity(intent);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).setCancelable(false).create().show();
    }

    public void showInfoSavingQrCodeIntoGallery(){
        Snackbar snackbar  =  Snackbar.make(activity.findViewById(R.id.fragment_container_view),
               R.string.saving_qr_code,
                Snackbar.ANIMATION_MODE_SLIDE);
        snackbar.show();
    }

    public void showInfoScannedQrCode(int res){
        String message = "Valid ticket";

        if(res == 0){
            Snackbar snackbar  =  Snackbar.make(activity.findViewById(R.id.fragment_container_view),
                    "Invalid Ticket",
                    Snackbar.ANIMATION_MODE_SLIDE);
             snackbar.setBackgroundTint(Color.RED);
            snackbar.setTextColor(Color.BLACK);
            snackbar.show();
        } else if(res ==2){
            Snackbar snackbar  =  Snackbar.make(activity.findViewById(R.id.fragment_container_view),
                    "Ticket already used",
                    Snackbar.ANIMATION_MODE_SLIDE);
            snackbar.setBackgroundTint(Color.RED);
            snackbar.setTextColor(Color.BLACK);
            snackbar.show();
        } else if(res == 1){
            Snackbar snackbar  =  Snackbar.make(activity.findViewById(R.id.fragment_container_view),
                    "Valid Ticket",
                    Snackbar.ANIMATION_MODE_SLIDE);
            snackbar.setBackgroundTint(Color.GREEN);
            snackbar.setTextColor(Color.BLACK);
            snackbar.show();
        } else if( res == 3) { //QR code event not found
            Snackbar snackbar  =  Snackbar.make(activity.findViewById(R.id.fragment_container_view),
                    "Event not found",
                    Snackbar.ANIMATION_MODE_SLIDE);
            snackbar.setBackgroundTint(Color.RED);
            snackbar.setTextColor(Color.BLACK);
            snackbar.show();
        }


    }

    public void askToConfirmEventDelete(EventListViewModel eventListViewModel, FragmentManager fragmentManager){
        new AlertDialog.Builder(activity).setMessage("Do you really want to delete this event and remove all tickets? ")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        fragmentManager.popBackStack();
                        fragmentManager.popBackStack();
                        eventListViewModel.deleteEvent();
                        eventListViewModel.deleteAllTicketOfEvent();

                    }
                }).setCancelable(false).create().show();
    }

    public void showInfoCantModifyEvent(){
        Snackbar snackbar  =  Snackbar.make(activity.findViewById(R.id.fragment_container_view),
                R.string.cant_modify_event,
                Snackbar.ANIMATION_MODE_SLIDE);
        snackbar.show();
    }

    public void showInfoCantScanTicket(){
        Snackbar snackbar  =  Snackbar.make(activity.findViewById(R.id.fragment_container_view),
                R.string.cant_scan_ticket,
                Snackbar.ANIMATION_MODE_SLIDE);
        snackbar.show();
    }

    public void showInfoGoogleMapsNotInstalled(){
        Snackbar snackbar  =  Snackbar.make(activity.findViewById(R.id.fragment_container_view),
                R.string.cant_use_maps_features,
                Snackbar.ANIMATION_MODE_SLIDE);
        snackbar.show();
    }

}
