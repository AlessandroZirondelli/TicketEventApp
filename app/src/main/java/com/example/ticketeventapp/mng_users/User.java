package com.example.ticketeventapp.mng_users;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "user", indices={
        @Index(value="username")}
)

public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String surname;
    private String username;
    private String password;
    private Boolean type; // 0 means Administrator , 1 means Customer

    public User(String name, String surname, String username, String password, Boolean type) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", type=" + type +
                '}';
    }
}
