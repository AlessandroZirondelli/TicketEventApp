package com.example.ticketeventapp.model.mng_events;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "event")
public class Event {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String description;
    private String date;
    private String time;
    private String place;
    private String price;
    private String imageUri;
    private String latitude;
    private String longitude;

    public Event( String name, String description, String date, String time, String place, String price, String imageUri, String latitude, String longitude) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.time = time;
        this.place = place;
        this.price = price;
        this.imageUri = imageUri;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
