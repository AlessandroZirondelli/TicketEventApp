package com.example.ticketeventapp.model.home.recyclerview;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.model.home.recyclerview.onitemlistener.OnItemListener;
import com.example.ticketeventapp.model.mng_events.Event;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EventItemAdapter extends RecyclerView.Adapter<EventViewHolder> {

    private Activity activity;
    private List<Event> eventList;
    private OnItemListener listener;

    public EventItemAdapter(Activity activity,List<Event> eventList, OnItemListener listener){
        this.eventList = eventList;
        this.activity = activity;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card_layout, parent, false );
        return new EventViewHolder(layoutView,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.set_event_name_text(event.getName());
        holder.set_event_date_text(event.getDate());
        holder.set_event_time_text(event.getTime());

        String image_uri = event.getImageUri();
        if(image_uri.contains("add_photo_alternate")){
            Drawable drawable = AppCompatResources.getDrawable(activity, activity.getResources().getIdentifier(image_uri,"drawable",activity.getPackageName()));
            holder.set_event_photo_drawable(drawable);
        } else { //User loaded a photo
            /*Bitmap bitmap = Utilities.getImageBitmap(activity, Uri.parse(image));
            holder.placeImageView.setImageBitmap(bitmap);*/

            //My solution below
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), Uri.parse(image_uri)); //
                holder.set_event_photo_bitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public int getItemCount() {
        return this.eventList.size();
    }

    public void setData(List<Event> eventList){
        final CardItemDiffCallback diffCallback = new CardItemDiffCallback(this.eventList, eventList);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.eventList = new ArrayList<>(eventList);
        //check difference between lists and update only changes
        diffResult.dispatchUpdatesTo(this);
    }
}
