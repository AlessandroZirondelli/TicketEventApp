package com.example.ticketeventapp.ui.home.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.model.home.recyclerview.EventItemAdapter;
import com.example.ticketeventapp.model.home.recyclerview.EventsRecyclerView;
import com.example.ticketeventapp.model.home.recyclerview.onitemlistener.OnEventListener;
import com.example.ticketeventapp.model.mng_events.Event;
import com.example.ticketeventapp.viewmodel.mng_events.EventListViewModel;

import java.util.List;


public class RecyclerViewEventsFragment extends Fragment {

    private EventsRecyclerView eventsRecyclerView;
    private EventListViewModel eventListViewModel;
    private Activity activity;
    private EventItemAdapter eventItemAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recycler_view_events_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = getActivity();
        if(activity==null){
            Log.e("HomeFragment","activity null in recyclervieeweventsfragemnt");
        }
        eventsRecyclerView = new EventsRecyclerView(getActivity());
        eventsRecyclerView.setRecyclerView(new OnEventListener());

        eventListViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(EventListViewModel.class);

        eventItemAdapter = eventsRecyclerView.getEventItemAdapter();

        eventListViewModel.getEventsLiveData().observe((LifecycleOwner) activity, new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> eventList) {
                eventItemAdapter.setData(eventList);
            }
        });

    }


}
