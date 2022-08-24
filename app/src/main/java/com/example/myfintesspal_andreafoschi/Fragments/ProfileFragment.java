package com.example.myfintesspal_andreafoschi.Fragments;

import static com.example.myfintesspal_andreafoschi.Utils.Utilities.TODAY;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.myfintesspal_andreafoschi.Activities.MainActivity;
import com.example.myfintesspal_andreafoschi.R;
import com.example.myfintesspal_andreafoschi.Utils.Utilities;
import com.example.myfintesspal_andreafoschi.ViewModel.AddViewModel;
import com.journeyapps.barcodescanner.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ProfileFragment extends Fragment {

    private int id;
    private String usr;

    private AddViewModel addViewModel;

    private EditText txt;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Activity activity = getActivity();
        if(activity != null){
            Utilities.setUpToolbar((AppCompatActivity) activity, getString(R.string.profile));
            TextView username = view.findViewById(R.id.username_info);

            SharedPreferences sharedPreferences = activity.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
            String pref_id = sharedPreferences.getString("id", "default");
            String pref_usr = sharedPreferences.getString("usr", "default");
            if (!pref_id.equals("default") && !pref_usr.equals("default")) {
                id = Integer.parseInt(pref_id);
                usr = pref_usr;
            }
            username.setText(usr);

            addViewModel = new ViewModelProvider((ViewModelStoreOwner) activity)
                    .get(AddViewModel.class);

            view.findViewById(R.id.update_weight).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    final EditText placeWeight = new EditText(getContext());
                    placeWeight.setHint(R.string.example);
                    placeWeight.setInputType(InputType.TYPE_CLASS_NUMBER);
                    builder.setTitle(R.string.message_weight);
                    builder.setView(placeWeight);
                    LinearLayout layoutName = new LinearLayout(getContext());
                    layoutName.setOrientation(LinearLayout.VERTICAL);
                    layoutName.addView(placeWeight);
                    builder.setView(layoutName);
                    builder.setPositiveButton(R.string.update, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            txt = placeWeight;
                            collectInputWeight();
                        }
                    });
                    builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.show();
                }
            });

            view.findViewById(R.id.update_password).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    final EditText placePsw = new EditText(getContext());
                    placePsw.setHint(R.string.psw_hint);
                    placePsw.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    builder.setTitle(R.string.insert_password);
                    builder.setView(placePsw);
                    LinearLayout layoutName = new LinearLayout(getContext());
                    layoutName.setOrientation(LinearLayout.VERTICAL);
                    layoutName.addView(placePsw);
                    builder.setView(layoutName);
                    builder.setPositiveButton(R.string.update, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            txt = placePsw;
                            collectInputPsw();
                        }
                    });
                    builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.show();
                }
            });

            view.findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setMessage(R.string.logout_msg)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    SharedPreferences sharedPreferences = activity.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
                                    sharedPreferences.edit().clear().apply();
                                    Utilities.setEmail(null);
                                    Utilities.setPsw(null);
                                    Intent intent = new Intent(getContext(), MainActivity.class);
                                    startActivity(intent);
                                    activity.finish();
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
                }
            });
        }
    }

    private void collectInputWeight() {
        String getInput = txt.getText().toString();
        if (getInput.trim().equals("")){
            Toast.makeText(getContext(), R.string.error_null, Toast.LENGTH_LONG).show();
        } else {
            addWeight(Integer.parseInt(getInput));
        }
    }

    private void addWeight(int weight) {
        addViewModel.updateWeight(weight, id, TODAY);
        Toast.makeText(getActivity(), R.string.weight_updated, Toast.LENGTH_SHORT).show();
    }

    private void collectInputPsw() {
        String getInput = txt.getText().toString();
        if (getInput.trim().equals("")){
            Toast.makeText(getContext(), R.string.error_psw1, Toast.LENGTH_LONG).show();
        } else if(getInput.length() < 10){
            Toast.makeText(getContext(), R.string.error_psw2, Toast.LENGTH_LONG).show();
        } else {
            updatePassword(getInput);
        }
    }

    private void updatePassword(String psw) {
        addViewModel.updatePassword(psw, id);
        Toast.makeText(getContext(), R.string.psw_updated, Toast.LENGTH_SHORT).show();
    }
}
