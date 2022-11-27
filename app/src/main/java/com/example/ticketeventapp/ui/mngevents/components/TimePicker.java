package com.example.ticketeventapp.ui.mngevents.components;

import android.util.Log;
import android.view.View;

import androidx.fragment.app.FragmentManager;

import com.example.ticketeventapp.viewmodel.mng_events.AddEventViewModel;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
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
                setTimeOnViewModel(hour,minutes);
            }
        });

    }


    private void setTimeOnViewModel(int hour, int minutes){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalTime time = LocalTime.of(hour,minutes);
            addEventViewModel.setSelectedTime(time);
        } else{
            //TODO
        }
    }


    public void show(){
        materialTimePicker.show(fragmentManager,"MATERIAL_TIME_PICKER");
    }

}
