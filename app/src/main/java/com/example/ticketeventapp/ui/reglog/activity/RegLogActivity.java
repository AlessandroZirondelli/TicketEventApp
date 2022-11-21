package com.example.ticketeventapp.ui.reglog.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.model.mng_users.User;
import com.example.ticketeventapp.viewmodel.mng_users.UsersViewModelRegLog;
import com.example.ticketeventapp.ui.reglog.fragment.LoginFragment;
import com.example.ticketeventapp.ui.utilities.Utilities;

import java.util.List;

public class RegLogActivity extends AppCompatActivity {

    private UsersViewModelRegLog usersViewModelRegLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_log);

        if(savedInstanceState == null){
            Utilities.insertFragment(this,new LoginFragment(), LoginFragment.class.getSimpleName());
        }

        usersViewModelRegLog = new ViewModelProvider(this).get(UsersViewModelRegLog.class);




    }
}