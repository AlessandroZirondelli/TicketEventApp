package com.example.ticketeventapp.ui.main.home.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.ui.main.mngevents.fragment.AddEventFragment;
import com.example.ticketeventapp.ui.utilities.Utilities;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BottomBarEventsAdminFragment extends Fragment {

    private FloatingActionButton floatingActionButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.navigation_bar_admin,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        floatingActionButton = view.findViewById(R.id.fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.insertFragment((AppCompatActivity) getActivity(), new AddEventFragment(), AddEventFragment.class.getSimpleName());
            }
        });

    }
}
