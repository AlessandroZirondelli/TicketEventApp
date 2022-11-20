package com.example.ticketeventapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import com.example.ticketeventapp.model.mng_users.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*Qui dobbiamo andare a specificare tutte le entities(tabelle) che sono presenti nel database. Nel nostro caso sula una. Dobbiamo esplicitarla come classe.*/
@Database(entities = {User.class}, version = 1)
public abstract class TicketEventAppDatabase extends RoomDatabase {

    /* Dobbiamo rendere pubbliche in maniera astratta le classi DAO che sono associate alle varie entities !!*/

    public abstract TicketEventAppDAO ticketEventAppDAO();

    //definisco una istanza singleton unica del database per tutto il progetto
    private static volatile TicketEventAppDatabase INSTANCE;

    //è necessaria un pool di thread per evitare che si blocchi la GUI
    //creo 4 thread, perchè è un numero "buono" per la gestione del DB e raccomandato da Android
    static final ExecutorService executor = Executors.newFixedThreadPool(4);

    static TicketEventAppDatabase getDatabase(final Context context) {
        if(INSTANCE == null){
            //syncronized ci permette di avere sinronizzato con il singleton
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
