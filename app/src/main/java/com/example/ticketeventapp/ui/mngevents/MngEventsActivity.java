package com.example.ticketeventapp.ui.mngevents;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.ui.utilities.Utilities;

public class MngEventsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_log);

        if(savedInstanceState == null){
            Utilities.insertFragment(this, new ActionSelectFragment(), ActionSelectFragment.class.getSimpleName());
        }


    }
}