package com.example.ticketeventapp.reglog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.mng_users.UsersViewModel;
import com.example.ticketeventapp.utilities.Utilities;

public class RegLogActivity extends AppCompatActivity {

    private UsersViewModel usersViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_log);

        if(savedInstanceState == null){
            Utilities.insertFragment(this,new LoginFragment(), LoginFragment.class.getSimpleName());
        }

        usersViewModel = new ViewModelProvider(this).get(UsersViewModel.class);

    }
}