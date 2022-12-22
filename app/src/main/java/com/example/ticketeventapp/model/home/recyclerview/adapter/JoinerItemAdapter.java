package com.example.ticketeventapp.model.home.recyclerview.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
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
import com.example.ticketeventapp.model.home.recyclerview.item_diff_callback.TicketCardItemDiffCallback;
import com.example.ticketeventapp.model.home.recyclerview.onitemlistener.OnItemListener;
import com.example.ticketeventapp.model.home.recyclerview.viewholder.JoinerViewHolder;
import com.example.ticketeventapp.model.home.recyclerview.viewholder.TicketViewHolder;
import com.example.ticketeventapp.model.mng_events.Event;
import com.example.ticketeventapp.model.mng_tickets.Ticket;
import com.example.ticketeventapp.model.mng_users.User;
import com.example.ticketeventapp.viewmodel.mng_events.EventListViewModel;
import com.example.ticketeventapp.viewmodel.mng_tickets.TicketListViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JoinerItemAdapter extends RecyclerView.Adapter<JoinerViewHolder>{

    private Activity activity;
    private List<User> joinerList;
    private OnItemListener listener;
    private TicketListViewModel ticketListViewModel;
    private EventListViewModel eventListViewModel;
    private List<Ticket> ticketList;

    public JoinerItemAdapter(Activity activity, List<User> joinerList, OnItemListener listener){
        this.joinerList = joinerList;
        this.activity = activity;
        this.listener = listener;
        ticketListViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(TicketListViewModel.class);
        eventListViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(EventListViewModel.class);
        /*Event selectedEvent = eventListViewModel.getSelectedEventItem().getValue();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ticketList = ticketListViewModel.getTicketsLiveData().getValue().stream().filter((ticket)->ticket.getId_event() == selectedEvent.getId()).collect(Collectors.toList());
        }*/
    }

    @NonNull
    @Override
    public JoinerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_joiners_layout, parent, false );
        return new JoinerViewHolder(layoutView,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull JoinerViewHolder holder, int position) {
        User joiner = joinerList.get(position);
        holder.setJoiner_full_name(joiner.getName());

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    /*public void setData(List<Ticket> ticketList){
        final TicketCardItemDiffCallback diffCallback = new TicketCardItemDiffCallback(this.ticketsList, ticketList);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.ticketsList  = new ArrayList<>(ticketList);
        //check difference between lists and update only changes
        diffResult.dispatchUpdatesTo(this);
    }

    public Ticket getItemSelected(int position) {
        return ticketsList.get(position);
    }*/
}
