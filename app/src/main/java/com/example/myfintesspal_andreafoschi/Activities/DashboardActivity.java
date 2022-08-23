package com.example.myfintesspal_andreafoschi.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myfintesspal_andreafoschi.Fragments.DashboardFragment;
import com.example.myfintesspal_andreafoschi.Fragments.DiaryFragment;
import com.example.myfintesspal_andreafoschi.Fragments.ProfileFragment;
import com.example.myfintesspal_andreafoschi.Fragments.ShoppingListFragment;
import com.example.myfintesspal_andreafoschi.R;
import com.example.myfintesspal_andreafoschi.Utils.Utilities;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class DashboardActivity extends AppCompatActivity {

    BottomNavigationView navigationView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        if(savedInstanceState == null) {
            Utilities.insertDashboardActivityFragment(this, new DashboardFragment(),
                    DashboardFragment.class.getSimpleName());
        }
        AppCompatActivity activity = this;
        navigationView = findViewById(R.id.bottom_bar);
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.page_dashboard:
                        Utilities.insertDashboardActivityFragment(activity, new DashboardFragment(), DashboardFragment.class.getSimpleName());
                        return true;
                    case R.id.page_diary:
                        Utilities.insertDashboardActivityFragment(activity, new DiaryFragment(), DiaryFragment.class.getSimpleName());
                        return true;
                    case R.id.page_list:
                        Utilities.insertDashboardActivityFragment(activity, new ShoppingListFragment(), ShoppingListFragment.class.getSimpleName());
                        return true;
                    case R.id.page_profile:
                        Utilities.insertDashboardActivityFragment(activity, new ProfileFragment(), ProfileFragment.class.getSimpleName());
                        return true;
                }
            return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_bar_app, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(navigationView.getSelectedItemId() == R.id.page_dashboard) {
            finish();
        }
        navigationView.setSelectedItemId(R.id.page_dashboard);
    }
}
