package com.example.ticketeventapp.ui.utilities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.ticketeventapp.R;
import com.example.ticketeventapp.ui.reglog.fragment.LoginFragment;


public class Utilities {

    public static void insertFragment(AppCompatActivity activity, Fragment fragment, String tag){
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_view,fragment, tag);

        if(!(fragment instanceof LoginFragment)) {
            transaction.addToBackStack(tag);
        }
        transaction.commit();

    }

    public static void insertHomeFragment(AppCompatActivity activity, Fragment fragment, String tag, int fragmentContainerResId){
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(fragmentContainerResId,fragment, tag);
        transaction.addToBackStack(tag);
        transaction.commit();
    }
}
