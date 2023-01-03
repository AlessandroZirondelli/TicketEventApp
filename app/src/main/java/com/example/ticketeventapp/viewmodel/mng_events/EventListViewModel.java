package com.example.ticketeventapp.viewmodel.mng_events;

import android.app.Application;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ticketeventapp.database.TicketEventAppRepository;
import com.example.ticketeventapp.model.mng_events.Event;

import java.util.List;

public class EventListViewModel extends AndroidViewModel {

    private LiveData<List<Event>> eventItems;
    private MutableLiveData<Location> location;
    private MutableLiveData<Event> selectedEventItem;

    private TicketEventAppRepository repository;

    public EventListViewModel(@NonNull Application application) {
        super(application);
        repository = new TicketEventAppRepository(application);
        eventItems = repository.getEventsLiveData();
        location = new MutableLiveData<>();
        selectedEventItem = new MutableLiveData<>();
    }

    public LiveData<List<Event>> getEventsLiveData(){
        return eventItems;
    }

    public void setLocation(Location location){
        this.location.setValue(location);
    }

    public LiveData<Location> getLocationLiveData(){
        return this.location;
    }

    public void setSelectedEventItem(Event selectedEventItem) {
        this.selectedEventItem.setValue(selectedEventItem);
    }

    public LiveData<Event> getSelectedEventItem(){
        return selectedEventItem;
    }

    public void clearSelectedItem() {
        this.selectedEventItem.setValue(null);
    }

    public void deleteEvent(){
        this.repository.deleteEvent(this.selectedEventItem.getValue());
    }

    public void deleteAllTicketOfEvent(){
        this.repository.deleteAllTicketOfEvent(this.selectedEventItem.getValue().getId());
    }
}
