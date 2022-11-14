package com.example.ticketeventapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;



import java.util.List;

/*
*   Qui andremo a specificare tutte le query al database
*  */

@Dao
public interface TicketEventDAO {
    /*Se inseriamo nella tabella una riga con gli stessi valori, specifichiamo cosa succede e come gestire tramite onConflict */
    /*@Insert(onConflict = OnConflictStrategy.IGNORE)
    void addCardItem(CardItem cardItem);

    //Forziamo il fatto che questa query venga eseguita in maniera atomica tramite transaction
    @Transaction
    @Query("SELECT * FROM item ORDER BY item_id DESC")
    LiveData<List<CardItem>> getCardItems();*/

}
