package com.example.ticketeventapp.reglog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.utilities.Utilities;

public class RegLogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_log);

        if(savedInstanceState == null){
            Utilities.insertFragment(this,new RegistrationFragment(), RegistrationFragment.class.getSimpleName());
        }


    }
}