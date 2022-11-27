package com.example.ticketeventapp.viewmodel.mng_events;

import android.app.Application;
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


    public AddEventViewModel(@NonNull Application application) {
        super(application);
        imageURI = new MutableLiveData<>();
        selectedDate = new MutableLiveData<>();
        selectedTime = new MutableLiveData<>();
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

    public MutableLiveData<LocalTime> getSelectedTime(){return this.selectedTime;}

    public MutableLiveData<Uri> getImageURI(){
        return this.imageURI;
    }

    public MutableLiveData<String> getSelectedDate(){
        return this.selectedDate;
    }



}
