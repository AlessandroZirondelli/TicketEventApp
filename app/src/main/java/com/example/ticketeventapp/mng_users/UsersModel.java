package com.example.ticketeventapp.mng_users;

import java.util.List;

public class UsersModel {

    private List<User> userList;

    public void setUserList(List<User> list){
        userList = list;
    }

    public List<User> getUsersList(){
        return userList;
    }
}
