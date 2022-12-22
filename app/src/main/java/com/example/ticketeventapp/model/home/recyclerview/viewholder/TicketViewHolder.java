package com.example.ticketeventapp.model.home.recyclerview.viewholder;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.model.home.recyclerview.onitemlistener.OnItemListener;


public class TicketViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public ImageView qrcode;
    private TextView event_name;
    private TextView event_date;
    private TextView event_time;

    private final OnItemListener itemListener;

    public TicketViewHolder(@NonNull View itemView, OnItemListener itemListener) {
        super(itemView);
        qrcode = itemView.findViewById(R.id.event_imageview);
        event_name = itemView.findViewById(R.id.event_name_textview);
        event_date = itemView.findViewById(R.id.event_date_textview);
        event_time = itemView.findViewById(R.id.event_time_textview);
        this.itemListener = itemListener;
        this.itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemListener.onItemClick(getAdapterPosition());
    }

    public void set_event_name_text(String event_name_text) {
        this.event_name.setText(event_name_text);
    }

    public void set_event_date_text(String  event_date_text){
        this.event_date.setText(event_date_text);
    }

    public void set_event_time_text(String event_time_text){
        this.event_time.setText(event_time_text);
    }

    public void set_event_photo_drawable(Drawable image) {
        this.qrcode.setImageDrawable(image);
    }

    public void set_event_photo_bitmap(Bitmap image){
        this.qrcode.setImageBitmap(image);
    }


}
