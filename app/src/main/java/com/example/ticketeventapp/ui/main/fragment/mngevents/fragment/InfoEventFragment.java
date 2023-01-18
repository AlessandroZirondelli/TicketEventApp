package com.example.ticketeventapp.ui.main.fragment.mngevents.fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.model.mng_events.Event;
import com.example.ticketeventapp.model.mng_tickets.Ticket;
import com.example.ticketeventapp.model.mng_tickets.TicketsManager;
import com.example.ticketeventapp.model.mng_users.User;
import com.example.ticketeventapp.model.utils.AppInfo;
import com.example.ticketeventapp.ui.main.fragment.mngevents.components.EnablerDialog;
import com.example.ticketeventapp.ui.main.fragment.mngtickets.TicketResultFragment;
import com.example.ticketeventapp.ui.utilities.Utilities;
import com.example.ticketeventapp.viewmodel.mng_events.EventListViewModel;
import com.example.ticketeventapp.viewmodel.mng_tickets.TicketListViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.LocalTime;

public class InfoEventFragment extends Fragment {


    private TextView fragment_title;
    private ImageView event_photo;
    private TextInputEditText event_name;
    private TextInputEditText event_description;
    private TextInputEditText event_place;
    private TextInputEditText event_date;
    private TextInputEditText event_time;
    private TextInputEditText event_price;
    private Button button;
    private EventListViewModel eventListViewModel;
    private TicketListViewModel infoTicketViewModel;
    private ImageView event_delete;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_event, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e("Bug","Creo InfoEvemtFragment");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setBackgroundColor(Color.WHITE); // Add background color beacause it's transparent
        eventListViewModel = new ViewModelProvider(getActivity()).get(EventListViewModel.class);
        infoTicketViewModel = new ViewModelProvider(getActivity()).get(TicketListViewModel.class);
        fragment_title = view.findViewById(R.id.info_add_event);
        event_photo = view.findViewById(R.id.event_icon_image_view);
        event_name = view.findViewById(R.id.event_name_text_input_edit_text);
        event_description = view.findViewById(R.id.event_description_text_input_edit_text);
        event_place = view.findViewById(R.id.event_place_text_input_edit_text);
        event_date = view.findViewById(R.id.event_date_text_input_edit_text);
        event_time = view.findViewById(R.id.event_time_text_input_edit_text);
        event_price = view.findViewById(R.id.event_price_text_input_edit_text);
        button = view.findViewById(R.id.add_event_button);
        event_delete = view.findViewById(R.id.delete_icon_image_view);

        event_delete.setVisibility(View.INVISIBLE);

        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            fragment_title.setText(R.string.info_event);
        }
        button.setText(R.string.join_event);
        this.disableFocusOnEditText();
        setFields();


        event_place.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Event selectedEvent = eventListViewModel.getSelectedEventItem().getValue();
                String latitude = selectedEvent.getLatitude();
                String longitude = selectedEvent.getLongitude();
                String place = selectedEvent.getPlace();
                String request="";
                if(!latitude.isEmpty() && !longitude.isEmpty()){
                     request="geo:"+latitude+","+longitude+"?q="+latitude+","+longitude;

                } else {
                     request = "google.navigation:q=a+"+Uri.encode(place);
                }
                // Create a Uri from an intent string. Use the result to create an Intent.
                Uri gmmIntentUri = Uri.parse(request);
                // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                // Make the Intent explicit by setting the Google Maps package
                mapIntent.setPackage("com.google.android.apps.maps");
                // Attempt to start an activity that can handle the Intent
                if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(mapIntent);
                }else {
                    EnablerDialog enablerDialog = new EnablerDialog(getActivity());
                    enablerDialog.showInfoGoogleMapsNotInstalled();
                }
                return true;
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Event selectedEvent = eventListViewModel.getSelectedEventItem().getValue();

                User buyer = AppInfo.getInstance().getLoggedUser();
                String event_code = TicketsManager.generateRandomString();
                Ticket ticket = new Ticket(event_code, selectedEvent.getId(), buyer.getUsername(), false);
                infoTicketViewModel.addTicket(ticket);
                infoTicketViewModel.setSelectedTicket(ticket);
                Utilities.replaceFragmentOnContainer((AppCompatActivity) getActivity(),new TicketResultFragment(),TicketResultFragment.class.getSimpleName(), R.id.fragment_container_view);
            }
        });
    }


    private void disableFocusOnEditText(){
        this.event_name.setFocusable(false);
        this.event_description.setFocusable(false);
        this.event_place.setFocusable(false);
        this.event_date.setFocusable(false);
        this.event_time.setFocusable(false);
        this.event_price.setFocusable(false);
    }

    private void setFields(){
        Event selectedEvent = eventListViewModel.getSelectedEventItem().getValue();

        this.event_name.setText(selectedEvent.getName());
        this.event_date.setText(selectedEvent.getDate());
        this.event_place.setText(selectedEvent.getPlace());
        this.event_price.setText(selectedEvent.getPrice());
        this.event_time.setText(selectedEvent.getTime());
        this.event_description.setText(selectedEvent.getDescription());

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate today = LocalDate.now();
            LocalTime time = LocalTime.now();

            LocalDate eventDate = LocalDate.parse(selectedEvent.getDate());
            LocalTime eventTime = LocalTime.parse(selectedEvent.getTime());
            if((eventDate.isBefore(today)) || (eventDate.isEqual(today) && (eventTime.isBefore(time)))){
                button.setEnabled(false);
            }
        }


        String image_uri = selectedEvent.getImageUri();
        if(image_uri.contains("add_photo_alternate")){
            Drawable drawable = AppCompatResources.getDrawable(getActivity(), getActivity().getResources().getIdentifier(image_uri,"drawable",getActivity().getPackageName()));
            this.event_photo.setImageDrawable(drawable);
        } else { //User loaded a photo
            Picasso.get().load(Uri.parse(image_uri)).fit().centerCrop()
                        .placeholder(R.drawable.add_photo_alternate)
                        .into(this.event_photo);


        }
    }

}
