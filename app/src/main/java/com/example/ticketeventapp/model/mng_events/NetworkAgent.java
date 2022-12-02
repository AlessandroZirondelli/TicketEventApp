package com.example.ticketeventapp.model.mng_events;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ticketeventapp.viewmodel.mng_events.AddEventViewModel;

import org.json.JSONException;
import org.json.JSONObject;

public class NetworkAgent {

    private final static  String OSM_REQUEST_TAG = "OSM_REQUEST";
    private RequestQueue requestQueue;
    private ConnectivityManager.NetworkCallback networkCallback;
    private boolean isNetworkConnected;
    private ConnectivityManager connectivityManager;

    private Activity activity;

    public NetworkAgent(Activity activity){
        this.connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        this.activity = activity;
        this.initNetworkCallback();
        this.isNetworkConnected = false;
    }


    private void initNetworkCallback(){
        AddEventViewModel addEventViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(AddEventViewModel.class);
        networkCallback = new ConnectivityManager.NetworkCallback(){
            @Override
            public void onAvailable(@NonNull Network network) {
                super.onAvailable(network);
                //Available connection, Update ViewModel
                isNetworkConnected = true;
                addEventViewModel.setIsConnectedToInternet(true);

            }

            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);
                //Lost connection, Update ViewModel
                isNetworkConnected = false;
                addEventViewModel.setIsConnectedToInternet(true);
            }
        };
    }

    public void  createRequestQueue(){
        requestQueue = Volley.newRequestQueue(activity);
        if(requestQueue!=null){
            Log.e("AddEventFragment", "Non null event queue");
        }
    }

    public void cancelRequestQueue(){
       if(requestQueue != null){requestQueue.cancelAll(OSM_REQUEST_TAG);}
    }

    public boolean isConnectedToInternet(){
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }


    public void registerNetworkCallback(){
        if(connectivityManager != null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Log.e("AddEventFragment","Register callback");
                connectivityManager.registerDefaultNetworkCallback(networkCallback);
             }
        }else { //No connection
            isNetworkConnected = false;
        }
    }

    public void unregisterNetworkCallback(){
        if(connectivityManager != null){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                    Log.e("AddEventFragment","Unregister callback");
                    connectivityManager.unregisterNetworkCallback(networkCallback);
                }
        }
    }

    public void sendVolleyRequest(String latitude, String longitude){
        AddEventViewModel addEventViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(AddEventViewModel.class);
        String url = "https://nominatim.openstreetmap.org/reverse?lat="+latitude+"&lon="+longitude+"&format=jsonv2&limit=1";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject address = (JSONObject) response.get("address");
                    String road = address.get("road").toString();
                    if(address.has("house_number")){
                        Log.e("AddEventFragment","Campo presente");
                        road = road+" "+address.get("house_number");
                    } //check if fields exists
                    //String house_number = address.get("house_number").toString();
                    //addEventViewModel.setPosition_display_name(road+" "+house_number);

                    addEventViewModel.setPosition_display_name(road);

                    unregisterNetworkCallback();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Add fragment",error.toString());
            }
        }
        );
        jsonObjectRequest.setTag(OSM_REQUEST_TAG);
        requestQueue.add(jsonObjectRequest);
    }



}
