package com.example.ticketeventapp.ui.main.fragment.mngevents.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.ui.utilities.Utilities;

public class ActionSelectFragment extends Fragment {

    private Activity activity;
    private ImageView scan;
    private ImageView joiners;
    private ImageView modify;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_action_select, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setBackgroundColor(Color.WHITE); // Add background color because it's transparent
        activity = getActivity();

        scan = view.findViewById(R.id.scan_qrcode_image_view);
        modify = view.findViewById(R.id.edit_event_image_view);
        joiners = view.findViewById(R.id.view_joiners_image_view);

        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.addFragmentOn((AppCompatActivity) activity,new ModifyEventFragment(),ModifyEventFragment.class.getSimpleName());
            }
        });

    }



}
