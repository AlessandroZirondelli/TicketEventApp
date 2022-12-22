package com.example.ticketeventapp.model.mng_tickets;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "ticket", indices={
        @Index(value="code")}
)
public class Ticket {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String code;
    private int id_event;
    private String username;
    private Boolean validated;

    public Ticket(String code, int id_event, String username, Boolean validated) {
        this.code = code;
        this.id_event = id_event;
        this.username = username;
        this.validated = validated;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getId_event() {
        return id_event;
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
    }


    public Boolean isValidated() {
        return validated;
    }

    public void setIsValidated(Boolean validated) {
        this.validated = validated;
    }
}
