package com.example.ticketeventapp.ui.main.fragment.mngtickets;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.model.mng_events.AddEventManager;
import com.example.ticketeventapp.model.mng_events.Event;
import com.example.ticketeventapp.model.mng_tickets.Ticket;
import com.example.ticketeventapp.model.mng_tickets.TicketsManager;
import com.example.ticketeventapp.ui.main.fragment.mngevents.components.EnablerDialog;
import com.example.ticketeventapp.viewmodel.mng_events.EventListViewModel;
import com.example.ticketeventapp.viewmodel.mng_tickets.TicketListViewModel;
import com.google.zxing.WriterException;

public class EventResultFragment extends Fragment {

    private ImageView qrcode;
    private ImageView save;
    private Bitmap generatedQrCode;
    private TextView title;
    private EnablerDialog enablerDialog;
    private EventListViewModel eventListViewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_qrcode, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        eventListViewModel = new ViewModelProvider(getActivity()).get(EventListViewModel.class);
        qrcode = view.findViewById(R.id.qrcode_image_view);
        save = view.findViewById(R.id.save_to_gallery_image_view);
        title = view.findViewById(R.id.info_event_ticket);
        enablerDialog = new EnablerDialog(getActivity());

        title.setText(R.string.qr_code_event_title);


        Event selectedEvent = eventListViewModel.getSelectedEventItem().getValue();

        eventListViewModel.getSelectedEventItem().observe(getActivity(), new Observer<Event>() {
            @Override
            public void onChanged(Event event) {

                Log.e("Bug","Selected event changed");
                if(event!=null){
                    Log.e("Bug","Selected event changed not null");
                }
            }
        });
        try {
            if(selectedEvent != null){
                generatedQrCode = AddEventManager.generateBitmapQrCode(selectedEvent.getCode());
                qrcode.setImageBitmap(generatedQrCode);
            } else {
                Log.e("Bug","selectedEvent is null");
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TicketsManager.saveImage(generatedQrCode,getActivity());
                enablerDialog.showInfoSavingQrCodeIntoGallery();
            }
        });

    }
}
