package com.example.ticketeventapp.model.mng_events;

import android.app.Activity;
import android.util.Log;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ticketeventapp.viewmodel.mng_events.AddEventViewModel;

import org.json.JSONException;
import org.json.JSONObject;

public class VolleyAgent {

    private final static String OSM_REQUEST_TAG = "OSM_REQUEST";

    private NetworkCallback networkCallback;
    private RequestQueue requestQueue;
    private AddEventViewModel addEventViewModel;



    public VolleyAgent (Activity activity, NetworkCallback networkCallback, AddEventViewModel addEventViewModel){
       this.networkCallback = networkCallback;
       this.requestQueue = Volley.newRequestQueue(activity);
       this.addEventViewModel = addEventViewModel;
    }

    public  void sendVolleyRequest(String latitude, String longitude) {
        String url = "https://nominatim.openstreetmap.org/reverse?lat=" + latitude +
                "&lon="+ longitude + "&format=jsonv2&limit=1";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            //placeTIET.setText(response.get("name").toString());
                            //descriptionTIET.setText(response.get("display_name").toString());

                            addEventViewModel.setLocationDisplayName(response.get("display_name").toString());

                            networkCallback.unregisterNetworkCallback();
                        } catch (JSONException e) {
                            //placeTIET.setText("/");
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("LAB-ADDFRAGMENT", error.toString());
                    }
                });

        jsonObjectRequest.setTag(OSM_REQUEST_TAG);
        requestQueue.add(jsonObjectRequest);
    }

    public RequestQueue getRequestQueue(){
        return this.requestQueue;
    }


}
