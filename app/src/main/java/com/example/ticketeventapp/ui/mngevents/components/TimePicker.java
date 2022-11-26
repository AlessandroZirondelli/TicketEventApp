package com.example.ticketeventapp.ui.mngevents.components;

import android.util.Log;
import android.view.View;

import androidx.fragment.app.FragmentManager;

import com.example.ticketeventapp.viewmodel.mng_events.AddEventViewModel;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class TimePicker {
    private final int DEFAULT_HOUR = 12;
    private final int DEFAULT_MINUTES = 10;

    private MaterialTimePicker materialTimePicker;
    private MaterialTimePicker.Builder materialTimeBuilder;
    private AddEventViewModel addEventViewModel;

    private FragmentManager fragmentManager;

    private Integer hour;
    private Integer minutes;

    public TimePicker(FragmentManager fragmentManager, AddEventViewModel addEventViewModel){
        this.addEventViewModel = addEventViewModel;
        this.fragmentManager = fragmentManager;

        hour = DEFAULT_HOUR;
        minutes = DEFAULT_MINUTES;
        materialTimeBuilder = new MaterialTimePicker.Builder();
        materialTimePicker = materialTimeBuilder.setTitleText("Select event time")
                            .setTimeFormat(TimeFormat.CLOCK_24H)
                            .setHour(DEFAULT_HOUR)
                            .setMinute(DEFAULT_MINUTES)
                            .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
                            .build();

        setPositiveClickListener();

    }

    private void setPositiveClickListener(){
        materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hour = materialTimePicker.getHour();
                minutes = materialTimePicker.getMinute();
                setHourOnViewModel(hour);
                setMinutesOnViewModel(minutes);
            }
        });

    }


    private int getMinutes(){
        return this.minutes;
    }

    private int getHour(){
        return this.hour;
    }

    private void setHourOnViewModel(Integer hour){
        this.addEventViewModel.setSelectedHour(hour);
    }

    private void setMinutesOnViewModel(Integer minutes){
        this.addEventViewModel.setSelectedMinutes(minutes);
    }

    public void show(){
        materialTimePicker.show(fragmentManager,"MATERIAL_TIME_PICKER");
    }

}
