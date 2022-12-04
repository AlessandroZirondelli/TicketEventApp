package com.example.ticketeventapp.ui.mngevents.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.model.utils.AppInfo;
import com.example.ticketeventapp.ui.mngevents.fragment.ActionSelectFragment;
import com.example.ticketeventapp.ui.mngevents.fragment.AddEventFragment;
import com.example.ticketeventapp.ui.mngevents.fragment.InfoEventFragment;
import com.example.ticketeventapp.ui.utilities.Utilities;
import com.example.ticketeventapp.viewmodel.mng_events.AddEventViewModel;

public class MngEventsActivity extends AppCompatActivity {

    private AddEventViewModel addEventViewModel;
    private AppInfo appInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mng_events);

        if(savedInstanceState == null){
            Utilities.insertFragment(this, new InfoEventFragment(), InfoEventFragment.class.getSimpleName());
        }
        appInfo = AppInfo.getInstance();
        addEventViewModel = new ViewModelProvider(this).get(AddEventViewModel.class);
        Log.e("MngEventsActivity","Logged User:"+appInfo.getLoggedUser().getUsername());

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int CHOOSE_PICTURE = 200;

        if(requestCode == CHOOSE_PICTURE && resultCode==RESULT_OK){
            Uri imageUri = data.getData();
            addEventViewModel.setImageURI(imageUri);
        }
    }
}