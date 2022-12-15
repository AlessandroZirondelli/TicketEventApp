package com.example.ticketeventapp.ui.main.home.fragment;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.model.home.recyclerview.EventItemAdapter;
import com.example.ticketeventapp.model.home.recyclerview.EventsRecyclerView;
import com.example.ticketeventapp.model.home.recyclerview.onitemlistener.OnItemListener;
import com.example.ticketeventapp.model.mng_events.Event;
import com.example.ticketeventapp.model.mng_events.LocationGpsAgent;
import com.example.ticketeventapp.model.utils.AppInfo;
import com.example.ticketeventapp.model.utils.PermissionManager;
import com.example.ticketeventapp.ui.main.mngevents.components.EnablerDialog;
import com.example.ticketeventapp.ui.main.mngevents.fragment.ActionSelectFragment;
import com.example.ticketeventapp.ui.main.mngevents.fragment.InfoEventFragment;
import com.example.ticketeventapp.ui.utilities.Utilities;
import com.example.ticketeventapp.viewmodel.mng_events.EventListViewModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;


public class RecyclerViewEventsFragment extends Fragment implements OnItemListener {

    private EventsRecyclerView eventsRecyclerView;
    private EventListViewModel eventListViewModel;
    private Activity activity;
    private EventItemAdapter eventItemAdapter;
    private ChipGroup chipGroup;
    private Chip chipCurrent;
    private Chip chipNext;
    private Chip chipPast;
    private Chip chipNear;
    private PermissionManager permissionManager;
    private LocationGpsAgent locationGpsAgent;
    private EnablerDialog enablerDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recycler_view_events_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = getActivity();
        if(activity==null){
            Log.e("HomeFragment","activity null in recyclervieeweventsfragemnt");
        }

        permissionManager = new PermissionManager(activity,this);
        locationGpsAgent = new LocationGpsAgent(activity,permissionManager);
        enablerDialog = new EnablerDialog(activity);
        eventsRecyclerView = new EventsRecyclerView(getActivity());
        eventsRecyclerView.setRecyclerView(this);

        eventListViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(EventListViewModel.class);
        eventListViewModel.clearSelectedItem();

        eventItemAdapter = eventsRecyclerView.getEventItemAdapter();

        eventListViewModel.getEventsLiveData().observe((LifecycleOwner) activity, new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> eventList) {
                Log.e("HomeFragment","Chiamato");
                //eventItemAdapter.notifyDataSetChanged();
                eventItemAdapter.setData(eventList);

            }
        });

        chipGroup = view.findViewById(R.id.chip_group_filter);
        chipGroup.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
                //Multiple checks with Near+Current or Near+Future or Near+
                if(checkedIds.isEmpty()){
                    Log.e("HomeFragment","Vuotooo");
                    eventItemAdapter.getFilter().filter("all");
                } else {
                    int countSelectedItems = checkedIds.size();
                    if(countSelectedItems == 2){
                        if(chipNear.isChecked() && chipNext.isChecked()){
                            eventItemAdapter.getFilter().filter("next-near");
                        }
                        if(chipNear.isChecked() && chipPast.isChecked()){
                            eventItemAdapter.getFilter().filter("past-near");
                        }
                        if(chipNear.isChecked() && chipCurrent.isChecked()){
                            eventItemAdapter.getFilter().filter("current-near");
                        }
                    }
                }
            }
        });

        chipCurrent = view.findViewById(R.id.current_events_chip);
        chipNext = view.findViewById(R.id.next_events_chip);
        chipPast = view.findViewById(R.id.past_events_chip);
        chipNear = view.findViewById(R.id.near_events_chip);


        chipCurrent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    //Log.e("HomeFragment","Checked");
                    if(chipGroup.getCheckedChipIds().size()==1){
                        eventItemAdapter.getFilter().filter("current");
                    }
                    chipPast.setCheckable(false);
                    chipNext.setCheckable(false);
                } else{
                    //Log.e("HomeFragment","Not checked");
                    chipPast.setCheckable(true);
                    chipNext.setCheckable(true);
                    if(chipNear.isChecked()){
                        //enablerDialog.showInfoAcquisitionPosition();
                        locationGpsAgent.startLocationUpdates();
                    }
                }
            }
        });

        chipNext.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    //Log.e("HomeFragment","Checked");
                    if(chipGroup.getCheckedChipIds().size()==1){
                        eventItemAdapter.getFilter().filter("next");
                    }
                    chipPast.setCheckable(false);
                    chipCurrent.setCheckable(false);
                } else{
                    //Log.e("HomeFragment","Not checked");
                    chipPast.setCheckable(true);
                    chipCurrent.setCheckable(true);
                    if(chipNear.isChecked()){
                        //enablerDialog.showInfoAcquisitionPosition();
                        locationGpsAgent.startLocationUpdates();
                    }
                }
            }
        });

        chipPast.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    if(chipGroup.getCheckedChipIds().size()==1){
                        Log.e("Filter","Sono l'unico");
                        eventItemAdapter.getFilter().filter("past");
                    }
                    chipNext.setCheckable(false);
                    chipCurrent.setCheckable(false);
                } else{
                    chipNext.setCheckable(true);
                    chipCurrent.setCheckable(true);
                    if(chipNear.isChecked()){
                        //enablerDialog.showInfoAcquisitionPosition();
                        locationGpsAgent.startLocationUpdates();
                    }
                }
            }
        });

        chipNear.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    if(permissionManager.isPermissionGPSAllowed()){
                        if(locationGpsAgent.isTurnedOnGPS()){
                            if(chipGroup.getCheckedChipIds().size()==1){
                                enablerDialog.showInfoAcquisitionPosition();
                                locationGpsAgent.startLocationUpdates();
                            }
                        } else {
                            enablerDialog.askTurnOnGPS();
                        }
                    }else {
                        permissionManager.launchPermissionRequestGPS();
                    }
                } else {
                    if(chipNext.isChecked()){
                        eventItemAdapter.getFilter().filter("next");
                    }
                    if(chipCurrent.isChecked()){
                        eventItemAdapter.getFilter().filter("current");
                    }
                    if(chipPast.isChecked()){
                        eventItemAdapter.getFilter().filter("past");
                    }
                }
            }
        });

        eventListViewModel.getLocationLiveData().observe(getActivity(), new Observer<Location>() {
            @Override
            public void onChanged(Location location) {
                //Continue to filter list
                Log.e("HomeFragment","Location restituita"+location.toString());
                eventItemAdapter.getFilter().filter("near");
            }
        });

        eventListViewModel.getSelectedEventItem().observe(getActivity(), new Observer<Event>() {
            @Override
            public void onChanged(Event event) {
                 if(event!=null){
                     if(AppInfo.getInstance().getLoggedUser().isUser()){
                         Utilities.addFragmentOn((AppCompatActivity) activity,new InfoEventFragment(),InfoEventFragment.class.getSimpleName());
                     } else {
                         Utilities.addFragmentOn((AppCompatActivity) activity,new ActionSelectFragment(),ActionSelectFragment.class.getSimpleName());
                     }
                 }
            }
        });





    }


    @Override
    public void onItemClick(int position) {
        Event clickedEvent = eventItemAdapter.getItemSelected(position);
        eventListViewModel.setSelectedEventItem(clickedEvent);
    }
}
