package com.example.ticketeventapp.model.home.recyclerview;

import android.app.Activity;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.model.home.recyclerview.adapter.JoinerItemAdapter;
import com.example.ticketeventapp.model.mng_tickets.Ticket;
import com.example.ticketeventapp.model.mng_users.User;

import java.util.ArrayList;
import java.util.List;

public class JoinersRecyclerView {

    private Activity activity;
    private RecyclerView joinersRecyclerView;
    private JoinerItemAdapter joinerItemAdapter;

    public JoinersRecyclerView(Activity activity){
        this.activity = activity;
    }

    public void  setRecyclerView(){
        if(activity == null){
            Log.e("Bug","Activity null");
        }
        joinersRecyclerView = activity.findViewById(R.id.recycler_view_joiners);
        joinersRecyclerView.setHasFixedSize(true);
        // Here I should set Adapter to pass data to display
        List<Ticket> ticketList = new ArrayList<>();
        this.joinerItemAdapter = new JoinerItemAdapter(activity, ticketList );//TicketList, USerList
        joinersRecyclerView.setAdapter(joinerItemAdapter);

    }

    public JoinerItemAdapter getJoinerItemAdapter(){
        return this.joinerItemAdapter;
    }

}
