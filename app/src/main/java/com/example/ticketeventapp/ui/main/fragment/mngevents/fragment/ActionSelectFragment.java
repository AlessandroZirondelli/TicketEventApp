package com.example.ticketeventapp.ui.main.fragment.mngevents.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.model.mng_events.Event;
import com.example.ticketeventapp.ui.main.fragment.home.fragment.RecyclerViewJoinersFragment;
import com.example.ticketeventapp.ui.main.fragment.mngevents.components.EnablerDialog;
import com.example.ticketeventapp.ui.main.fragment.qr_code_scanner.QrCodeScannerFragment;
import com.example.ticketeventapp.ui.utilities.Utilities;
import com.example.ticketeventapp.viewmodel.mng_events.EventListViewModel;

import java.time.LocalDate;
import java.time.LocalTime;

import okhttp3.internal.Util;

public class ActionSelectFragment extends Fragment {

    private Activity activity;
    private ImageView scan;
    private ImageView joiners;
    private ImageView modify;
    private EventListViewModel eventListViewModel;
    private EnablerDialog enablerDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_action_select, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventListViewModel =  new ViewModelProvider(getActivity()).get(EventListViewModel.class);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                Log.e("HomeFrament","Turn indrì action select");
                eventListViewModel.setSelectedEventItem(null);
                getFragmentManager().popBackStack();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setBackgroundColor(Color.WHITE); // Add background color because it's transparent
        activity = getActivity();

        enablerDialog = new EnablerDialog(activity);

        scan = view.findViewById(R.id.scan_qrcode_image_view);
        modify = view.findViewById(R.id.edit_event_image_view);
        joiners = view.findViewById(R.id.view_joiners_image_view);


        joiners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.replaceFragmentOnContainer((AppCompatActivity) activity,new RecyclerViewJoinersFragment(),RecyclerViewJoinersFragment.class.getSimpleName(), R.id.fragment_container_view);
            }
        });

        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    Event selectedEvent = eventListViewModel.getSelectedEventItem().getValue();

                    if(selectedEvent != null){
                        LocalDate eventDate = LocalDate.parse(selectedEvent.getDate());
                        LocalTime eventTime = LocalTime.parse(selectedEvent.getTime());
                        LocalDate today = LocalDate.now();
                        LocalTime time = LocalTime.now();
                        Log.e("Bug","Selected event date"+selectedEvent.getName());
                        if((eventDate.isAfter(today)) || (eventDate.isEqual(today) && (eventTime.isAfter(time)))){
                            Utilities.replaceFragmentOnContainer((AppCompatActivity) activity,new ModifyEventFragment(),ModifyEventFragment.class.getSimpleName(), R.id.fragment_container_view);
                        } else {
                            enablerDialog.showInfoCantModifyEvent();
                        }
                    }
                }
            }
        });
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    LocalDate eventDate = LocalDate.parse(eventListViewModel.getSelectedEventItem().getValue().getDate());
                    LocalTime eventTime = LocalTime.parse(eventListViewModel.getSelectedEventItem().getValue().getTime());
                    LocalDate today = LocalDate.now();
                    LocalTime time = LocalTime.now();
                    if((eventDate.isBefore(today) || (eventDate.isEqual(today)) && (eventTime.isBefore(time)))){
                        QrCodeScannerFragment qrCodeScannerFragment = new QrCodeScannerFragment();
                        qrCodeScannerFragment.setScanTicketMode(true);
                        Utilities.replaceFragmentOnContainer((AppCompatActivity) activity, qrCodeScannerFragment, QrCodeScannerFragment.class.getSimpleName(), R.id.fragment_container_view);
                    } else {
                        enablerDialog.showInfoCantScanTicket();
                    }
                }
            }
        });

    }



}
