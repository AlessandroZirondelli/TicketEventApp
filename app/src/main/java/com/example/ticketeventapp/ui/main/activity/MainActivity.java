package com.example.ticketeventapp.ui.main.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.model.utils.AppInfo;
import com.example.ticketeventapp.ui.main.fragment.home.fragment.HomeFragment;
import com.example.ticketeventapp.ui.utilities.Utilities;
import com.example.ticketeventapp.viewmodel.mng_events.AddEventViewModel;

public class MainActivity extends AppCompatActivity {

    private AddEventViewModel addEventViewModel;
    private AppInfo appInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);

        if(savedInstanceState == null){
            Log.e("BackStack","Inserisco home");
            Utilities.replaceFragmentOnContainer(this, new HomeFragment(), HomeFragment.class.getSimpleName(), R.id.fragment_container_view);
        }
        appInfo = AppInfo.getInstance();
        addEventViewModel = new ViewModelProvider(this).get(AddEventViewModel.class);
        //Log.e("MngEventsActivity","Logged User:"+appInfo.getLoggedUser().getUsername());

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int CHOOSE_PICTURE = 200;

        if(requestCode == CHOOSE_PICTURE && resultCode==RESULT_OK){
            Uri imageUri = data.getData();
            Log.e("AddEventFragment",imageUri.toString());
            addEventViewModel.setImageURI(imageUri.toString());
        }
    }
}