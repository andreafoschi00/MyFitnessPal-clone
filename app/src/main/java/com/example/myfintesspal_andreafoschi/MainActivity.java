package com.example.myfintesspal_andreafoschi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null)
            Utilities.insertFragment(this, new HomeFragment(), HomeFragment.class.getSimpleName());
    }
}