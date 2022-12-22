package com.example.ticketeventapp.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.ticketeventapp.model.mng_events.Event;
import com.example.ticketeventapp.model.mng_tickets.Ticket;
import com.example.ticketeventapp.model.mng_users.User;

import java.util.List;

public class TicketEventAppRepository {

    private TicketEventAppDAO ticketEventAppDAO;
    private LiveData<List<User>> usersLiveData;
    private LiveData<List<Event>> eventsLiveData;
    private LiveData<List<Ticket>> ticketsLiveData;


    public TicketEventAppRepository(Application application){
        TicketEventAppDatabase db = TicketEventAppDatabase.getDatabase(application);
        ticketEventAppDAO = db.ticketEventAppDAO();
        usersLiveData = ticketEventAppDAO.getUsersLiveData();
        eventsLiveData = ticketEventAppDAO.getEventsLiveData();
        ticketsLiveData = ticketEventAppDAO.getTicketsLiveData();
    }

    public LiveData<List<User>> getUsersLiveData(){
        return usersLiveData;
    }

    public LiveData<List<Event>> getEventsLiveData(){return eventsLiveData;}

    public void addUser(User user){
        TicketEventAppDatabase.executor.execute(new Runnable() {

            @Override
            public void run() {
                ticketEventAppDAO.addUser(user);
            }
        });
    }

    public void addEvent(Event event){
        TicketEventAppDatabase.executor.execute(new Runnable() {

            @Override
            public void run() { ticketEventAppDAO.addEvent(event);}
        });
    }

    public void updateEvent(Event event){
        TicketEventAppDatabase.executor.execute(new Runnable() {

            @Override
            public void run() { ticketEventAppDAO.updateEvent(event);}
        });
    }

    public void addTicket(Ticket ticket){
        TicketEventAppDatabase.executor.execute(new Runnable() {

            @Override
            public void run() { ticketEventAppDAO.addTicket(ticket);}
        });
    }

    public LiveData<List<Ticket>> getTicketsLiveData(){
        return ticketsLiveData;
    }


    public void updateTicket(Ticket ticket){
        TicketEventAppDatabase.executor.execute(new Runnable() {

            @Override
            public void run() { ticketEventAppDAO.updateTicket(ticket);}
        });
    }

}
