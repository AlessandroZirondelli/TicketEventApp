package com.example.ticketeventapp.viewmodel.mng_tickets;


import android.app.Application;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ticketeventapp.database.TicketEventAppRepository;
import com.example.ticketeventapp.model.mng_tickets.Ticket;

import java.util.List;

public class InfoTicketViewModel extends AndroidViewModel {

    private MutableLiveData<Bitmap> qrcode;
    private TicketEventAppRepository repository;
    private MutableLiveData<Ticket> selectedTicket;
    private LiveData<List<Ticket>> ticketsLiveData;

    public InfoTicketViewModel(@NonNull Application application) {
        super(application);
        repository = new TicketEventAppRepository(application);
        qrcode = new MutableLiveData<>();
        selectedTicket = new MutableLiveData<>();
        ticketsLiveData = repository.getTicketsLiveData();
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

    public LiveData<List<Ticket>> getTicketsLiveData(){
        return this.ticketsLiveData;
    }
}
