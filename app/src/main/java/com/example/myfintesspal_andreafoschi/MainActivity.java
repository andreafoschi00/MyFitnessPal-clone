package com.example.myfintesspal_andreafoschi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null)
            Utilities.insertMainActivityFragment(this, new HomeFragment(), HomeFragment.class.getSimpleName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_bar_app, menu);
        return true;
    }
}