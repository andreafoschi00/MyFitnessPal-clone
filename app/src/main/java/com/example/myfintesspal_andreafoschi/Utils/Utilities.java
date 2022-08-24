package com.example.myfintesspal_andreafoschi.Utils;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myfintesspal_andreafoschi.Fragments.DashboardFragment;
import com.example.myfintesspal_andreafoschi.Fragments.HomeFragment;
import com.example.myfintesspal_andreafoschi.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utilities {

    public static final String TODAY = new SimpleDateFormat("dd-MM-yyyy", Locale.ITALY).format(new Date());

    private static String email = null;
    private static String password = null;

    public static void insertMainActivityFragment(AppCompatActivity activity, Fragment fragment, String tag){
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container_view, fragment, tag);

        if(!(fragment instanceof HomeFragment)){
            transaction.addToBackStack(tag);
        }

        transaction.commit();
    }

    public static void insertDashboardActivityFragment(AppCompatActivity activity, Fragment fragment, String tag) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.dashboard_container_view, fragment, tag);

        if(!(fragment instanceof DashboardFragment)){
            transaction.addToBackStack(tag);
        }

        transaction.commit();
    }

    public static void setUpToolbar(AppCompatActivity activity, String title){
        ActionBar actionBar = activity.getSupportActionBar();
        if(actionBar == null) {
            Toolbar toolbar = new Toolbar(activity);
            activity.setSupportActionBar(toolbar);
        } else {
            activity.getSupportActionBar().setTitle(title);
        }
    }

    public static void setEmail(String mail) {
        email = mail;
    }

    public static void setPsw(String psw) {
        password = psw;
    }

    public static String getEmail() {
        return email;
    }

    public static String getPassword() {
        return password;
    }
}
