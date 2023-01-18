package com.example.ticketeventapp.ui.utilities;



import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.ticketeventapp.ui.main.fragment.home.fragment.BottomBarEventsAdminFragment;
import com.example.ticketeventapp.ui.main.fragment.home.fragment.BottomBarEventsUserFragment;
import com.example.ticketeventapp.ui.main.fragment.home.fragment.HomeFragment;
import com.example.ticketeventapp.ui.main.fragment.home.fragment.RecyclerViewEventsFragment;
import com.example.ticketeventapp.ui.reglog.fragment.LoginFragment;


public class Utilities {

    public static void replaceFragmentOnContainer(AppCompatActivity activity, Fragment fragment, String tag, int fragmentContainerId){
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(fragmentContainerId,fragment, tag);
        if(!(fragment instanceof LoginFragment)
                && !(fragment instanceof HomeFragment)
                && !(fragment instanceof BottomBarEventsAdminFragment)
                && !(fragment instanceof BottomBarEventsUserFragment)
                && !(fragment instanceof RecyclerViewEventsFragment)) {
            transaction.addToBackStack(tag);
        }
        transaction.commit();
    }

    public static void replaceNestedFragmentOnHomeFragment(AppCompatActivity activity, Fragment hostFragment, Fragment childFragment, String tag, int fragmentContainerId){
        FragmentTransaction transaction = hostFragment.getChildFragmentManager().beginTransaction();
        transaction.replace(fragmentContainerId,childFragment, tag);
        transaction.addToBackStack(tag);
        transaction.commit();
    }

}
