package com.example.ticketeventapp.viewmodel.mng_events;

import android.app.Application;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class AddEventViewModel extends AndroidViewModel {

    private final MutableLiveData<Bitmap> imageBitmap;
    private final MutableLiveData<String> selectedDate;

    private final MutableLiveData<Integer> selectedHour;
    private final MutableLiveData<Integer> selectedMinutes;

    public AddEventViewModel(@NonNull Application application) {
        super(application);
        imageBitmap = new MutableLiveData<>();
        selectedDate = new MutableLiveData<>();
        selectedHour = new MutableLiveData<>();
        selectedMinutes = new MutableLiveData<>();


    }

    public void setImageBitmap(Bitmap bitmap){
        imageBitmap.setValue(bitmap);
    }

    public void setSelectedDate(String selectedDate){
        this.selectedDate.setValue(selectedDate);
    }

    public void setSelectedHour(Integer selectedHour){
        this.selectedHour.setValue(selectedHour);
    }

    public void setSelectedMinutes(Integer selectedMinutes) {this.selectedMinutes.setValue(selectedMinutes);}

    public MutableLiveData<Integer> getSelectedMinutes(){return this.selectedMinutes;}

    public MutableLiveData<Integer> getSelectedHour(){return this.selectedHour;}

    public MutableLiveData<Bitmap> getImageBitmap(){
        return this.imageBitmap;
    }

    public MutableLiveData<String> getSelectedDate(){
        return this.selectedDate;
    }



}
