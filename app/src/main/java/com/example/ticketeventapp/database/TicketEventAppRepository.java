package com.example.ticketeventapp.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.ticketeventapp.model.mng_users.User;

import java.util.List;

public class TicketEventAppRepository {

    private TicketEventAppDAO ticketEventAppDAO;

    private List<User> usersList;
    private LiveData<List<User>> usersLiveData;

    public TicketEventAppRepository(Application application){
        TicketEventAppDatabase db = TicketEventAppDatabase.getDatabase(application);
        ticketEventAppDAO = db.ticketEventAppDAO();
        usersList = ticketEventAppDAO.getUsersList();
        //usersLiveData = ticketEventAppDAO.getUsersLiveData();
    }

    /*public LiveData<List<User>> getUsersLiveData(){
        return usersLiveData;
    }*/

    public List<User> getUsersList(){
        return usersList;
    }

    public void addUser(User user){
        usersList.add(user);// DA CANCELLARE SE SI USA SOLO LIVEDATA
        TicketEventAppDatabase.executor.execute(new Runnable() {

            @Override
            public void run() {
                ticketEventAppDAO.addUser(user);
            }
        });
    }

}
