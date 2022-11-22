package com.example.ticketeventapp.ui.reglog.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.model.mng_users.LogUserManager;
import com.example.ticketeventapp.model.mng_users.RegUserManager;
import com.example.ticketeventapp.model.mng_users.User;
import com.example.ticketeventapp.viewmodel.mng_users.UsersViewModelRegLog;
import com.example.ticketeventapp.ui.utilities.Utilities;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginFragment extends Fragment {

    private UsersViewModelRegLog usersViewModelRegLog;
    private LogUserManager logUserManager;

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


        usersViewModelRegLog = new ViewModelProvider(getActivity()).get(UsersViewModelRegLog.class);
        logUserManager = new LogUserManager(usersViewModelRegLog.getUsersLiveData().getValue(),getActivity());

        usersViewModelRegLog.getUsersLiveData().observe(getActivity(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                logUserManager.setUsersList(users);
            }
        });



        registerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.insertFragment((AppCompatActivity) activity,new RegistrationFragment(), RegistrationFragment.class.getSimpleName());
            }
        });

        loginIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*String username_string = String.valueOf(username.getText());
                String password_string = String.valueOf(password.getText());

                int resFilled = logUserManager.areFilledFields(username_string,password_string);
                if(resFilled != 0){
                    enableEmptyFieldError(resFilled);
                } else {
                    int resCredentials = logUserManager.areCorrectCredentials(username_string,password_string);
                    if(resCredentials != 0){
                        enableCredentialsError(resCredentials);
                        Log.e("LoginFragment","Login non corretto");
                    } else{ //correct credentials
                        //redirect to activity with home
                        Log.e("LoginFragment","Login corretto");
                        logUserManager.startLoginSession(username_string,password_string);
                    }
                }*/

                Log.e("LoginFragment","username:"+logUserManager.getLoggedUser().getUsername()+" password:"+logUserManager.getLoggedUser().getPassword());


            }
        });

    }



    private void enableEmptyFieldError(int field){
        Map<Integer,TextInputEditText> map = createMapIntegerErrorTextFields();
        TextInputEditText view = map.get(field);
        view.setError(getString(R.string.empty_field));
        setFocusOutListener(view);
    }

    private void enableCredentialsError(int field){
        Map<Integer,TextInputEditText> map = createMapIntegerErrorTextFields();
        TextInputEditText view = map.get(field);
        if(field == 1){
            view.setError(getString(R.string.username_not_exists));
        }
        if(field == 2){
            view.setError(getString(R.string.wrong_password));
        }
        setFocusOutListener(view);
    }

    private  Map<Integer,TextInputEditText> createMapIntegerErrorTextFields(){
        Map<Integer,TextInputEditText> map = new HashMap<>();
        map.put(1, username);
        map.put(2, password);
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

    private void disableErrorField(TextInputEditText view){
        view.setError(null);
    }

}
