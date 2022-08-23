package com.example.myfintesspal_andreafoschi.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.myfintesspal_andreafoschi.Tables.ProfileInformation;
import com.example.myfintesspal_andreafoschi.R;
import com.example.myfintesspal_andreafoschi.Utils.Utilities;
import com.example.myfintesspal_andreafoschi.ViewModel.AddViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Pattern;

public class RegistrationFragment extends Fragment {

    private RadioGroup genderGroup;
    private EditText birth_date;
    private EditText height;
    private EditText weight;
    private EditText username;
    private EditText email;
    private EditText password;

    final Calendar calendar = Calendar.getInstance();

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

            birth_date = getActivity().findViewById(R.id.birth_edittext);
            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, day);
                    updateLabel();
                }
            };
            birth_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new DatePickerDialog(getActivity(), date, calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });

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

                            int goal = ProfileInformation.calculateGoal(gender,
                                    birth_date.getText().toString(),
                                    Integer.parseInt(height.getText().toString()),
                                    Integer.parseInt(weight.getText().toString()));

                            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                            builder.setMessage(getString(R.string.create_confirm1) + " " + goal +
                                    " " + getString(R.string.create_confirm2))
                                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Utilities.insertMainActivityFragment((AppCompatActivity) activity,
                                                    new LoginFragment(),
                                                    LoginFragment.class.getSimpleName());
                                        }
                                    })
                                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                        }
                                    })
                                    .create()
                                    .show();

                            ((AppCompatActivity) activity).getSupportFragmentManager().popBackStack();
                        } else {
                            Toast.makeText(activity, R.string.signup_error,
                                    Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.top_bar_app, menu);
    }

    private boolean patternMatches(Editable text) {
        return text.length() > 0 && Pattern.compile("^(.+)@(\\S+)$")
                .matcher(text)
                .matches();
    }

    private void updateLabel() {
        String format = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.ITALY);
        birth_date.setText(dateFormat.format(calendar.getTime()));
    }
}
