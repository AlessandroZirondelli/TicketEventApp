package com.example.ticketeventapp.ui.main.fragment.mngevents.fragment;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.model.mng_events.Event;
import com.example.ticketeventapp.model.mng_tickets.Ticket;
import com.example.ticketeventapp.model.mng_tickets.TicketsManager;
import com.example.ticketeventapp.model.mng_users.User;
import com.example.ticketeventapp.model.utils.AppInfo;
import com.example.ticketeventapp.ui.main.fragment.mngtickets.TicketResultFragment;
import com.example.ticketeventapp.ui.utilities.Utilities;
import com.example.ticketeventapp.viewmodel.mng_events.EventListViewModel;
import com.example.ticketeventapp.viewmodel.mng_events.InfoTicketViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

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
    private InfoTicketViewModel infoTicketViewModel;


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
        infoTicketViewModel = new ViewModelProvider(getActivity()).get(InfoTicketViewModel.class);
        fragment_title = view.findViewById(R.id.info_add_event);
        event_photo = view.findViewById(R.id.event_icon_image_view);
        event_name = view.findViewById(R.id.event_name_text_input_edit_text);
        event_description = view.findViewById(R.id.event_description_text_input_edit_text);
        event_place = view.findViewById(R.id.event_place_text_input_edit_text);
        event_date = view.findViewById(R.id.event_date_text_input_edit_text);
        event_time = view.findViewById(R.id.event_time_text_input_edit_text);
        event_price = view.findViewById(R.id.event_price_text_input_edit_text);
        button = view.findViewById(R.id.add_event_button);

        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            fragment_title.setText(R.string.info_event);
        }
        button.setText(R.string.join_event);
        this.disableFocusOnEditText();
        setFields();

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
