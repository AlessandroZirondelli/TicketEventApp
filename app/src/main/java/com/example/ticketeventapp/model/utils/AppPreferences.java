package com.example.ticketeventapp.model.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import com.example.ticketeventapp.model.mng_users.User;

import java.time.LocalDate;

public class AppPreferences {

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor sharedPreferencesEditor;

    public AppPreferences(Context context){
        sharedPreferences = context.getSharedPreferences("login",Context.MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();
    }

    public void setLoggedUser(User user){
        sharedPreferencesEditor.putString("logged_username", user.getUsername());
        sharedPreferencesEditor.commit();
        sharedPreferencesEditor.putString("logged_password",user.getPassword());
        sharedPreferencesEditor.commit();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate expirationDate = LocalDate.now().plusDays(21); //yyyy-mm-dd
            sharedPreferencesEditor.putString("logged_expiration",expirationDate.toString());
            sharedPreferencesEditor.commit();
            Log.e("LoginFragment","Expiration date aggiunta");
        } else {
            //TODO
        }
    }

    public User getLoggedUser(){
        String username = sharedPreferences.getString("logged_username",null);
        String password = sharedPreferences.getString("logged_password",null);
        return new User(username,password);
    }

    public LocalDate getExpirationDate(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.e("LoginFragment","Leggo expiration date"+sharedPreferences.getString("logged_expiration",null));
            String date = sharedPreferences.getString("logged_expiration",null);
            if(date==null){
                return null;
            } else {
                return LocalDate.parse(sharedPreferences.getString("logged_expiration",null));
            }

        } else{
            //TODO
            Log.e("LoginFragment","Data reperita nulla");
            return null;
        }
    }

    public Boolean canDoAutoLogin(){ /*Check if it's possible to do autologin */
        User user = getLoggedUser();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate todayDate = LocalDate.now();
            if(user.getUsername()==null || user.getUsername()== null || getExpirationDate()==null ){
                return false;
            }
            if(!user.getUsername().isEmpty() && !user.getPassword().isEmpty() && !todayDate.isAfter(getExpirationDate()) ){
                return true;
            }
            //Expiration date or username/password not saved in shared preferences
            sharedPreferencesEditor.clear();
            sharedPreferencesEditor.commit();
            return false;
        }
        else{
            return false;
            //TODO
        }

    }






}
