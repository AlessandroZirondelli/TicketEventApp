package com.example.ticketeventapp.ui.home.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.ui.utilities.Utilities;

public class HomeFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);
        Utilities.insertHomeFragment((AppCompatActivity) getActivity(), new BottomBarEventsFragment(),BottomBarEventsFragment.class.getSimpleName(),R.id.fragment_container_view_bottom_bar);
        Utilities.insertHomeFragment((AppCompatActivity) getActivity(),new RecyclerViewEventsFragment(),RecyclerViewEventsFragment.class.getSimpleName(),R.id.fragment_container_recycler_view);
        return view;
    }


}
