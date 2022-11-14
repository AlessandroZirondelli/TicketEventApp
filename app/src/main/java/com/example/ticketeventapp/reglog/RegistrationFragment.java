package com.example.ticketeventapp.reglog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.ticketeventapp.R;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

public class RegistrationFragment extends Fragment {

    private TextInputEditText name;
    private TextInputEditText surname;
    private TextInputEditText username;
    private TextInputEditText password;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name = view.findViewById(R.id.name_login_text_input_edit_text);
        surname = view.findViewById(R.id.surname_login_text_input_edit_text);
        username = view.findViewById(R.id.username_login_text_input_edit_text);
        password = view.findViewById(R.id.password_login_text_input_edit_text);
    }
}
