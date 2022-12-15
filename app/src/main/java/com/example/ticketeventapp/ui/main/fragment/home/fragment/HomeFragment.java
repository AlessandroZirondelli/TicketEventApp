package com.example.ticketeventapp.ui.main.fragment.home.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.model.utils.AppInfo;
import com.example.ticketeventapp.ui.utilities.Utilities;

public class HomeFragment extends Fragment {

    private Activity activity;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);

        if( savedInstanceState == null){
            Utilities.replaceFragmentInHomeFragment((AppCompatActivity) getActivity(), new RecyclerViewEventsFragment(), RecyclerViewEventsFragment.class.getSimpleName(),R.id.fragment_container_recycler_view,this);
            if(AppInfo.getInstance().getLoggedUser().isUser()){
                Utilities.replaceFragmentInHomeFragment((AppCompatActivity) getActivity(), new BottomBarEventsUserFragment(),BottomBarEventsUserFragment.class.getSimpleName(),R.id.fragment_container_view_bottom_bar,this);
            } else {
                Utilities.replaceFragmentInHomeFragment((AppCompatActivity) getActivity(), new BottomBarEventsAdminFragment(),BottomBarEventsAdminFragment.class.getSimpleName(),R.id.fragment_container_view_bottom_bar,this);
            }

            
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Activity activity = getActivity();
        //eventListViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(EventListViewModel.class);

    }


}
