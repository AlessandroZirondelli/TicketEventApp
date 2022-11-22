package com.example.ticketeventapp.model.mng_users;

import android.os.Build;
import android.util.Log;

import java.util.List;

public class LogUserManager {

    private List<User> usersList;

    public LogUserManager(List<User> list){
        usersList = list;
    }


    public List<User> getUsersList(){
        return usersList;
    }

    public void setUsersList(List<User> list){
        usersList = list;
    }

    public int  areFilledFields(String username, String password){
        if(username.isEmpty()){
            return 1;
        }
        if(password.isEmpty()){
            return 2;
        }
        return 0; // if return 0 , all fields are filled
    }

    public int areCorrectCredentials(String username, String password){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            User user = usersList.stream().filter((item)->item.getUsername().equals(username)).findFirst().orElse(null);
            if(user == null){
                //Log.e("LoginFragment","user null");
                return 1; //wrong username
            }
            if(!user.getPassword().equals(password)){
                //Log.e("LoginFragment","Password inserted is:"+user.getPassword());
                return 2; //wrong password
            }
            return 0; //if returns 0, le credentials are not correct
        } else {
            for(User user : getUsersList()){
                if(user.getUsername().equals(username)){
                    if(!user.getPassword().equals(password)){
                        return 2; //wrong password
                    } else {
                        return 0;
                    }
                }
            }
            return 1; //wrong username
        }

    }

}
