package com.example.ticketeventapp.ui.main.fragment.home.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.model.home.recyclerview.JoinersRecyclerView;
import com.example.ticketeventapp.model.home.recyclerview.adapter.JoinerItemAdapter;
import com.example.ticketeventapp.model.home.recyclerview.viewholder.EventViewHolder;
import com.example.ticketeventapp.model.mng_events.Event;
import com.example.ticketeventapp.model.mng_tickets.Ticket;
import com.example.ticketeventapp.model.mng_tickets.TicketsManager;
import com.example.ticketeventapp.model.mng_users.User;
import com.example.ticketeventapp.viewmodel.mng_events.EventListViewModel;
import com.example.ticketeventapp.viewmodel.mng_tickets.TicketListViewModel;
import com.example.ticketeventapp.viewmodel.mng_users.UsersViewModelRegLog;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;


public class RecyclerViewJoinersFragment extends Fragment {

    private UsersViewModelRegLog usersViewModelRegLog;
    private TicketListViewModel ticketListViewModel;
    private JoinersRecyclerView joinersRecyclerView;
    private EventListViewModel eventListViewModel;
    private JoinerItemAdapter joinerItemAdapter;
    private Activity activity;
    private TicketsManager ticketsManager;
    private ChipGroup chipGroup;
    private Chip chipPresent;
    private Chip chipAbsent;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recycler_view_joiners_layout, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = getActivity();
        usersViewModelRegLog = new ViewModelProvider((ViewModelStoreOwner) activity).get(UsersViewModelRegLog.class);
        ticketListViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(TicketListViewModel.class);
        eventListViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(EventListViewModel.class);
        joinersRecyclerView = new JoinersRecyclerView(activity);
        joinersRecyclerView.setRecyclerView();
        joinerItemAdapter = joinersRecyclerView.getJoinerItemAdapter();
        joinerItemAdapter.setUsersList(usersViewModelRegLog.getUsersLiveData().getValue());
        ticketsManager = new TicketsManager();


        chipGroup = view.findViewById(R.id.chip_group_filter);
        chipAbsent = view.findViewById(R.id.absent_joiners_chip);
        chipPresent = view.findViewById(R.id.present_joiners_chip);

        chipGroup.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
                if(checkedIds.isEmpty()){
                    joinerItemAdapter.getFilter().filter("all");
                }
            }
        });

        chipPresent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    chipAbsent.setCheckable(false);
                    joinerItemAdapter.getFilter().filter("present");
                } else {
                    chipAbsent.setCheckable(true);
                }
            }
        });

        chipAbsent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    chipPresent.setCheckable(false);
                    joinerItemAdapter.getFilter().filter("absent");
                } else {
                    chipPresent.setCheckable(true);
                }
            }
        });


        usersViewModelRegLog.getUsersLiveData().observe((LifecycleOwner) activity, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                Log.e("Bug","Setto ora usersList");
                joinerItemAdapter.setUsersList(users);
            }
        });

        ticketListViewModel.getTicketsLiveData().observe((LifecycleOwner) activity, new Observer<List<Ticket>>() {
            @Override
            public void onChanged(List<Ticket> ticketList) {
                Event selectedEvent = eventListViewModel.getSelectedEventItem().getValue();
                if(selectedEvent != null){ // if it's null, it means that we've just deleted event
                    ticketsManager.setTicketList(ticketList);
                    List<Ticket> ticketOfSpecificEvent = ticketsManager.getTicketOfEvent(selectedEvent.getId());
                    Log.e("Bug","Setto ora i dati dell'adapter");
                    joinerItemAdapter.setData(ticketOfSpecificEvent);
                }
            }
        });





    }
}
