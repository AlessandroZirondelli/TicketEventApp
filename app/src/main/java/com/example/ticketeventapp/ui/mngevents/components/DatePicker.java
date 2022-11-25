package com.example.ticketeventapp.ui.mngevents.components;

import androidx.fragment.app.FragmentManager;

import com.google.android.material.datepicker.MaterialDatePicker;

public class DatePicker {

    MaterialDatePicker materialDatePicker;
    MaterialDatePicker.Builder  materialDateBuilder;
    FragmentManager fragmentManager;

    public DatePicker(FragmentManager fragmentManager){
        materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("SELECT A DATE");
        materialDatePicker = materialDateBuilder.build();
        this.fragmentManager = fragmentManager;
    }


    public void show(){
        materialDatePicker.show(fragmentManager, "MATERIAL_DATE_PICKER");
    }


}
