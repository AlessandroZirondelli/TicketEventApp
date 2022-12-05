package com.example.ticketeventapp.ui.home.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.model.home.recyclerview.EventsRecyclerView;
import com.example.ticketeventapp.model.home.recyclerview.onitemlistener.OnEventListener;


public class RecyclerViewEventsFragment extends Fragment {

    private EventsRecyclerView eventsRecyclerView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recycler_view_events_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        eventsRecyclerView = new EventsRecyclerView(getActivity());
        eventsRecyclerView.setRecyclerView(new OnEventListener());
        super.onViewCreated(view, savedInstanceState);
    }


}
