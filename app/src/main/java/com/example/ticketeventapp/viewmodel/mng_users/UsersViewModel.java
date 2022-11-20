package com.example.ticketeventapp.viewmodel.mng_users;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.ticketeventapp.database.TicketEventAppRepository;
import com.example.ticketeventapp.model.mng_users.User;

import java.util.List;

public class UsersViewModel extends AndroidViewModel {

    private LiveData<List<User>> usersList;
    private TicketEventAppRepository repository;

    public UsersViewModel(@NonNull Application application){
        super(application);
        repository = new TicketEventAppRepository(application);
        usersList = repository.getUsersList();

    }

    public LiveData<List<User>> getUsers(){
        return usersList;
    }

    public void addUser(User user){
        repository.addUser(user);
    }

}
