package com.example.ticketeventapp.model.home.recyclerview.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.model.home.recyclerview.item_diff_callback.EventCardItemDiffCallback;
import com.example.ticketeventapp.model.home.recyclerview.item_diff_callback.TicketCardItemDiffCallback;
import com.example.ticketeventapp.model.home.recyclerview.onitemlistener.OnItemListener;
import com.example.ticketeventapp.model.home.recyclerview.viewholder.TicketViewHolder;
import com.example.ticketeventapp.model.mng_events.Event;
import com.example.ticketeventapp.model.mng_tickets.Ticket;
import com.example.ticketeventapp.viewmodel.mng_tickets.TicketListViewModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TicketItemAdapter extends RecyclerView.Adapter<TicketViewHolder>{

    private Activity activity;
    private List<Ticket> ticketsList;
    private OnItemListener listener;
    private TicketListViewModel ticketListViewModel;


    public TicketItemAdapter(Activity activity, List<Ticket> ticketList, OnItemListener listener){
        this.ticketsList = ticketList;
        this.activity = activity;
        this.listener = listener;
        ticketListViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(TicketListViewModel.class);
    }


    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card_layout, parent, false );
        return new TicketViewHolder(layoutView,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        Ticket ticket = ticketsList.get(position);
        holder.set_event_name_text("TestName");
        holder.set_event_date_text("TestDate");
        holder.set_event_time_text("TestTime");
        /*if(holder.qrcode!=null){
            Log.e("Bug","Holder qrcode NOT null");
        }


        Picasso.get().load(R.drawable.calendar_month).placeholder(R.drawable.qr_code_2).into(holder.qrcode, new Callback() {
            @Override
            public void onSuccess() {
                Log.e("Bug","Loaded image ok");
            }

            @Override
            public void onError(Exception e) {

                Log.e("Bug",e.toString());
            }
        });
        Drawable drawable;*/

        Drawable drawable = AppCompatResources.getDrawable(activity, activity.getResources().getIdentifier("@drawable/qr_code_2","drawable",activity.getPackageName()));
        holder.set_event_photo_drawable(drawable);

    }

    @Override
    public int getItemCount() {
        return ticketsList.size();
    }

    public void setData(List<Ticket> ticketList){
        final TicketCardItemDiffCallback diffCallback = new TicketCardItemDiffCallback(this.ticketsList, ticketList);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.ticketsList  = new ArrayList<>(ticketList);
        //check difference between lists and update only changes
        diffResult.dispatchUpdatesTo(this);
    }
}
