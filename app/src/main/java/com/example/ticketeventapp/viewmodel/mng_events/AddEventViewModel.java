package com.example.ticketeventapp.viewmodel.mng_events;

import android.app.Application;
import android.location.Location;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.net.URI;
import java.time.LocalTime;

public class AddEventViewModel extends AndroidViewModel {

    private final MutableLiveData<Uri> imageURI;
    private final MutableLiveData<String> selectedDate;
    private final MutableLiveData<LocalTime> selectedTime;
    private final MutableLiveData<Location> location;
    private final MutableLiveData<String> locationName;
    private final MutableLiveData<String> locationDisplayName;

    public AddEventViewModel(@NonNull Application application) {
        super(application);
        imageURI = new MutableLiveData<>();
        selectedDate = new MutableLiveData<>();
        selectedTime = new MutableLiveData<>();
        location = new MutableLiveData<>();
        locationName = new MutableLiveData<>();
        locationDisplayName = new MutableLiveData<>();
    }

    public void setImageURI(Uri uri){
        imageURI.setValue(uri);
    }

    public void setSelectedDate(String selectedDate){
        this.selectedDate.setValue(selectedDate);
    }

    public void setSelectedTime(LocalTime time){
        this.selectedTime.setValue(time);
    }

    public void setLocation(Location location){ this.location.setValue(location);}

    public void setLocationName(String name){
        this.locationName.setValue(name);
    }

    public void setLocationDisplayName(String displayName){
        this.locationDisplayName.setValue(displayName);
    }

    public MutableLiveData<String> getLocationName(){
        return this.locationName;
    }

    public MutableLiveData<String> getLocationDisplayName(){
        return this.locationDisplayName;
    }

    public MutableLiveData<Location> getLocation(){return this.location;}

    public MutableLiveData<LocalTime> getSelectedTime(){return this.selectedTime;}

    public MutableLiveData<Uri> getImageURI(){
        return this.imageURI;
    }

    public MutableLiveData<String> getSelectedDate(){
        return this.selectedDate;
    }



}
