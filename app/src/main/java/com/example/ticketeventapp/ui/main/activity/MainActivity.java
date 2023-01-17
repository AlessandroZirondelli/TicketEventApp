package com.example.ticketeventapp.ui.main.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.model.mng_users.User;
import com.example.ticketeventapp.model.utils.AppInfo;
import com.example.ticketeventapp.ui.main.fragment.home.fragment.HomeFragment;
import com.example.ticketeventapp.ui.utilities.Utilities;
import com.example.ticketeventapp.viewmodel.mng_events.AddEventViewModel;
import com.example.ticketeventapp.viewmodel.mng_users.UsersViewModelRegLog;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AddEventViewModel addEventViewModel;
    private UsersViewModelRegLog usersViewModelRegLog;
    private AppInfo appInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);

        if(savedInstanceState == null){
            Log.e("BackStack","SavedInstanceState in Main Activity is null, so Insert Home");
            Utilities.replaceFragmentOnContainer(this, new HomeFragment(), HomeFragment.class.getSimpleName(), R.id.fragment_container_view);
        } else {
            Log.e("BackStack","SavedInstanceState in Main Activity NOT NULL");
        }
        appInfo = AppInfo.getInstance();
        addEventViewModel = new ViewModelProvider(this).get(AddEventViewModel.class);
        usersViewModelRegLog = new ViewModelProvider(this).get(UsersViewModelRegLog.class);

        usersViewModelRegLog.getUsersLiveData().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int CHOOSE_PICTURE = 200;

        if(requestCode == CHOOSE_PICTURE && resultCode==RESULT_OK){
            Uri imageUri = data.getData();
            addEventViewModel.setImageURI(imageUri.toString());
        }
    }
}