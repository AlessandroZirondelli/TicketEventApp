package com.example.ticketeventapp.ui.mngevents.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.ui.mngevents.fragment.ActionSelectFragment;
import com.example.ticketeventapp.ui.mngevents.fragment.AddEventFragment;
import com.example.ticketeventapp.ui.utilities.Utilities;

public class MngEventsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_log);

        if(savedInstanceState == null){
            Utilities.insertFragment(this, new AddEventFragment(), ActionSelectFragment.class.getSimpleName());
        }


    }
}