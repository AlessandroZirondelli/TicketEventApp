package com.example.ticketeventapp.model.mng_events;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.viewmodel.mng_events.AddEventViewModel;
import com.google.android.material.snackbar.Snackbar;

public class NetworkCallback {

    private ConnectivityManager.NetworkCallback networkCallback;
    private Boolean isNetworkConnected;
    private Boolean requestingLocationUpdates;
    private Activity activity;

    private LocationAgent locationAgent;
    private Snackbar snackbar;

    private AddEventViewModel addEventViewModel;

    public NetworkCallback(Activity activity, Boolean isNetworkConnected, Boolean requestingLocationUpdates, Fragment fragment, AddEventViewModel addEventViewModel){
        this.activity = activity;
        this.isNetworkConnected = isNetworkConnected;
        this.requestingLocationUpdates = requestingLocationUpdates;
        this.addEventViewModel= addEventViewModel;

        locationAgent = new LocationAgent(activity,this.isNetworkConnected,this,requestingLocationUpdates,fragment,addEventViewModel);
        initCallBack();
        initSnackbar();

    }


    private void initCallBack(){
        networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                super.onAvailable(network);
                isNetworkConnected = true;
                Log.e("AddEventFragment","isNetworkFragment true network callback");
                snackbar.dismiss();
                if(requestingLocationUpdates) {
                    locationAgent.startLocationUpdates(activity);
                }
            }

            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);
                isNetworkConnected = false;
                Log.e("AddEventFragment","isNetworkFragment false network callback");
                snackbar.show();
            }
        };
    }


    public void verifyConnection(){
        ConnectivityManager connectivityManager =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isNetworkConnected = (networkInfo != null && networkInfo.isConnected());

    }

    public void registerNetworkCallback(Activity activity){
        ConnectivityManager connectivityManager =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                connectivityManager.registerDefaultNetworkCallback(networkCallback);
            } else {
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                isNetworkConnected = (networkInfo != null && networkInfo.isConnected());
                Log.e("AddEventFragment","isNetworkFragment && netwrok callback :"+isNetworkConnected);
            }
        } else {
            isNetworkConnected = false;
            Log.e("AddEventFragment","isNetworkFragment false  Network callback");
        }
    }

    public void unregisterNetworkCallback(){
        if (activity != null){
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    connectivityManager.unregisterNetworkCallback(networkCallback);
                }
            } else {
                snackbar.dismiss();
            }
        }
    }

    public LocationAgent getLocationAgent(){
        return this.locationAgent;
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

    public VolleyAgent getVolleyAgent(){
        return locationAgent.getVolleyAgent();
    }

}
