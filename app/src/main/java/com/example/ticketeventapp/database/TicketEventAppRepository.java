package com.example.ticketeventapp.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.ticketeventapp.model.mng_events.Event;
import com.example.ticketeventapp.model.mng_users.User;

import java.util.List;

public class TicketEventAppRepository {

    private TicketEventAppDAO ticketEventAppDAO;
    private LiveData<List<User>> usersLiveData;

    public TicketEventAppRepository(Application application){
        TicketEventAppDatabase db = TicketEventAppDatabase.getDatabase(application);
        ticketEventAppDAO = db.ticketEventAppDAO();
        usersLiveData = ticketEventAppDAO.getUsersLiveData();
    }

    public LiveData<List<User>> getUsersLiveData(){
        return usersLiveData;
    }


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

}
