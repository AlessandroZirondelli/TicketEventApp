package com.example.ticketeventapp.model.home.recyclerview.viewholder;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.model.home.recyclerview.onitemlistener.OnItemListener;


public class JoinerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public  ImageView presence;
    private TextView joiner_full_name;

    private final OnItemListener itemListener;

    public JoinerViewHolder(@NonNull View itemView, OnItemListener itemListener) {
        super(itemView);
        presence = itemView.findViewById(R.id.presence_image_view);
        joiner_full_name = itemView.findViewById(R.id.joiner_text_view);
        this.itemListener = itemListener;
        this.itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemListener.onItemClick(getAdapterPosition());
    }

    public ImageView getPresence() {
        return presence;
    }

    public void setPresenceDrawable(Drawable presence) {
        this.presence.setImageDrawable(presence);
    }

    public TextView getJoiner_full_name() {
        return joiner_full_name;
    }

    public void setJoiner_full_name(String joiner_full_name) {
        this.joiner_full_name.setText(joiner_full_name);
    }
}
