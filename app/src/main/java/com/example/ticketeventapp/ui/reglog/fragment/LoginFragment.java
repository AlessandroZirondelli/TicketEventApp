package com.example.ticketeventapp.ui.reglog.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.viewmodel.mng_users.UsersViewModelRegLog;
import com.example.ticketeventapp.ui.utilities.Utilities;
import com.google.android.material.textfield.TextInputEditText;

public class LoginFragment extends Fragment {

    private UsersViewModelRegLog usersViewModelRegLog;

    private TextInputEditText username;
    private TextInputEditText password;
    private ImageView loginIcon;
    private ImageView registerIcon;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Activity activity = getActivity();

        username = view.findViewById(R.id.username_login_text_input_edit_text);
        password = view.findViewById(R.id.password_login_text_input_edit_text);
        registerIcon = view.findViewById(R.id.add_account_icon_image_view);
        loginIcon = view.findViewById(R.id.login_icon_image_view);

        //username.setFocusable(false); Serve per  rendere il textinpt non focusable e quindi non modificabile

        //usersViewModelRegLog = new ViewModelProvider(getActivity()).get(UsersViewModelRegLog.class);


        registerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.insertFragment((AppCompatActivity) activity,new RegistrationFragment(), RegistrationFragment.class.getSimpleName());


            }
        });

    }

}
