package com.example.ticketeventapp.ui.main.fragment.mngtickets;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.model.mng_tickets.Ticket;
import com.example.ticketeventapp.model.mng_tickets.TicketsManager;
import com.example.ticketeventapp.ui.main.fragment.mngevents.components.EnablerDialog;
import com.example.ticketeventapp.viewmodel.mng_tickets.InfoTicketViewModel;
import com.google.zxing.WriterException;

public class TicketResultFragment extends Fragment {

    private ImageView qrcode;
    private ImageView save;
    private Bitmap generatedQrCode;
    private EnablerDialog enablerDialog;
    private InfoTicketViewModel infoTicketViewModel;

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
        infoTicketViewModel = new ViewModelProvider(getActivity()).get(InfoTicketViewModel.class);
        qrcode = view.findViewById(R.id.qrcode_image_view);
        save = view.findViewById(R.id.save_to_gallery_image_view);
        enablerDialog = new EnablerDialog(getActivity());

        Ticket selectedTicket = infoTicketViewModel.getSelectedTicket().getValue();
        try {
            if(selectedTicket != null){
                generatedQrCode = TicketsManager.generateBitmapQrCode(selectedTicket.getCode());
                qrcode.setImageBitmap(generatedQrCode);
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
