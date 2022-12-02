package com.example.ticketeventapp.model.mng_events;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

    public Uri saveImage(Bitmap bitmap, Activity activity) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd__HHmmss", Locale.ITALY).format(new Date()); //serve per salvare il nome in modo univoco
        String name = "JPEG_" + timeStamp + ".jpg";

        ContentResolver contentResolver = activity.getContentResolver();

        ContentValues contentValues = new ContentValues();//definisce imppstazioni del file da salvare, quindi nome e formato file da salvare
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg");

        Uri imageURI = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        OutputStream outputStream = null;
        try {
            outputStream = contentResolver.openOutputStream(imageURI);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageURI;

    }

}
