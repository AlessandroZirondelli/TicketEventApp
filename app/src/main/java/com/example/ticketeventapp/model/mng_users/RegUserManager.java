package com.example.ticketeventapp.model.mng_users;

import android.os.Build;
import android.util.Log;

import java.util.List;

public class RegUserManager {

    private List<User> usersList;

    public RegUserManager(List<User> list){
        usersList = list;
    }


    public boolean isUsernameAvailable(String username){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Log.e("RegistrationFragment","Sono qui1");
            return !usersList.stream().anyMatch((user)->user.getUsername().equals(username));
        } else {
            Log.e("RegistrationFragment","Sono qui2");
            for(User user : usersList){
                if(user.getUsername().equals(username)){
                    return false;
                }
            }
            return true;
            }
    }

    public List<User> getUsersList(){
        return usersList;
    }

    public void setUsersList(List<User> list){
        usersList = list;
    }

    public int  areFilledFields(String name, String surname, String username, String password){
        if(name.isEmpty()){
            return 1;
        }
        if(surname.isEmpty()){
            return 2;
        }
        if(username.isEmpty()){
            return 3;
        }
        if(password.isEmpty()){
            return 4;
        }
        return 0; // if return 0 , all fields are filled
    }

}
