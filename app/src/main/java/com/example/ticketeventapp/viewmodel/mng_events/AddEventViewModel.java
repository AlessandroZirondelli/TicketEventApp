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
    private final MutableLiveData<Boolean> isPermissionGPSAllowed;
    private final MutableLiveData<Boolean> isTurnedOnGPS;
    private final MutableLiveData<Location> position;
    private final MutableLiveData<Boolean> isConnectedToInternet;
    private final MutableLiveData<String> position_display_name;



    public AddEventViewModel(@NonNull Application application) {
        super(application);
        imageURI = new MutableLiveData<>();
        selectedDate = new MutableLiveData<>();
        selectedTime = new MutableLiveData<>();
        isPermissionGPSAllowed = new MutableLiveData<>();
        isTurnedOnGPS = new MutableLiveData<>();
        position = new MutableLiveData<>();
        isConnectedToInternet = new MutableLiveData<>();
        position_display_name = new MutableLiveData<>();
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

    public void setIsPermissionGPSAllowed(Boolean isPermissionGPSAllowed){this.isPermissionGPSAllowed.setValue(isPermissionGPSAllowed);}

    public void setIsTurnedOnGPS(Boolean isTurnedOnGPS){
        this.isTurnedOnGPS.setValue(isTurnedOnGPS);
    }

    public  void setPosition(Location position){
        this.position.setValue(position);
    }

    public void setIsConnectedToInternet(Boolean isConnectedToInternet){this.isConnectedToInternet.postValue(isConnectedToInternet);}

    public void setPosition_display_name(String display_name){ this.position_display_name.setValue(display_name);}

    public MutableLiveData<String> getPosition_display_name() { return position_display_name; }

    public MutableLiveData<Boolean> getIsConnectedToInternet(){return this.isConnectedToInternet;}

    public MutableLiveData<Boolean> getIsTurnedOnGPS(){
        return this.isTurnedOnGPS;
    }

    public MutableLiveData<LocalTime> getSelectedTime(){return this.selectedTime;}

    public MutableLiveData<Uri> getImageURI(){
        return this.imageURI;
    }

    public MutableLiveData<String> getSelectedDate(){
        return this.selectedDate;
    }

    public MutableLiveData<Boolean> getIsPermissionGPSAllowed() { return isPermissionGPSAllowed;}

    public MutableLiveData<Location> getPosition(){
        return this.position;
    }



}
