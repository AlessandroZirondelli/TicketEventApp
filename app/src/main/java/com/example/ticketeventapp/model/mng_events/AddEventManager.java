package com.example.ticketeventapp.model.mng_events;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.apache.commons.lang3.RandomStringUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class AddEventManager {

    private List<Event> eventList;


    public void setEventList(List<Event> eventList){
        this.eventList = eventList;
    }

    public static String generateRandomString(){
        return RandomStringUtils.randomAlphanumeric(255);
    }

    public static Bitmap generateBitmapQrCode(String str) throws WriterException {
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix = writer.encode(str, BarcodeFormat.QR_CODE, 400, 400);
        int w = bitMatrix.getWidth();
        int h = bitMatrix.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                pixels[y * w + x] = bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
        return bitmap;
    }


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

    public Event isValidCodeEvent(String codeToCheck){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Optional<Event> scannedEvent = this.eventList.stream().filter((event)->event.getCode().equals(codeToCheck)).findFirst();
            if(scannedEvent.isPresent()){
                return scannedEvent.get();
            }
            return null;
        } else {return null;}
    }

}
