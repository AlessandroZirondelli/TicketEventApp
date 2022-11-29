package com.example.ticketeventapp.ui.mngevents.fragment;

import android.content.Intent;
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
import com.example.ticketeventapp.model.mng_events.LocationGpsAgent;
import com.example.ticketeventapp.model.mng_events.NetworkAgent;
import com.example.ticketeventapp.model.utils.PermissionManager;
import com.example.ticketeventapp.ui.mngevents.components.DatePicker;
import com.example.ticketeventapp.ui.mngevents.components.EnablerDialog;
import com.example.ticketeventapp.ui.mngevents.components.PermissionDialog;
import com.example.ticketeventapp.ui.mngevents.components.TimePicker;
import com.example.ticketeventapp.viewmodel.mng_events.AddEventViewModel;
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

    private PermissionManager permissionManager;
    private PermissionDialog permissionDialog;
    private EnablerDialog enablerDialog;
    private LocationGpsAgent locationGpsAgent;
    private NetworkAgent networkAgent;





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
        event_price.setFocusable(false);

        event_place.setFocusable(false);
        permissionManager = new PermissionManager(getActivity(),this);
        permissionDialog = new PermissionDialog(getActivity());
        locationGpsAgent = new LocationGpsAgent(getActivity(),permissionManager);
        enablerDialog = new EnablerDialog(getActivity());
        networkAgent = new NetworkAgent(getActivity());



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

        addEventViewModel.getIsPermissionGPSAllowed().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isPermissionGPSAllowed) {
                if(!isPermissionGPSAllowed){
                    permissionDialog.shoInfoDeniedPermissionGPS();

                }
                Log.e("AddEventFragment","Permessi cambiati in: "+ isPermissionGPSAllowed);
            }
        });

        addEventViewModel.getIsTurnedOnGPS().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isTurnedOnGPS) {
                if(!isTurnedOnGPS){
                    Log.e("AddEventFragment","GPS si è disabilitato");
                    enablerDialog.showInfoTurnedOnGPS();
                }
            }
        });

        addEventViewModel.getPosition().observe(getActivity(), new Observer<Location>() {
            @Override
            public void onChanged(Location location) {
                event_place.setText(location.getLatitude()+ "  "+location.getLongitude());

                networkAgent.createRequestQueue();
                networkAgent.registerNetworkCallback();
                networkAgent.sendVolleyRequest(String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()));
            }
        });

        addEventViewModel.getPosition_display_name().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                event_place.setText(s);
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


        event_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(permissionManager.isPermissionGPSAllowed()){ //GPS permission allowed
                    if(!locationGpsAgent.isTurnedOnGPS()){
                        Log.e("AddEventFragment","GPS is not active");
                        enablerDialog.askTurnOnGPS();
                    } else {
                        addEventViewModel.setIsTurnedOnGPS(true);
                        Log.e("AddEventFragment","GPS is active");
                        locationGpsAgent.startLocationUpdates();


                    }

                } else {//GPS permission denied, so ask for it
                    permissionManager.launchPermissionRequestGPS();
                }
            }
        });

        event_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(networkAgent.isConnectedToInternet()){
                    Log.e("AddEventFragment","Internet connection is active");
                } else {
                    Log.e("AddEventFragment","Internet connection is not  active");
                    enablerDialog.askToTurnOnInternetConnection();
                }
            }
        });



    }


    @Override
    public void onPause() {
        super.onPause();
        locationGpsAgent.stopLocationUpdates();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(locationGpsAgent.isTurnedOnGPS()){
            addEventViewModel.setIsTurnedOnGPS(true);
            Log.e("AddEventFragment","GPS Riattivato");
        }
        if(networkAgent.isConnectedToInternet()){
            addEventViewModel.setIsConnectedToInternet(true);
            Log.e("AddEventFragment","Connessione Internet riattivata");
        }
        Log.e("AddEventFragment","OnResume");
    }

    @Override
    public void onStop() {
        super.onStop();
        networkAgent.cancelRequestQueue();
        networkAgent.unregisterNetworkCallback();
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
