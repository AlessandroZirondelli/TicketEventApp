package com.example.ticketeventapp.ui.home.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.model.home.recyclerview.EventsRecyclerView;
import com.example.ticketeventapp.model.home.recyclerview.OnItemListener;


public class RecyclerViewEventsFragment extends Fragment implements OnItemListener {

    private EventsRecyclerView eventsRecyclerView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recycler_view_events_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        eventsRecyclerView = new EventsRecyclerView(getActivity());
        eventsRecyclerView.setRecyclerView(this);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onItemClick(int position) {
        Activity activity = getActivity();
        if (activity != null){
            /*Utilities.insertFragment((AppCompatActivity) activity, new DetailsFragment(),
                    DetailsFragment.class.getSimpleName());*/

            //listViewModel.setItemSelected(adapter.getItemSelected(position));
            Log.e("HomeFragment","Clickato");
        }
    }
}
