package com.example.ticketeventapp.model.home.recyclerview.viewholder;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ticketeventapp.R;


public class JoinerViewHolder extends RecyclerView.ViewHolder{

    public  ImageView presence;
    private TextView joiner_full_name;

    public JoinerViewHolder(@NonNull View itemView) {
        super(itemView);
        presence = itemView.findViewById(R.id.presence_image_view);
        joiner_full_name = itemView.findViewById(R.id.joiner_text_view);
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
