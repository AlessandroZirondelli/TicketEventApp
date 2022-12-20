package com.example.ticketeventapp.ui.main.fragment.home.fragment;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.model.utils.AppInfo;
import com.example.ticketeventapp.ui.utilities.Utilities;
import com.example.ticketeventapp.viewmodel.mng_events.EventListViewModel;

public class HomeFragment extends Fragment {

    private Activity activity;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);

        if(savedInstanceState == null){
            Log.e("Bug"," savedInstanceState null in HomeFragment");
            Utilities.replaceFragmentOnContainer((AppCompatActivity) getActivity(), new RecyclerViewEventsFragment(), RecyclerViewEventsFragment.class.getSimpleName(),R.id.fragment_container_recycler_view);
            if(AppInfo.getInstance().getLoggedUser().isUser()){
                Utilities.replaceFragmentOnContainer((AppCompatActivity) getActivity(), new BottomBarEventsUserFragment(),BottomBarEventsUserFragment.class.getSimpleName(),R.id.fragment_container_view_bottom_bar);
            } else {
                Utilities.replaceFragmentOnContainer((AppCompatActivity) getActivity(), new BottomBarEventsAdminFragment(),BottomBarEventsAdminFragment.class.getSimpleName(),R.id.fragment_container_view_bottom_bar);
            }
        }

        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // code for landscape mode
            Guideline guideLine = (Guideline) view.findViewById(R.id.guideline_bottom_bar);
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) guideLine.getLayoutParams();
            params.guidePercent = 0.80f; // 45% // range: 0 <-> 1
            guideLine.setLayoutParams(params);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Activity activity = getActivity();
        EventListViewModel eventListViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(EventListViewModel.class);
        eventListViewModel.setSelectedEventItem(null);
    }


}
