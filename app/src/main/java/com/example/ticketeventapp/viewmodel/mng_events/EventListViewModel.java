package com.example.ticketeventapp.viewmodel.mng_events;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.ticketeventapp.database.TicketEventAppRepository;
import com.example.ticketeventapp.model.mng_events.Event;

import java.util.List;

public class EventListViewModel extends AndroidViewModel {

    private LiveData<List<Event>> eventItems;

    private TicketEventAppRepository repository;

    public EventListViewModel(@NonNull Application application) {
        super(application);
        repository = new TicketEventAppRepository(application);
        eventItems = repository.getEventsLiveData();
    }

    public LiveData<List<Event>> getEventsLiveData(){
        return eventItems;
    }
}
