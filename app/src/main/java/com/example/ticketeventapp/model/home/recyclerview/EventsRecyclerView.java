package com.example.ticketeventapp.model.home.recyclerview;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.model.home.recyclerview.onitemlistener.OnItemListener;
import com.example.ticketeventapp.model.mng_events.Event;

import java.util.ArrayList;
import java.util.List;

public class EventsRecyclerView {
    private Activity activity;
    private RecyclerView eventsRecyclerView;

    public EventsRecyclerView(Activity activity){
        this.activity = activity;
    }


    public void  setRecyclerView(OnItemListener onItemListener){
        eventsRecyclerView = activity.findViewById(R.id.recycler_view_events);
        eventsRecyclerView.setHasFixedSize(true);
        // Here I should set Adapter to pass data to display
        List<Event> eventList = new ArrayList<>();
        Event event1 = new Event("Event1","","2020-03-04","15:45","","","add_photo_alternate","","");
        Event event2 = new Event("Event2","","2022-07-10","20:00","","","add_photo_alternate","","");
        Event event3 = new Event("Event3","","2022-07-10","20:00","","","add_photo_alternate","","");
        Event event4 = new Event("Event4","","2022-07-10","20:00","","","add_photo_alternate","","");
        Event event5 = new Event("Event5","","2022-07-10","20:00","","","add_photo_alternate","","");
        Event event6 = new Event("Event6","","2022-07-10","20:00","","","add_photo_alternate","","");

        eventList.add(event1);
        eventList.add(event2);
        eventList.add(event3);
        eventList.add(event4);
        eventList.add(event5);
        eventList.add(event6);

        EventItemAdapter eventItemAdapter = new EventItemAdapter(activity,eventList,onItemListener);
        eventsRecyclerView.setAdapter(eventItemAdapter);
    }
}
