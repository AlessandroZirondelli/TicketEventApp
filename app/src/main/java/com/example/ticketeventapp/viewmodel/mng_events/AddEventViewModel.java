package com.example.ticketeventapp.viewmodel.mng_events;

import android.app.Application;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class AddEventViewModel extends AndroidViewModel {

    private final MutableLiveData<Bitmap> imageBitmap;
    private final MutableLiveData<String> selectedDate;
    private final MutableLiveData<String> selectedTime;

    public AddEventViewModel(@NonNull Application application) {
        super(application);
        imageBitmap = new MutableLiveData<>();
        selectedDate = new MutableLiveData<>();
        selectedTime = new MutableLiveData<>();
    }

    public void setImageBitmap(Bitmap bitmap){
        imageBitmap.setValue(bitmap);
    }

    public void setSelectedDate(String selectedDate){
        this.selectedDate.setValue(selectedDate);
    }

    public void setSelectedTime(String selectedTime){
        this.selectedTime.setValue(selectedTime);
    }

    public MutableLiveData<Bitmap> getImageBitmap(){
        return this.imageBitmap;
    }

    public MutableLiveData<String> getSelectedDate(){
        return this.selectedDate;
    }

    public MutableLiveData<String> getSelectedTime(){
        return this.selectedTime;
    }

}
