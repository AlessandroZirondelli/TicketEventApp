package com.example.ticketeventapp.ui.main.mngevents.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.model.mng_events.AddEventManager;
import com.example.ticketeventapp.model.mng_events.Event;
import com.example.ticketeventapp.model.mng_events.LocationGpsAgent;
import com.example.ticketeventapp.model.mng_events.NetworkAgent;
import com.example.ticketeventapp.model.utils.PermissionManager;
import com.example.ticketeventapp.ui.main.mngevents.components.DatePicker;
import com.example.ticketeventapp.ui.main.mngevents.components.EnablerDialog;
import com.example.ticketeventapp.ui.main.mngevents.components.PermissionDialog;
import com.example.ticketeventapp.ui.main.mngevents.components.TimePicker;
import com.example.ticketeventapp.viewmodel.mng_events.AddEventViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

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

    private AddEventManager addEventManager;

    private Activity activity;





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setBackgroundColor(Color.WHITE);

        activity = getActivity();
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
        addEventViewModel.clearData();
        addEventManager = new AddEventManager();

        datePicker = new DatePicker(getActivity().getSupportFragmentManager(),addEventViewModel,event_date);
        timePicker = new TimePicker(getActivity().getSupportFragmentManager(),addEventViewModel, event_time);

        event_date.setFocusable(false);
        event_time.setFocusable(false);
        




        permissionManager = new PermissionManager(getActivity(),this);
        permissionDialog = new PermissionDialog(getActivity());
        locationGpsAgent = new LocationGpsAgent(getActivity(),permissionManager);
        enablerDialog = new EnablerDialog(getActivity());
        networkAgent = new NetworkAgent(getActivity());



        addEventViewModel.getSelectedDate().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String date) {
                if(date == null){
                    event_date.setText("");
                } else{
                    event_date.setText(date);
                }
            }
        });

        addEventViewModel.getSelectedTime().observe(getActivity(), new Observer<LocalTime>() {
            @Override
            public void onChanged(LocalTime localTime) {
                if(localTime == null){
                    event_time.setText("");
                } else{
                    event_time.setText(localTime.toString());
                }
            }
        });

        addEventViewModel.getImageURI().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String uri) {
                //Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                //Log.e("AddEventFragment","ImageUriChanged:"+uri.getPath());
                if(uri == null){
                    Drawable drawable = AppCompatResources.getDrawable(activity, activity.getResources().getIdentifier("add_photo_alternate","drawable",activity.getPackageName()));
                }
                else if(!uri.equals("add_photo_alternate")){
                    Picasso.get().load(Uri.parse(uri)).fit().centerCrop()
                            .placeholder(R.drawable.add_photo_alternate)
                            .into(event_photo);
                }

            }
        });

        /*addEventViewModel.getIsPermissionGPSAllowed().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isPermissionGPSAllowed) {
                if(!isPermissionGPSAllowed){
                    permissionDialog.showInfoDeniedPermissionGPS();

                }
                Log.e("AddEventFragment","Permessi cambiati in: "+ isPermissionGPSAllowed);
            }
        });*/

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
                if(location != null){
                    event_place.setText(location.getLatitude()+ "  "+location.getLongitude());
                    Log.e("AddEventFragment",location.getLatitude()+" "+location.getLongitude());
                    networkAgent.createRequestQueue();
                    networkAgent.registerNetworkCallback();
                    networkAgent.sendVolleyRequest(String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()));
                }

            }
        });

        addEventViewModel.getPosition_display_name().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s==null){
                    event_place.setText("");
                } else {
                    event_place.setText(s);
                }
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

        event_place.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.e("Press","LongPress");
                if(permissionManager.isPermissionGPSAllowed()){ //GPS permission allowed
                    if(!locationGpsAgent.isTurnedOnGPS()){
                        Log.e("AddEventFragment","GPS is not active");
                        enablerDialog.askTurnOnGPS();
                    } else {
                        Log.e("AddEventFragment","GPS is active");
                        if(networkAgent.isConnectedToInternet()){
                            Log.e("AddEventFragment","Internet connection is active");
                            addEventViewModel.setIsTurnedOnGPS(true);
                            locationGpsAgent.startLocationUpdates();
                        } else {
                            Log.e("AddEventFragment","Internet connection is not  active");
                            enablerDialog.askToTurnOnInternetConnection();
                        }
                    }

                } else {//GPS permission denied, so ask for it
                    permissionManager.launchPermissionRequestGPS();
                }
                return true;
            }
        });

        event_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Press","ShortPress");
            }
        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = String.valueOf(event_name.getText());
                String description = String.valueOf(event_description.getText());
                String place = String.valueOf(event_place.getText());
                String date = String.valueOf(event_date.getText());
                String time = String.valueOf(event_time.getText());
                String price = String.valueOf(event_price.getText());


                int resFilled = addEventManager.areFilledFields(name,description,place,date,time,price);
                if(resFilled != 0){
                    enableEmptyFieldError(resFilled);
                } else {
                    if(!addEventManager.isPriceNumber(price)){
                        enablePriceError();
                    } else {
                        String imageUri = addEventViewModel.getImageURI().getValue();
                        if(imageUri == null){
                            imageUri = "add_photo_alternate";
                            addEventViewModel.setImageURI("add_photo_alternate");
                            Log.e("AddEventFragment","Image Uri null");

                        } else {
                            Log.e("AddEventFragment","Image Uri not null");
                            try {
                                Uri uri = Uri.parse(imageUri);
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri );
                                addEventManager.saveImage(bitmap,getActivity());
                                Log.e("AddEventFragment","Immagine salvata");
                            } catch (IOException e) {
                                Log.e("AddEventFragment","Errore salvataggio foto");
                                e.printStackTrace();
                            }

                        }
                        String latitude = "";
                        String longitude = "";
                        Location position = addEventViewModel.getPosition().getValue();
                        if(position != null){
                            Log.e("AddEventFragment","Trovate coordinate");
                            latitude = String.valueOf(position.getLatitude());
                            longitude = String.valueOf(position.getLongitude());
                        }


                        Event event = new Event(name,description,date,time,place,price, imageUri,latitude,longitude);
                        Log.e("AddEventFragment","value uri evento:"+event.getImageUri());
                        addEventViewModel.addEvent(event);
                        Log.e("AddEventFragment","Inserimento evento effettuato");
                        getFragmentManager().popBackStack();
                        addEventViewModel.clearData();

                    }
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


    private  Map<Integer,TextInputEditText> createMapIntegerErrorTextFields(){
        Map<Integer,TextInputEditText> map = new HashMap<>();
        map.put(1, event_name);
        map.put(2, event_description);
        map.put(3, event_place);
        map.put(4, event_date);
        map.put(5, event_time);
        map.put(6, event_price);
        return map;
    }

    private  void enableEmptyFieldError(int field){
        Map<Integer,TextInputEditText> map = createMapIntegerErrorTextFields();
        TextInputEditText view = map.get(field);
        view.setError(getString(R.string.empty_field));
        setFocusOutListener(view);
    }

    private void disableErrorField(TextInputEditText view){
        view.setError(null);
    }

    private void setFocusOutListener(TextInputEditText viewT){
        viewT.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    Log.e("RegistrationFragment","Perdo focus");
                    disableErrorField(viewT);
                }
            }
        });
    }

    private void enablePriceError(){
        event_price.setError(getString(R.string.price_must_be_numeric));
        setFocusOutListener(event_price);
    }




}
