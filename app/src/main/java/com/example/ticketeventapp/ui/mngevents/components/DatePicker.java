package com.example.ticketeventapp.ui.mngevents.components;

import android.content.DialogInterface;
import android.os.Build;
import android.util.Log;

import androidx.fragment.app.FragmentManager;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.viewmodel.mng_events.AddEventViewModel;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.TimeZone;

public class DatePicker {

    private MaterialDatePicker<Long> materialDatePicker;
    private MaterialDatePicker.Builder  materialDateBuilder;
    private FragmentManager fragmentManager;
    private String selectedDate;
    private AddEventViewModel addEventViewModel;
    private boolean isOpen;

    public DatePicker(FragmentManager fragmentManager, AddEventViewModel addEventViewModel){
        materialDateBuilder = MaterialDatePicker.Builder.datePicker().setTitleText(R.string.select_event_date);
        disablePastDatesSelection();
        materialDatePicker = materialDateBuilder.build();
        this.fragmentManager = fragmentManager;
        setClickListeners();
        this.addEventViewModel = addEventViewModel;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            selectedDate = LocalDate.now().toString();
        }else{
            //TODO
        }
        isOpen = false;
    }


    public void show(){
        if(!isOpen){
            isOpen = true;
            materialDatePicker.show(fragmentManager, "MATERIAL_DATE_PICKER");
        }
    }

    private void setClickListeners(){
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                calendar.setTimeInMillis(selection);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate  = format.format(calendar.getTime());
                selectedDate = formattedDate;
                setDateOnViewModel();
            }
        });

        materialDatePicker.addOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                isOpen = false;
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

    private void setDateOnViewModel(){
        this.addEventViewModel.setSelectedDate(this.getSelectedDate());
    }

    public String getSelectedDate(){
        return selectedDate;
    }






}
