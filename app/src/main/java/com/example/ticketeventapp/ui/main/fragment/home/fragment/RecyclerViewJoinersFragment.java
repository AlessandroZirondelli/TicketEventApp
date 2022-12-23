package com.example.ticketeventapp.ui.main.fragment.home.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import java.util.List;


public class RecyclerViewJoinersFragment extends Fragment {

    private UsersViewModelRegLog usersViewModelRegLog;
    private TicketListViewModel ticketListViewModel;
    private JoinersRecyclerView joinersRecyclerView;
    private EventListViewModel eventListViewModel;
    private JoinerItemAdapter joinerItemAdapter;
    private Activity activity;
    private TicketsManager ticketsManager;


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
        joinerItemAdapter = joinersRecyclerView.getJoinerItemAdapter();
        ticketsManager = new TicketsManager();


        usersViewModelRegLog.getUsersLiveData().observe((LifecycleOwner) activity, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                joinerItemAdapter.setUsersList(users);
            }
        });

        ticketListViewModel.getTicketsLiveData().observe((LifecycleOwner) activity, new Observer<List<Ticket>>() {
            @Override
            public void onChanged(List<Ticket> ticketList) {
                Event selectedEvent = eventListViewModel.getSelectedEventItem().getValue();
                ticketsManager.setTicketList(ticketList);
                List<Ticket> ticketOfSpecificEvent = ticketsManager.getTicketOfEvent(selectedEvent.getId());
                joinerItemAdapter.setData(ticketOfSpecificEvent);
            }
        });





    }
}
