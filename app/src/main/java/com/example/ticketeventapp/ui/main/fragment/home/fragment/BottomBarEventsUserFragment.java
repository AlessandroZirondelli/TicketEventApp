package com.example.ticketeventapp.ui.main.fragment.home.fragment;

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
import com.example.ticketeventapp.ui.main.fragment.qr_code_scanner.QrCodeScannerFragment;
import com.example.ticketeventapp.ui.utilities.Utilities;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomBarEventsUserFragment extends Fragment {
    private Activity activity;
    private BottomNavigationView  bottomNavigationView;
    private HomeFragment hostFragment;


    public void setHostFragment(HomeFragment hostFragment){
        this.hostFragment = hostFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.navigation_bar_user,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = getActivity();
        bottomNavigationView = view.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.events_bottom_navigation && !item.isChecked()){ // isChecked is to resolve double tap bu. It displayed blank
                Utilities.replaceNestedFragmentOnHomeFragment((AppCompatActivity) activity,hostFragment,new RecyclerViewEventsFragment(), RecyclerViewTicketsFragment.class.getSimpleName(),R.id.fragment_container_recycler_view);
            }

            if(item.getItemId() == R.id.tickets_bottom_navigation && !item.isChecked()){
                Utilities.replaceNestedFragmentOnHomeFragment((AppCompatActivity) activity,hostFragment,new RecyclerViewTicketsFragment(), RecyclerViewEventsFragment.class.getSimpleName(),R.id.fragment_container_recycler_view);
            }

            if(item.getItemId() == R.id.scan_bottom_navigation){
                QrCodeScannerFragment qrCodeScannerFragment = new QrCodeScannerFragment();
                qrCodeScannerFragment.setScanTicketMode(false);
                Utilities.replaceNestedFragmentOnHomeFragment((AppCompatActivity) activity,hostFragment,qrCodeScannerFragment, QrCodeScannerFragment.class.getSimpleName(),R.id.fragment_container_recycler_view);
            }
            return true;
        });
    }
}
