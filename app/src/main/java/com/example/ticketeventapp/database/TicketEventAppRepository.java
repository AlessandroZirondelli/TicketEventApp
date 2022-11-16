package com.example.ticketeventapp.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TicketEventAppRepository {

    private TicketEventAppDAO ticketEventAppDAO;
    //private LiveData<List<CardItem>> cardItemList;

    public TicketEventAppRepository(Application application){
        TicketEventAppDatabase db = TicketEventAppDatabase.getDatabase(application);
        ticketEventAppDAO = db.ticketEventAppDAO();
        //cardItemList = cardItemDAO.getCardItems();
    }

    /*public LiveData<List<CardItem>> getCardItemList(){
        return cardItemList;
    }*/

    /*public void addCardItem(CardItem cardItem){
        TicketEventAppDatabase.executor.execute(new Runnable() {

            @Override
            public void run() {
                cardItemDAO.addCardItem(cardItem);
            }
        });
    }*/

}
