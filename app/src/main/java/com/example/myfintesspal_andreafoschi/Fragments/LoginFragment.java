package com.example.myfintesspal_andreafoschi.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.myfintesspal_andreafoschi.Activities.DashboardActivity;
import com.example.myfintesspal_andreafoschi.Tables.ProfileInformation;
import com.example.myfintesspal_andreafoschi.R;
import com.example.myfintesspal_andreafoschi.Utils.Utilities;
import com.example.myfintesspal_andreafoschi.ViewModel.ListViewModel;


public class LoginFragment extends Fragment {

    private ListViewModel viewModel;

    private EditText email;
    private EditText psw;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentActivity activity = getActivity();
        if(activity != null){
            Utilities.setUpToolbar((AppCompatActivity) activity, getString(R.string.log_in));

            email = view.findViewById(R.id.email_placetext);
            psw = view.findViewById(R.id.psw_placetext);

            view.findViewById(R.id.try_login_button).setOnClickListener(view1 -> {
                viewModel = new ViewModelProvider(activity).get(ListViewModel.class);
                viewModel.getProfilesInfo().observe(activity, profileInformation -> {
                    try{
                        if(email.getText().length() != 0 && psw.getText().length() != 0){
                            boolean found = false;
                            for (ProfileInformation profile: profileInformation) {
                                if(profile != null &&
                                        profile.getEmail().equals(email.getText().toString()) &&
                                        profile.getPassword().equals(psw.getText().toString())) {
                                    Toast.makeText(activity, R.string.login_success, Toast.LENGTH_SHORT).show();
                                    SharedPreferences sharedPreferences = activity.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("email", profile.getEmail());
                                    editor.putString("id", String.valueOf(profile.getId()));
                                    editor.putString("goal", String.valueOf(profile.getDayGoal()));
                                    editor.putString("weight", String.valueOf(profile.getWeight()));
                                    editor.putString("usr", profile.getUsername());
                                    editor.apply();
                                    found = true;
                                    Intent intent = new Intent(getContext(), DashboardActivity.class);
                                    this.startActivity(intent);
                                    activity.finish();
                                    break;
                                }
                            }
                            if(!found){
                                Toast.makeText(activity, R.string.login_fail, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(activity, R.string.insert_email_psw, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            });
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.top_bar_app, menu);
    }
}
