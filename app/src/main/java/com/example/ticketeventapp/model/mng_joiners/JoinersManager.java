package com.example.ticketeventapp.model.mng_joiners;

import com.example.ticketeventapp.model.mng_tickets.Ticket;
import com.example.ticketeventapp.model.mng_users.User;

import java.util.List;

public class JoinersManager { //JoinersManager for specific event

    private List<User> joinersList; //joiner of specific event
    private List<Ticket> ticketList; //tickets list of specific event



    public boolean isPresentJoiner(User joiner){
        return false;
    }

    public void setJoinersList(List<User> joinersList) {
        this.joinersList = joinersList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }
}
