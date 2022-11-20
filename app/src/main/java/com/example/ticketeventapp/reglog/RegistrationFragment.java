package com.example.ticketeventapp.reglog;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.mng_users.User;
import com.example.ticketeventapp.mng_users.UsersViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class RegistrationFragment extends Fragment {

    private TextInputEditText name;
    private TextInputEditText surname;
    private TextInputEditText username;
    private TextInputEditText password;
    private Button signup;

    private UsersViewModel usersViewModel;

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
        signup = view.findViewById(R.id.sign_up_button);
        usersViewModel = new ViewModelProvider(getActivity()).get(UsersViewModel.class);
        //usersModel = new UsersModel();

        /* Se commento l'observe, NON funziona ! */
       usersViewModel.getUsers().observe(getActivity(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                Log.e("RegistrationFragment","Livedata cambia");
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name_string = String.valueOf(name.getText());
                String surname_string = String.valueOf(surname.getText());
                String username_string = String.valueOf(username.getText());
                String password_string = String.valueOf(password.getText());


                Log.e("RegistrationFragment",String.valueOf(usersViewModel.getUsers().getValue().size()));


                if(name_string != null && surname_string != null && username_string != null && password_string != null){

                    usersViewModel.addUser(new User(name_string,surname_string,username_string,password_string,true));
                    //getActivity().getSupportFragmentManager().popBackStack();
                    Snackbar snackbar  =  Snackbar.make(getActivity().findViewById(R.id.fragment_container_view),
                            R.string.successful_registration,
                            Snackbar.ANIMATION_MODE_SLIDE);
                    snackbar.show();
                    clearRegistrationFields();

                }

            }
        });


    }

    private void clearRegistrationFields(){
        name.setText("");
        surname.setText("");
        username.setText("");
        password.setText("");
        name.clearFocus();
        surname.clearFocus();
        username.clearFocus();
        password.clearFocus();
    }
}
