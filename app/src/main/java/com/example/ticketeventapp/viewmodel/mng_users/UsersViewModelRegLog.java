package com.example.ticketeventapp.viewmodel.mng_users;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.ticketeventapp.database.TicketEventAppRepository;
import com.example.ticketeventapp.model.mng_users.User;

import java.util.List;

public class UsersViewModelRegLog extends AndroidViewModel {

    private TicketEventAppRepository repository;
    private LiveData<List<User>> usersLiveData;

    public UsersViewModelRegLog(@NonNull Application application){
        super(application);
        repository = new TicketEventAppRepository(application);
        usersLiveData = repository.getUsersLiveData();
    }

    public LiveData<List<User>> getUsersLiveData(){
        return usersLiveData;
    }

    public void addUser(User user){
        repository.addUser(user);
    }

}
