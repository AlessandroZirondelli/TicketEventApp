package com.example.ticketeventapp.model.mng_events;

public class AddEventManager {

    public int areFilledFields(String name, String description, String place, String date, String time, String price){
        if(name.isEmpty()){
            return 1;
        }
        if(description.isEmpty()){
            return 2;
        }
        if(place.isEmpty()){
            return 3;
        }
        if(date.isEmpty()){
            return 4;
        }
        if(time.isEmpty()){
            return 5;
        }
        if(price.isEmpty()){
            return 6;
        }
        return 0; // if return 0 , all fields are filled
    }

    public boolean isPriceNumber(String price){
        try {
            Double.parseDouble(price);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

}
