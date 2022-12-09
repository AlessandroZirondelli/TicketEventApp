package com.example.ticketeventapp.model.home.recyclerview;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.model.home.recyclerview.onitemlistener.OnItemListener;
import com.example.ticketeventapp.model.mng_events.Event;
import com.example.ticketeventapp.model.mng_events.LocationGpsAgent;
import com.example.ticketeventapp.viewmodel.mng_events.EventListViewModel;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventItemAdapter extends RecyclerView.Adapter<EventViewHolder> implements Filterable {

    private Activity activity;
    private List<Event> eventsNotFilteredList;
    private List<Event> eventsFilteredList;
    private OnItemListener listener;
    private Filter eventFilter;
    private EventListViewModel eventListViewModel;


    public EventItemAdapter(Activity activity,List<Event> eventList, OnItemListener listener){
        this.eventsNotFilteredList = eventList;
        this.eventsFilteredList = eventList;
        this.activity = activity;
        this.listener = listener;
        this.createFilter();
        eventListViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(EventListViewModel.class);
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card_layout, parent, false );
        return new EventViewHolder(layoutView,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = eventsFilteredList.get(position);
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
        return this.eventsFilteredList.size();
    }

    public void setData(List<Event> eventList){
        final CardItemDiffCallback diffCallback = new CardItemDiffCallback(this.eventsFilteredList, eventList);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.eventsFilteredList  = new ArrayList<>(eventList);
        this.eventsNotFilteredList = new ArrayList<>(eventList);
        //check difference between lists and update only changes
        diffResult.dispatchUpdatesTo(this);
    }


    private void createFilter(){
        this.eventFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String filterType = charSequence.toString();
                List<Event> filteredList = new ArrayList<>();
                String temp="ciao";

                Log.e("HomeFragment",filterType);
                if (filterType.isEmpty() || filterType.equals("all")) { //all events
                    filteredList.addAll(eventsNotFilteredList);

                }
                else  if(filterType.equals("past")){ //past events
                    for(Event event : eventsNotFilteredList){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            LocalDate eventDate  = LocalDate.parse(event.getDate());
                            LocalDate today = LocalDate.now();;
                            if(eventDate.isBefore(today)){
                                filteredList.add(event);
                            }
                        } else{
                            //TODO
                        }
                    }
                }
                else if(filterType.equals("current")){
                    Log.e("HomeFragment","Sonoqui");
                    for(Event event : eventsNotFilteredList){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                            LocalDate eventDate  = LocalDate.parse(event.getDate());

                            LocalDate today = LocalDate.now();;
                            if(eventDate.isEqual(today)){
                                filteredList.add(event);
                            }
                        } else{
                            //TODO
                        }
                    }
                }
                else if(filterType.equals("next")){
                    for(Event event : eventsNotFilteredList){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                            LocalDate eventDate  = LocalDate.parse(event.getDate());

                            LocalDate today = LocalDate.now();;
                            if(eventDate.isAfter(today)){
                                filteredList.add(event);
                            }
                        } else{
                            //TODO
                        }
                    }
                }
                else if(filterType.equals("near")){ //near 30 km
                    Location userPosition = eventListViewModel.getLocationLiveData().getValue();
                    for(Event event : eventsNotFilteredList){
                        String latitude = event.getLatitude();
                        String longitude = event.getLongitude();
                        if(!latitude.isEmpty() && !longitude.isEmpty() && userPosition!=null){
                            Location location = new Location("");
                            location.setLatitude(Double.parseDouble(latitude));
                            location.setLongitude(Double.parseDouble(longitude));
                            Double distance = LocationGpsAgent.getDistanceKmBetweenLocations(userPosition,location);
                            if(distance <= 30){ //if distance is less than 30 kilometers
                                filteredList.add(event);
                            }
                        }
                        else{
                            Log.e("HomeFragment","Salto evento");
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                List<Event> filteredList = new ArrayList<>();
                List<?> result = (List<?>) filterResults.values;
                for (Object object : result) {
                    if (object instanceof Event) {
                        filteredList.add((Event) object);
                    }
                }

                //warn the adapter that the data are changed after the filtering
                Log.e("HomeFragment","size:"+filteredList.size());
                updateCardListItems(filteredList);
            }
        };
    }

    private  void updateCardListItems(List<Event> filteredList) {
        final CardItemDiffCallback diffCallback =
                new CardItemDiffCallback(this.eventsFilteredList, filteredList);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.eventsFilteredList = new ArrayList<>(filteredList);
        diffResult.dispatchUpdatesTo(this);
    }


    @Override
    public Filter getFilter() {
        return this.eventFilter;
    }

    public Event getItemSelected(int position) {
        return eventsFilteredList.get(position);
    }
}
