package com.example.ticketeventapp.ui.utilities;


import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.ticketeventapp.R;
import com.example.ticketeventapp.ui.main.fragment.home.fragment.HomeFragment;
import com.example.ticketeventapp.ui.reglog.fragment.LoginFragment;


public class Utilities {

    public static void insertFragment(AppCompatActivity activity, Fragment fragment, String tag){
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_view,fragment, tag);

        if(!(fragment instanceof LoginFragment) && !(fragment instanceof HomeFragment)) {
            transaction.addToBackStack(tag);
        }
        Log.e("BackStack","Aggiungo fragment:"+fragment.getClass().getSimpleName()+"by InsertFragment");
        transaction.commit();

    }

    public static void replaceFragmentInHomeFragment(AppCompatActivity activity, Fragment fragment, String tag, int fragmentContainerResId, Fragment hostFragment ){
        //host fragment will bve HomeFragment.
        //getChildFragmentManager return fragment manager of hostFragment. So it manages transaction of child fragments
        //It works also with getSupportFragmentManager
        //FragmentTransaction transaction = hostFragment.getChildFragmentManager().beginTransaction();;
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(fragmentContainerResId,fragment, tag);
        //transaction.addToBackStack(tag);
        transaction.commit();
        Log.e("BackStack","Aggiungo fragment:"+fragment.getClass().getSimpleName()+"by InsertHomeragment");
    }

    public static void addFragmentOn(AppCompatActivity activity, Fragment fragment, String tag){
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container_view,fragment, tag);

        transaction.addToBackStack(tag);

        Log.e("BackStack","Aggiungo fragment:"+fragment.getClass().getSimpleName()+"by insertInfoDetailFragment");
        transaction.commit();

    }

}
