package com.example.ticketeventapp.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.ticketeventapp.model.mng_users.User;

import java.util.List;

public class TicketEventAppRepository {

    private TicketEventAppDAO ticketEventAppDAO;


    private LiveData<List<User>> usersList;

    public TicketEventAppRepository(Application application){
        TicketEventAppDatabase db = TicketEventAppDatabase.getDatabase(application);
        ticketEventAppDAO = db.ticketEventAppDAO();
        usersList = ticketEventAppDAO.getUsersList();
    }

    public LiveData<List<User>> getUsersList(){
        return usersList;
    }

    public void addUser(User user){
        TicketEventAppDatabase.executor.execute(new Runnable() {

            @Override
            public void run() {
                ticketEventAppDAO.addUser(user);
            }
        });
    }

}
