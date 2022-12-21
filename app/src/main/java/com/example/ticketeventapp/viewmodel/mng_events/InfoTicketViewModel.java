package com.example.ticketeventapp.viewmodel.mng_events;


import android.app.Application;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.ticketeventapp.database.TicketEventAppRepository;
import com.example.ticketeventapp.model.mng_tickets.Ticket;

public class InfoTicketViewModel extends AndroidViewModel {

    private MutableLiveData<Bitmap> qrcode;
    private TicketEventAppRepository repository;
    private MutableLiveData<Ticket> selectedTicket;

    public InfoTicketViewModel(@NonNull Application application) {
        super(application);
        repository = new TicketEventAppRepository(application);
        qrcode = new MutableLiveData<>();
        selectedTicket = new MutableLiveData<>();
    }


    public void addTicket(Ticket ticket){
        repository.addTicket(ticket);
    }

    public void setSelectedTicket(Ticket selectedTicket) {
        this.selectedTicket.setValue(selectedTicket);
    }

    public MutableLiveData<Ticket> getSelectedTicket(){
        return this.selectedTicket;
    }
}
