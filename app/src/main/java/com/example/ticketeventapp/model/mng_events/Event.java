package com.example.ticketeventapp.model.mng_events;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class Event {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String description;
    private String date;
    private String time;
    private String place;
    private Double price;
    private String imageUri;

    public Event(String imageUri, String name, String description, String place, String date, String time, Double price) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.time = time;
        this.place = place;
        this.price = price;
        this.imageUri = imageUri;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
