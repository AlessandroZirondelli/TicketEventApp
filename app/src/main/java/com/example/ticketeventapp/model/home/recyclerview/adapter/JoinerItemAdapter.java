package com.example.ticketeventapp.model.home.recyclerview.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.model.home.recyclerview.item_diff_callback.JoinerCardItemDiffCallback;
import com.example.ticketeventapp.model.home.recyclerview.viewholder.JoinerViewHolder;
import com.example.ticketeventapp.model.mng_tickets.Ticket;
import com.example.ticketeventapp.model.mng_users.User;
import com.example.ticketeventapp.viewmodel.mng_events.EventListViewModel;
import com.example.ticketeventapp.viewmodel.mng_tickets.TicketListViewModel;
import java.util.ArrayList;
import java.util.List;

public class JoinerItemAdapter extends RecyclerView.Adapter<JoinerViewHolder> implements Filterable {

    private Activity activity;
    private TicketListViewModel ticketListViewModel;
    private EventListViewModel eventListViewModel;
    private List<Ticket> ticketsNotFilteredList;
    private List<Ticket> ticketsFilteredList;
    private List<User> usersList;

    private Filter ticketFilter;

    public JoinerItemAdapter(Activity activity, List<Ticket> ticketList, List<User> usersList){
        this.activity = activity;
        ticketListViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(TicketListViewModel.class);
        eventListViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(EventListViewModel.class);
        ticketsFilteredList = ticketList;
        ticketsNotFilteredList = ticketList;
        this.usersList = usersList;
        this.createFilter();
    }

    @NonNull
    @Override
    public JoinerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.joiner_layout, parent, false );
        return new JoinerViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull JoinerViewHolder holder, int position) {
        //Set full name and check if ticket is already scanned or not
        Ticket ticket = ticketsFilteredList.get(position);
        if(ticket.isValidated()){//user is present at the event
            Drawable drawable = AppCompatResources.getDrawable(activity, activity.getResources().getIdentifier("@drawable/present_joiner","drawable",activity.getPackageName()));
            holder.setPresenceDrawable(drawable);
        } else { //user is absent at the event
            Drawable drawable = AppCompatResources.getDrawable(activity, activity.getResources().getIdentifier("@drawable/absent_joiner","drawable",activity.getPackageName()));
            holder.setPresenceDrawable(drawable);
        }
        String username_joiner = ticket.getUsername(); //this person can be absent of present ath the event. We need to get his full name

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            User buyer = this.usersList.stream().filter((user)->user.getUsername().equals(username_joiner)).findFirst().get();
            holder.setJoiner_full_name(buyer.getName()+" "+ buyer.getSurname());
        } else {
            //TODO
        }

    }

    @Override
    public int getItemCount() {
        return this.ticketsFilteredList.size();
    }


    public void setData(List<Ticket> ticketList){
        final JoinerCardItemDiffCallback diffCallback = new JoinerCardItemDiffCallback(this.ticketsFilteredList, ticketList);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.ticketsFilteredList  = new ArrayList<>(ticketList);
        this.ticketsNotFilteredList = new ArrayList<>(ticketList);
        //check difference between lists and update only changes
        diffResult.dispatchUpdatesTo(this);
    }

    public void setUsersList(List<User> usersList){
        this.usersList = usersList;
    }

    private  void updateCardListItems(List<Ticket> filteredList) {
        final JoinerCardItemDiffCallback diffCallback =
                new JoinerCardItemDiffCallback(this.ticketsFilteredList, filteredList);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.ticketsFilteredList = new ArrayList<>(filteredList);
        diffResult.dispatchUpdatesTo(this);
    }


    private void createFilter(){
        this.ticketFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String filterType = charSequence.toString();
                List<Ticket> filteredList = new ArrayList<>();

                if (filterType.isEmpty() || filterType.equals("all")) { //all events
                    filteredList.addAll(ticketsNotFilteredList);

                }
                else  if(filterType.equals("present")){ //past events
                    for(Ticket ticket : ticketsNotFilteredList){
                        if(ticket.isValidated()){ // Add only if present
                                filteredList.add(ticket);
                            }
                    }
                }
                else if(filterType.equals("absent")){ //near 30 km
                    for(Ticket ticket : ticketsNotFilteredList){
                        if(!ticket.isValidated()){ // Add only if absent
                            filteredList.add(ticket);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                List<Ticket> filteredList = new ArrayList<>();
                List<?> result = (List<?>) filterResults.values;
                for (Object object : result) {
                    if (object instanceof Ticket) {
                        filteredList.add((Ticket) object);
                    }
                }
                //warn the adapter that the data are changed after the filtering
                updateCardListItems(filteredList);
            }
        };
    }

    @Override
    public Filter getFilter() {
        return this.ticketFilter;
    }
}
