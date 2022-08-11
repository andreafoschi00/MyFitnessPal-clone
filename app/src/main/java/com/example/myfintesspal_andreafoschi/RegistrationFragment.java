package com.example.myfintesspal_andreafoschi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.myfintesspal_andreafoschi.ViewModel.AddViewModel;

import java.text.ParseException;
import java.util.regex.Pattern;

public class RegistrationFragment extends Fragment {

    private RadioGroup genderGroup;
    private EditText birth_date;
    private EditText height;
    private EditText weight;
    private EditText username;
    private EditText email;
    private EditText password;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.registration, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Activity activity = getActivity();
        if(activity != null){
            Utilities.setUpToolbar((AppCompatActivity) activity, getString(R.string.register));

            AddViewModel addViewModel = new ViewModelProvider((ViewModelStoreOwner) activity)
                    .get(AddViewModel.class);

            genderGroup = (RadioGroup) view.findViewById(R.id.gender_group);
            birth_date = view.findViewById(R.id.birth_edittext);
            height = view.findViewById(R.id.height_edittext);
            weight = view.findViewById(R.id.weight_edittext);
            username = view.findViewById(R.id.username_edittext);
            email = view.findViewById(R.id.email_edittext);
            password = view.findViewById(R.id.psw_edittext);
            view.findViewById(R.id.signin_button).setOnClickListener(new View.OnClickListener() {

                @SuppressLint("NonConstantResourceId")
                @Override
                public void onClick(View view) {
                    try {
                        int selectedId = genderGroup.getCheckedRadioButtonId();
                        String gender = "";
                        switch (selectedId){
                            case R.id.gender_male:
                                gender = "Male";
                            break;
                            case R.id.gender_female:
                                gender = "Female";
                            break;
                        }
                        if(birth_date.getText() != null && height.getText() != null &&
                                weight.getText() != null && username.getText() != null &&
                                email.getText() != null && password.getText() != null &&
                                patternMatches(email.getText()) && password.getText().length() >= 8) {
                            addViewModel.addProfile(new ProfileInformation(
                                    gender,
                                    birth_date.getText().toString(),
                                    Integer.parseInt(height.getText().toString()),
                                    Integer.parseInt(weight.getText().toString()),
                                    username.getText().toString(), email.getText().toString(),
                                    password.getText().toString()));

                            ((AppCompatActivity) activity).getSupportFragmentManager().popBackStack();
                        } else {
                            Toast.makeText(activity, "Error on params.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private boolean patternMatches(Editable text) {
        return Pattern.compile("^(.+)@(\\S+)$")
                .matcher(text)
                .matches();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.top_bar_app, menu);
    }
}
