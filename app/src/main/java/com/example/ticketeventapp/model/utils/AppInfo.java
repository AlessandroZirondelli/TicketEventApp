package com.example.ticketeventapp.model.utils;

import com.example.ticketeventapp.model.mng_users.User;

public class AppInfo {

    private static AppInfo mInstance= null;
    public User loggedUser;

    private AppInfo(){}

    public static synchronized AppInfo getInstance() {
            if(null == mInstance){
                mInstance = new AppInfo();
            }
            return mInstance;
    }

    public void setLoggedUser(User user){
        this.loggedUser = user;
    }

}
