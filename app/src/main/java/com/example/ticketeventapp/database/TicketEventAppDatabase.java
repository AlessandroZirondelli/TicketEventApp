package com.example.ticketeventapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import com.example.ticketeventapp.model.mng_events.Event;
import com.example.ticketeventapp.model.mng_tickets.Ticket;
import com.example.ticketeventapp.model.mng_users.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Event.class, Ticket.class}, version = 1)
public abstract class TicketEventAppDatabase extends RoomDatabase {

    public abstract TicketEventAppDAO ticketEventAppDAO();
    private static volatile TicketEventAppDatabase INSTANCE;
    static final ExecutorService executor = Executors.newFixedThreadPool(4);

    static TicketEventAppDatabase getDatabase(final Context context) {
        if(INSTANCE == null){
            synchronized (TicketEventAppDatabase.class){
                if(INSTANCE == null){ // ce lo richiediamo perchè eseguendo thread diversi potrebbe essere successo qualcosa nel mentre
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),TicketEventAppDatabase.class,
                            "ticket_event_app_database").build();

                }
            }
        }
        return INSTANCE;
    }


}
