package com.example.ticketeventapp.model.home.recyclerview;

import android.app.Activity;
import android.util.Log;

public class OnEventListener implements  OnItemListener{
    @Override
    public void onItemClick(int position) {
            Log.e("HomeFragment","Clickato:"+position);
    }
}
