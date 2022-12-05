package com.example.ticketeventapp.ui.home.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.model.mng_events.Event;
import com.example.ticketeventapp.ui.utilities.Utilities;
import com.example.ticketeventapp.viewmodel.mng_events.EventListViewModel;

import java.util.List;

public class HomeFragment extends Fragment {

    private Activity activity;
    //private EventListViewModel eventListViewModel;

    private BottomBarEventsFragment bottomBarEventsFragment;
    private RecyclerViewEventsFragment recyclerViewEventsFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);
        bottomBarEventsFragment = new BottomBarEventsFragment();
        recyclerViewEventsFragment = new RecyclerViewEventsFragment();
        Utilities.insertHomeFragment((AppCompatActivity) getActivity(), bottomBarEventsFragment,BottomBarEventsFragment.class.getSimpleName(),R.id.fragment_container_view_bottom_bar,this);
        Utilities.insertHomeFragment((AppCompatActivity) getActivity(), recyclerViewEventsFragment,RecyclerViewEventsFragment.class.getSimpleName(),R.id.fragment_container_recycler_view,this);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Activity activity = getActivity();
        //eventListViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(EventListViewModel.class);

    }


}
