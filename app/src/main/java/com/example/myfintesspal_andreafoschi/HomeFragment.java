package com.example.myfintesspal_andreafoschi;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class HomeFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentActivity activity = getActivity();
        if(activity != null) {
            Button loginButton = view.findViewById(R.id.login_button);
            Button registerButton = view.findViewById(R.id.register_button);

            loginButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Utilities.insertFragment((AppCompatActivity) activity, new LoginFragment(), LoginFragment.class.getSimpleName());
                }
            });

            registerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utilities.insertFragment((AppCompatActivity) activity, new RegistrationFragment(), RegistrationFragment.class.getSimpleName());
                }
            });

        } else {
            Log.e("HomeFragment", "Activity is null");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getActivity() != null)
            ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        if(getActivity() != null)
            ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
}
