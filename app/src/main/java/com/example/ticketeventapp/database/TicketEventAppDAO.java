package com.example.ticketeventapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.ticketeventapp.model.mng_users.User;

import java.util.List;

@Dao
public interface TicketEventAppDAO {

    @Transaction
    @Query("SELECT * FROM user")
    List<User> getUsersList();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addUser(User user);

    @Transaction
    @Query("SELECT * FROM user")
    LiveData<List<User>> getUsersLiveData();




}
