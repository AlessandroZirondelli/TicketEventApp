package com.example.ticketeventapp.ui.mngevents.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.model.mng_events.LocationAgent;
import com.example.ticketeventapp.model.mng_events.NetworkCallback;
import com.example.ticketeventapp.ui.mngevents.components.DatePicker;
import com.example.ticketeventapp.ui.mngevents.components.TimePicker;
import com.example.ticketeventapp.viewmodel.mng_events.AddEventViewModel;
import com.example.ticketeventapp.viewmodel.mng_users.UsersViewModelRegLog;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalTime;

public class AddEventFragment extends Fragment {

    private AddEventViewModel addEventViewModel;

    private TextView fragment_title;
    private ImageView event_photo;
    private TextInputEditText event_name;
    private TextInputEditText event_description;
    private TextInputEditText event_place;
    private TextInputEditText event_date;
    private TextInputEditText event_time;
    private TextInputEditText event_price;
    private Button button;

    private DatePicker datePicker;
    private TimePicker timePicker;



    private Boolean requestingLocationUpdates = false;
    private Boolean isNetworkConnected = false;
    private final static String OSM_REQUEST_TAG = "OSM_REQUEST";

    private NetworkCallback networkCallback;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragment_title = view.findViewById(R.id.info_add_event);
        event_photo = view.findViewById(R.id.event_icon_image_view);
        event_name = view.findViewById(R.id.event_name_text_input_edit_text);
        event_description = view.findViewById(R.id.event_description_text_input_edit_text);
        event_place = view.findViewById(R.id.event_place_text_input_edit_text);
        event_date = view.findViewById(R.id.event_date_text_input_edit_text);
        event_time = view.findViewById(R.id.event_time_text_input_edit_text);
        event_price = view.findViewById(R.id.event_price_text_input_edit_text);
        button = view.findViewById(R.id.add_event_button);

        addEventViewModel = new ViewModelProvider(getActivity()).get(AddEventViewModel.class);

        datePicker = new DatePicker(getActivity().getSupportFragmentManager(),addEventViewModel);
        timePicker = new TimePicker(getActivity().getSupportFragmentManager(),addEventViewModel);

        event_date.setFocusable(false);
        event_time.setFocusable(false);



        addEventViewModel.getSelectedDate().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String date) {
                event_date.setText(date);
            }
        });

        addEventViewModel.getSelectedTime().observe(getActivity(), new Observer<LocalTime>() {
            @Override
            public void onChanged(LocalTime localTime) {
                event_time.setText(localTime.toString());
            }
        });

        addEventViewModel.getImageURI().observe(getActivity(), new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                event_photo.setImageURI(uri);
            }
        });

        /*addEventViewModel.getLocation().observe(getActivity(), new Observer<Location>() {
            @Override
            public void onChanged(Location location) {
                event_place.setText(location.getLatitude()+" "+ location.getLongitude());
            }
        });*/

        addEventViewModel.getLocationDisplayName().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                event_place.setText(s);
                Log.e("AddEventFragment","val tradotto:"+s);
            }
        });
        

        event_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.show();
            }
        });

        event_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePicker.show();
            }
        });

        event_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });



        networkCallback = new NetworkCallback(getActivity(),isNetworkConnected,requestingLocationUpdates, this, addEventViewModel);

        event_place.setFocusable(false);
        event_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestingLocationUpdates = true;
                networkCallback.registerNetworkCallback(getActivity());
                networkCallback.getLocationAgent().startLocationUpdates(getActivity());
            }
        });


    }


    @Override
    public void onStart() {
        super.onStart();
        if (getActivity() != null && requestingLocationUpdates){
            networkCallback.registerNetworkCallback(getActivity());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (requestingLocationUpdates && getActivity() != null){
            networkCallback.getLocationAgent().startLocationUpdates(getActivity());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        networkCallback.getLocationAgent().startLocationUpdates(getActivity());
    }

    @Override
    public void onStop() {
        super.onStop();
        if(networkCallback.getVolleyAgent().getRequestQueue() != null){
            networkCallback.getVolleyAgent().getRequestQueue() .cancelAll(OSM_REQUEST_TAG);
        }

        if (requestingLocationUpdates)
            networkCallback.unregisterNetworkCallback();
    }



    private void imageChooser(){
        int CHOOSE_PICTURE = 200;
        Intent choosePicture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);

        if (choosePicture.resolveActivity(getActivity().getPackageManager()) != null) {
            getActivity().startActivityForResult(choosePicture, CHOOSE_PICTURE);
        } else {
            Log.e("AddEventFragment","No resolver of specific choose picture intent");
        }

    }

}
