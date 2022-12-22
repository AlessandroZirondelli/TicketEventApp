package com.example.ticketeventapp.model.home.recyclerview;

import android.app.Activity;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.model.home.recyclerview.adapter.TicketItemAdapter;
import com.example.ticketeventapp.model.home.recyclerview.onitemlistener.OnItemListener;
import com.example.ticketeventapp.model.mng_tickets.Ticket;

import java.util.ArrayList;
import java.util.List;

public class JoinersRecyclerView {
    private Activity activity;
    private RecyclerView ticketsRecyclerView;
    //private TicketItemAdapter ticketItemAdapter;

    public JoinersRecyclerView(Activity activity){
        this.activity = activity;
    }


    public void  setRecyclerView(OnItemListener onItemListener){
        if(activity == null){
            Log.e("Bug","Activity null");
        }
        //ticketsRecyclerView = activity.findViewById(R.id.recycler_view_tickets);
        //ticketsRecyclerView.setHasFixedSize(true);
        // Here I should set Adapter to pass data to display
        //List<Ticket> ticketList = new ArrayList<>();
        //this.ticketItemAdapter = new TicketItemAdapter(activity, ticketList, onItemListener );
        //ticketsRecyclerView.setAdapter(ticketItemAdapter);

    }
    /*
    public TicketItemAdapter getEventItemAdapter(){
        return this.ticketItemAdapter;
    }*/


}
