package com.example.myfintesspal_andreafoschi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "default");

        if(!email.equals("default")) {
            Intent intent = new Intent(this, DashboardActivity.class);
            this.startActivity(intent);
            this.finish();
        } else if(savedInstanceState == null)
            Utilities.insertMainActivityFragment(this, new HomeFragment(), HomeFragment.class.getSimpleName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_bar_app, menu);
        return true;
    }
}