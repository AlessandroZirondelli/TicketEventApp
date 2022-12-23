package com.example.ticketeventapp.ui.main.fragment.home.fragment;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.model.home.recyclerview.EventsRecyclerView;
import com.example.ticketeventapp.model.home.recyclerview.adapter.EventItemAdapter;
import com.example.ticketeventapp.model.home.recyclerview.onitemlistener.OnItemListener;
import com.example.ticketeventapp.model.mng_events.Event;
import com.example.ticketeventapp.model.mng_events.LocationGpsAgent;
import com.example.ticketeventapp.model.utils.AppInfo;
import com.example.ticketeventapp.model.utils.PermissionManager;
import com.example.ticketeventapp.ui.main.fragment.mngevents.components.EnablerDialog;
import com.example.ticketeventapp.ui.main.fragment.mngevents.fragment.ActionSelectFragment;
import com.example.ticketeventapp.ui.main.fragment.mngevents.fragment.InfoEventFragment;
import com.example.ticketeventapp.ui.utilities.Utilities;
import com.example.ticketeventapp.viewmodel.mng_events.EventListViewModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;


public class RecyclerViewJoinersFragment extends Fragment implements OnItemListener {


    @Override
    public void onItemClick(int position) {

    }
}
