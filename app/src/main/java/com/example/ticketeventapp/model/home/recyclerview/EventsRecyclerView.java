package com.example.ticketeventapp.model.home.recyclerview;

import android.app.Activity;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.model.home.recyclerview.onitemlistener.OnItemListener;
import com.example.ticketeventapp.model.mng_events.Event;

import java.util.ArrayList;
import java.util.List;

public class EventsRecyclerView {
    private Activity activity;
    private RecyclerView eventsRecyclerView;
    private EventItemAdapter eventItemAdapter;

    public EventsRecyclerView(Activity activity){
        this.activity = activity;
    }


    public void  setRecyclerView(OnItemListener onItemListener){
        if(activity == null){
            Log.e("Bug","Activity null");
        }
        eventsRecyclerView = activity.findViewById(R.id.recycler_view_events);
        eventsRecyclerView.setHasFixedSize(true);
        // Here I should set Adapter to pass data to display
        List<Event> eventList = new ArrayList<>();
        this.eventItemAdapter = new EventItemAdapter(activity,eventList,onItemListener);
        eventsRecyclerView.setAdapter(eventItemAdapter);

    }

    public EventItemAdapter getEventItemAdapter(){
        return this.eventItemAdapter;
    }


}
