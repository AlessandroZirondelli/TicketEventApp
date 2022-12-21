package com.example.ticketeventapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.ticketeventapp.model.mng_events.Event;
import com.example.ticketeventapp.model.mng_tickets.Ticket;
import com.example.ticketeventapp.model.mng_users.User;

import java.util.List;

@Dao
public interface TicketEventAppDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addUser(User user);

    @Transaction
    @Query("SELECT * FROM user")
    LiveData<List<User>> getUsersLiveData();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addEvent(Event event);

    @Transaction
    @Query("SELECT * FROM event")
    LiveData<List<Event>> getEventsLiveData();

    @Update
    void updateEvent(Event event);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addTicket(Ticket ticket);





}
