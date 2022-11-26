package com.example.ticketeventapp.ui.mngevents.components;

import android.os.Build;
import android.util.Log;

import androidx.fragment.app.FragmentManager;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.TimeZone;

public class DatePicker {

    MaterialDatePicker<Long> materialDatePicker;
    MaterialDatePicker.Builder  materialDateBuilder;
    FragmentManager fragmentManager;
    String selectedDate;

    public DatePicker(FragmentManager fragmentManager){
        materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("SELECT A DATE");

        disablePastDatesSelection();
        materialDatePicker = materialDateBuilder.build();
        this.fragmentManager = fragmentManager;
        setPositiveClickListener();

    }


    public void show(){
        materialDatePicker.show(fragmentManager, "MATERIAL_DATE_PICKER");
    }

    private void setPositiveClickListener(){
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                calendar.setTimeInMillis(selection);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate  = format.format(calendar.getTime());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    selectedDate = formattedDate;
                }else{
                    //TODO
                }
                Log.e("AddFragment",formattedDate);
            }
        });
    }

    private void disablePastDatesSelection(){
        CalendarConstraints.Builder calendarConstraintsBuilder = new CalendarConstraints.Builder();
        CalendarConstraints.DateValidator dateValidator = DateValidatorPointForward.now();
        calendarConstraintsBuilder.setValidator(dateValidator);
        CalendarConstraints calendarConstraints = calendarConstraintsBuilder.build();
        materialDateBuilder.setCalendarConstraints(calendarConstraints);

    }

    public String getSelectedDate(){
        return selectedDate;
    }


}
