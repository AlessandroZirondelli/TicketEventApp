package com.example.ticketeventapp.ui.mngevents.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.ui.mngevents.fragment.ActionSelectFragment;
import com.example.ticketeventapp.ui.mngevents.fragment.AddEventFragment;
import com.example.ticketeventapp.ui.utilities.Utilities;
import com.example.ticketeventapp.viewmodel.mng_events.AddEventViewModel;

public class MngEventsActivity extends AppCompatActivity {

    private AddEventViewModel addEventViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_log);

        if(savedInstanceState == null){
            Utilities.insertFragment(this, new AddEventFragment(), ActionSelectFragment.class.getSimpleName());
        }

        addEventViewModel = new ViewModelProvider(this).get(AddEventViewModel.class);


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