package com.example.ticketeventapp.database;

import androidx.room.Dao;

/*
*   Qui andremo a specificare tutte le query al database
*  */

@Dao
public interface TicketEventAppDAO {
    /*Se inseriamo nella tabella una riga con gli stessi valori, specifichiamo cosa succede e come gestire tramite onConflict */
    /*@Insert(onConflict = OnConflictStrategy.IGNORE)
    void addCardItem(CardItem cardItem);

    //Forziamo il fatto che questa query venga eseguita in maniera atomica tramite transaction
    @Transaction
    @Query("SELECT * FROM item ORDER BY item_id DESC")
    LiveData<List<CardItem>> getCardItems();*/

}
