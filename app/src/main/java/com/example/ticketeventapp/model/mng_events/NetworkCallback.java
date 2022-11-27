package com.example.ticketeventapp.model.mng_events;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class NetworkCallback {

    private ConnectivityManager.NetworkCallback networkCallback;
    private Boolean isNetworkConnected;
    private Boolean requestingLocationUpdates;
    private Activity activity;

    private LocationAgent locationAgent;

    public NetworkCallback(Activity activity, Boolean isNetworkConnected, Boolean requestingLocationUpdates, Fragment fragment){
        this.activity = activity;
        this.isNetworkConnected = isNetworkConnected;
        this.requestingLocationUpdates = requestingLocationUpdates;

        locationAgent = new LocationAgent(activity,isNetworkConnected,this,requestingLocationUpdates,fragment);
        initCallBack();

    }


    private void initCallBack(){
        networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                super.onAvailable(network);
                isNetworkConnected = true;
                //snackbar.dismiss();
                if(requestingLocationUpdates) {
                    locationAgent.startLocationUpdates(activity);
                }
            }

            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);
                isNetworkConnected = false;
                //snackbar.show();
            }
        };
    }


    public void registerNetworkCallback(Activity activity){
        ConnectivityManager connectivityManager =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                connectivityManager.registerDefaultNetworkCallback(networkCallback);
            } else {
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                //isNetworkConnected = (networkInfo != null && networkInfo.isConnected());
            }
        } else {
            //isNetworkConnected = false;
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
                //snackbar.dismiss();
            }
        }
    }


}
