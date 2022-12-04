package com.example.ticketeventapp.model.home.recyclerview;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketeventapp.R;

public class EventsRecyclerView {
    private Activity activity;
    private RecyclerView eventsRecyclerView;

    public EventsRecyclerView(Activity activity){
        this.activity = activity;
    }


    private void  setRecyclerView(){
        eventsRecyclerView = activity.findViewById(R.id.recycler_view_events);
        eventsRecyclerView.setHasFixedSize(true);
        // Here I should set Adapter to pass data to display
        //EventItemAdapter eventItemAdapter = new EventItemAdapter();
        //eventsRecyclerView.setAdapter(eventItemAdapter);
    }
}
