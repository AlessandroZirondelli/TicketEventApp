package com.example.ticketeventapp.ui.reglog.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.model.mng_users.RegUserManager;
import com.example.ticketeventapp.model.mng_users.User;
import com.example.ticketeventapp.viewmodel.mng_users.UsersViewModelRegLog;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RegistrationFragment extends Fragment {

    private TextInputEditText name;
    private TextInputEditText surname;
    private TextInputEditText username;
    private TextInputEditText password;
    private Button signup;

    private UsersViewModelRegLog usersViewModelRegLog;
    private RegUserManager regUserManager;

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
        signup = view.findViewById(R.id.add_event_button);

        usersViewModelRegLog = new ViewModelProvider(getActivity()).get(UsersViewModelRegLog.class);
        regUserManager = new RegUserManager(usersViewModelRegLog.getUsersLiveData().getValue());



        //It's mandatory to call this observe method
        usersViewModelRegLog.getUsersLiveData().observe(getActivity(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                regUserManager.setUsersList(users);
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //disableErrorFields();
                String name_string = String.valueOf(name.getText());
                String surname_string = String.valueOf(surname.getText());
                String username_string = String.valueOf(username.getText());
                String password_string = String.valueOf(password.getText());

                int resFilled = regUserManager.areFilledFields(name_string,surname_string,username_string,password_string);

                if(resFilled != 0){ // if fields are empty
                    enableEmptyFieldError(resFilled);
                }
                else { // We can continue with registration
                    if(regUserManager.isUsernameAvailable(username_string)){
                        User user = new User(name_string,surname_string,username_string,password_string,true);
                        usersViewModelRegLog.addUser(user);
                        Snackbar snackbar  =  Snackbar.make(getActivity().findViewById(R.id.fragment_container_view),
                                R.string.successful_registration,
                                Snackbar.ANIMATION_MODE_SLIDE);
                        snackbar.show();
                        clearRegistrationFields();
                        timerChangeFragment(getFragmentManager());

                    } else{
                        enableExistedUsernameError();

                    }
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

    private void enableExistedUsernameError(){
        username.setError(getString(R.string.used_username));
        setFocusOutListener(username);
    }

    /*
    private void disableExistedUsernameError(){
        username.setError(null);
    }*/

    private void enableEmptyFieldError(int field){
        Map<Integer,TextInputEditText> map = createMapIntegerErrorTextFields();
        TextInputEditText view = map.get(field);
        view.setError(getString(R.string.empty_field));
        setFocusOutListener(view);
    }

    /*private void disableErrorFields(){
        name.setError(null);
        surname.setError(null);
        username.setError(null);
        password.setError(null);
    }*/

    private void disableErrorField(TextInputEditText view){
        view.setError(null);
    }


    private  Map<Integer,TextInputEditText> createMapIntegerErrorTextFields(){
        Map<Integer,TextInputEditText> map = new HashMap<>();
        map.put(1, name);
        map.put(2, surname);
        map.put(3, username);
        map.put(4, password);
        return map;
    }

    private void setFocusOutListener(TextInputEditText viewT){
        viewT.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    Log.e("RegistrationFragment","Perdo focus");
                    disableErrorField(viewT);
                }
            }
        });
    }

    private void disableFocusListener(TextInputEditText view){
        view.setOnFocusChangeListener(null);
    }

    private void timerChangeFragment(FragmentManager fragmentManager){
        new CountDownTimer(3, 1000) {
            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {
                getFragmentManager().popBackStack();
            }
        }.start();
    }
}
