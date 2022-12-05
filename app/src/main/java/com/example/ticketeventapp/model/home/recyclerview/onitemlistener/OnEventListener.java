package com.example.ticketeventapp.model.home.recyclerview.onitemlistener;

import android.util.Log;

import com.example.ticketeventapp.model.home.recyclerview.onitemlistener.OnItemListener;

public class OnEventListener implements OnItemListener {
    @Override
    public void onItemClick(int position) {
            Log.e("HomeFragment","Clickato:"+position);
    }
}
