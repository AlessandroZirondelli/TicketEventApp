package com.example.ticketeventapp.ui.main.fragment.home.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.model.home.recyclerview.EventsRecyclerView;
import com.example.ticketeventapp.model.home.recyclerview.TicketsRecyclerView;
import com.example.ticketeventapp.model.home.recyclerview.adapter.TicketItemAdapter;
import com.example.ticketeventapp.model.home.recyclerview.onitemlistener.OnItemListener;
import com.example.ticketeventapp.model.mng_tickets.Ticket;
import com.example.ticketeventapp.viewmodel.mng_tickets.TicketListViewModel;

import java.util.List;

public class RecyclerViewTicketsFragment extends Fragment implements OnItemListener {

    private TicketsRecyclerView ticketsRecyclerView;
    private TicketListViewModel ticketListViewModel;
    private TicketItemAdapter ticketItemAdapter;
    private ImageView logout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recycler_view_tickets_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ticketListViewModel = new ViewModelProvider(getActivity()).get(TicketListViewModel.class);
        ticketsRecyclerView = new TicketsRecyclerView(getActivity());
        ticketsRecyclerView.setRecyclerView(this);
        ticketItemAdapter = ticketsRecyclerView.getEventItemAdapter();

        ticketListViewModel.getTicketsLiveData().observe(getActivity(), new Observer<List<Ticket>>() {
            @Override
            public void onChanged(List<Ticket> ticketList) {
                ticketItemAdapter.setData(ticketList);
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Log.e("Tickets","Click on ticket");
    }
}