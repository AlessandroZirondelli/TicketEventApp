package com.example.ticketeventapp.ui.mngevents.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.ui.mngevents.components.DatePicker;
import com.example.ticketeventapp.viewmodel.mng_events.AddEventViewModel;
import com.example.ticketeventapp.viewmodel.mng_users.UsersViewModelRegLog;
import com.google.android.material.textfield.TextInputEditText;

public class AddEventFragment extends Fragment {

    private AddEventViewModel addEventViewModel;

    private TextView fragment_title;
    private ImageView event_photo;
    private TextInputEditText event_name;
    private TextInputEditText event_description;
    private TextInputEditText event_place;
    private TextInputEditText event_date;
    private TextInputEditText event_time;
    private TextInputEditText event_price;
    private Button button;

    private DatePicker datePicker;





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragment_title = view.findViewById(R.id.info_add_event);
        event_photo = view.findViewById(R.id.event_icon_image_view);
        event_name = view.findViewById(R.id.event_name_text_input_edit_text);
        event_description = view.findViewById(R.id.event_description_text_input_edit_text);
        event_place = view.findViewById(R.id.event_place_text_input_edit_text);
        event_date = view.findViewById(R.id.event_date_text_input_edit_text);
        event_time = view.findViewById(R.id.event_time_text_input_edit_text);
        event_price = view.findViewById(R.id.event_price_text_input_edit_text);
        button = view.findViewById(R.id.add_event_button);

        addEventViewModel = new ViewModelProvider(getActivity()).get(AddEventViewModel.class);

        datePicker = new DatePicker(getActivity().getSupportFragmentManager(),addEventViewModel);

        event_date.setFocusable(false);



        addEventViewModel.getSelectedDate().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String date) {
                event_date.setText(date);
            }
        });

        event_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.show();
            }
        });

        event_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("AddEventFragment",addEventViewModel.getSelectedDate().getValue());
            }
        });

    }

}
