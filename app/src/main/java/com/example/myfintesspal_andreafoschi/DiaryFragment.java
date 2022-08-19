package com.example.myfintesspal_andreafoschi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.myfintesspal_andreafoschi.ViewModel.AddViewModel;
import com.example.myfintesspal_andreafoschi.ViewModel.ListViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DiaryFragment extends Fragment{

    private static final String TODAY = new SimpleDateFormat("dd-MM-yyyy", Locale.ITALY).format(new Date());

    private AddViewModel addViewModel;
    private ListViewModel viewModel;

    private int id;
    private int goal;

    private int caloriesLeft;

    private TextView breakfast;
    private TextView lunch;
    private TextView snack;
    private TextView dinner;
    private TextView training;
    private TextView left;

    EditText txt;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.diary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentActivity activity = getActivity();
        if(activity != null){
            breakfast = view.findViewById(R.id.breakfast_cal);
            lunch = view.findViewById(R.id.lunch_cal);
            snack = view.findViewById(R.id.snack_cal);
            dinner = view.findViewById(R.id.dinner_cal);
            training = view.findViewById(R.id.training_cal);
            left = view.findViewById(R.id.goal_cal);
            Utilities.setUpToolbar((AppCompatActivity) activity, getString(R.string.diary));
            SharedPreferences sharedPreferences = activity.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
            String pref_id = sharedPreferences.getString("id", "default");
            String pref_goal = sharedPreferences.getString("goal", "default");
            if(!pref_id.equals("default") && !pref_goal.equals("default")){
                id = Integer.parseInt(pref_id);
                goal = Integer.parseInt(pref_goal);
            }
            addViewModel = new ViewModelProvider((ViewModelStoreOwner) activity)
                    .get(AddViewModel.class);
            viewModel = new ViewModelProvider(activity).get(ListViewModel.class);
            viewModel.getCaloriesInfo().observe(activity, calories -> {
                try{
                    boolean found = false;
                    for(ProfileDailyCalories cal : calories){
                        if(cal.getId() == id && cal.getDate().equals(TODAY)){
                            found = true;
                            updateView(cal.getBreakfast_cal(), cal.getLunch_cal(), cal.getSnack_cal(),
                                    cal.getDinner_cal(), cal.getTraining_cal(), cal.getDaily_cal_left());
                            break;
                        }
                    }
                    if(!found){
                        addViewModel.addCalories(new ProfileDailyCalories(TODAY, goal, id));
                        updateView(0, 0, 0, 0, 0, goal);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        view.findViewById(R.id.add_breakfast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage(R.string.add_message)
                        .setPositiveButton(R.string.enter_manually, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext());
                                final EditText placeCalories = new EditText(getContext());
                                placeCalories.setHint(R.string.example);
                                placeCalories.setInputType(InputType.TYPE_CLASS_NUMBER);
                                builder2.setTitle(R.string.insert_cal);
                                builder2.setView(placeCalories);
                                LinearLayout layoutName = new LinearLayout(getContext());
                                layoutName.setOrientation(LinearLayout.VERTICAL);
                                layoutName.addView(placeCalories);
                                builder2.setView(layoutName);
                                builder2.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        txt = placeCalories;
                                        collectInputBreakfast();
                                    }
                                });
                                builder2.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                                builder2.show();
                            }
                        })
                        .setNegativeButton(R.string.use_camera, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .create()
                        .show();
            }
        });

        view.findViewById(R.id.add_lunch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage(R.string.add_message)
                        .setPositiveButton(R.string.enter_manually, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext());
                                final EditText placeCalories = new EditText(getContext());
                                placeCalories.setHint(R.string.example);
                                placeCalories.setInputType(InputType.TYPE_CLASS_NUMBER);
                                builder2.setTitle(R.string.insert_cal);
                                builder2.setView(placeCalories);
                                LinearLayout layoutName = new LinearLayout(getContext());
                                layoutName.setOrientation(LinearLayout.VERTICAL);
                                layoutName.addView(placeCalories);
                                builder2.setView(layoutName);
                                builder2.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        txt = placeCalories;
                                        collectInputLunch();
                                    }
                                });
                                builder2.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                                builder2.show();
                            }
                        })
                        .setNegativeButton(R.string.use_camera, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .create()
                        .show();
            }
        });

        view.findViewById(R.id.add_snack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage(R.string.add_message)
                        .setPositiveButton(R.string.enter_manually, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext());
                                final EditText placeCalories = new EditText(getContext());
                                placeCalories.setHint(R.string.example);
                                placeCalories.setInputType(InputType.TYPE_CLASS_NUMBER);
                                builder2.setTitle(R.string.insert_cal);
                                builder2.setView(placeCalories);
                                LinearLayout layoutName = new LinearLayout(getContext());
                                layoutName.setOrientation(LinearLayout.VERTICAL);
                                layoutName.addView(placeCalories);
                                builder2.setView(layoutName);
                                builder2.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        txt = placeCalories;
                                        collectInputSnack();
                                    }
                                });
                                builder2.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                                builder2.show();
                            }
                        })
                        .setNegativeButton(R.string.use_camera, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .create()
                        .show();
            }
        });

        view.findViewById(R.id.add_dinner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage(R.string.add_message)
                        .setPositiveButton(R.string.enter_manually, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext());
                                final EditText placeCalories = new EditText(getContext());
                                placeCalories.setHint(R.string.example);
                                placeCalories.setInputType(InputType.TYPE_CLASS_NUMBER);
                                builder2.setTitle(R.string.insert_cal);
                                builder2.setView(placeCalories);
                                LinearLayout layoutName = new LinearLayout(getContext());
                                layoutName.setOrientation(LinearLayout.VERTICAL);
                                layoutName.addView(placeCalories);
                                builder2.setView(layoutName);
                                builder2.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        txt = placeCalories;
                                        collectInputDinner();
                                    }
                                });
                                builder2.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                                builder2.show();
                            }
                        })
                        .setNegativeButton(R.string.use_camera, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .create()
                        .show();
            }
        });

        view.findViewById(R.id.add_training).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext());
                final EditText placeCalories = new EditText(getContext());
                placeCalories.setHint(R.string.example);
                placeCalories.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder2.setTitle(R.string.insert_cal);
                builder2.setView(placeCalories);
                LinearLayout layoutName = new LinearLayout(getContext());
                layoutName.setOrientation(LinearLayout.VERTICAL);
                layoutName.addView(placeCalories);
                builder2.setView(layoutName);
                builder2.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        txt = placeCalories;
                        collectInputTraining();
                    }
                });
                builder2.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder2.show();
            }
        });
    }

    private void updateView(int breakfast_cal, int lunch_cal, int snack_cal, int dinner_cal,
                            int training_cal, int daily_cal_left) {
        breakfast.setText(String.valueOf(breakfast_cal));
        lunch.setText(String.valueOf(lunch_cal));
        snack.setText(String.valueOf(snack_cal));
        dinner.setText(String.valueOf(dinner_cal));
        training.setText(String.valueOf(training_cal));
        left.setText(String.valueOf(daily_cal_left));
    }

    private void collectInputBreakfast(){
        String getInput = txt.getText().toString();
        if (getInput.trim().equals("")){
            Toast.makeText(getContext(), R.string.error_null, Toast.LENGTH_LONG).show();
        } else {
            addBreakfast(Integer.parseInt(getInput));
        }
    }

    private void addBreakfast(int calories) {
        int oldCalories = Integer.parseInt(breakfast.getText().toString());
        addViewModel.updateBreakfast(calories, id, TODAY);
        breakfast.setText(String.valueOf(calories));
        Activity activity = getActivity();
        if(activity != null){
            viewModel.getCaloriesInfo().observe(getActivity(), calories2 -> {
                try{
                    for(ProfileDailyCalories cal : calories2){
                        if(cal.getId() == id && cal.getDate().equals(TODAY)){
                            caloriesLeft = cal.getDaily_cal_left();
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        if(oldCalories >= calories){
            caloriesLeft += (oldCalories - calories);
        }
        else {
            caloriesLeft -= (calories - oldCalories);
        }
        addViewModel.updateCaloriesLeft(caloriesLeft, id, TODAY);
        left.setText(String.valueOf(caloriesLeft));
        Toast.makeText(activity, R.string.add_confirm, Toast.LENGTH_SHORT).show();
    }

    private void collectInputLunch() {
        String getInput = txt.getText().toString();
        if (getInput.trim().equals("")){
            Toast.makeText(getContext(), R.string.error_null, Toast.LENGTH_LONG).show();
        } else {
            addLunch(Integer.parseInt(getInput));
        }
    }

    private void addLunch(int calories) {
        int oldCalories = Integer.parseInt(lunch.getText().toString());
        addViewModel.updateLunch(calories, id, TODAY);
        lunch.setText(String.valueOf(calories));
        Activity activity = getActivity();
        if(activity != null){
            viewModel.getCaloriesInfo().observe(getActivity(), calories2 -> {
                try{
                    for(ProfileDailyCalories cal : calories2){
                        if(cal.getId() == id && cal.getDate().equals(TODAY)){
                            caloriesLeft = cal.getDaily_cal_left();
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        if(oldCalories >= calories){
            caloriesLeft += (oldCalories - calories);
        }
        else {
            caloriesLeft -= (calories - oldCalories);
        }
        addViewModel.updateCaloriesLeft(caloriesLeft, id, TODAY);
        left.setText(String.valueOf(caloriesLeft));
        Toast.makeText(activity, R.string.add_confirm, Toast.LENGTH_SHORT).show();
    }

    private void collectInputSnack(){
        String getInput = txt.getText().toString();
        if (getInput.trim().equals("")){
            Toast.makeText(getContext(), R.string.error_null, Toast.LENGTH_LONG).show();
        } else {
            addSnack(Integer.parseInt(getInput));
        }
    }

    private void addSnack(int calories) {
        int oldCalories = Integer.parseInt(snack.getText().toString());
        addViewModel.updateSnack(calories, id, TODAY);
        snack.setText(String.valueOf(calories));
        Activity activity = getActivity();
        if(activity != null){
            viewModel.getCaloriesInfo().observe(getActivity(), calories2 -> {
                try{
                    for(ProfileDailyCalories cal : calories2){
                        if(cal.getId() == id && cal.getDate().equals(TODAY)){
                            caloriesLeft = cal.getDaily_cal_left();
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        if(oldCalories >= calories){
            caloriesLeft += (oldCalories - calories);
        }
        else {
            caloriesLeft -= (calories - oldCalories);
        }
        addViewModel.updateCaloriesLeft(caloriesLeft, id, TODAY);
        left.setText(String.valueOf(caloriesLeft));
        Toast.makeText(activity, R.string.add_confirm, Toast.LENGTH_SHORT).show();
    }

    private void collectInputDinner(){
        String getInput = txt.getText().toString();
        if (getInput.trim().equals("")){
            Toast.makeText(getContext(), R.string.error_null, Toast.LENGTH_LONG).show();
        } else {
            addDinner(Integer.parseInt(getInput));
        }
    }

    private void addDinner(int calories) {
        int oldCalories = Integer.parseInt(dinner.getText().toString());
        addViewModel.updateDinner(calories, id, TODAY);
        dinner.setText(String.valueOf(calories));
        Activity activity = getActivity();
        if(activity != null){
            viewModel.getCaloriesInfo().observe(getActivity(), calories2 -> {
                try{
                    for(ProfileDailyCalories cal : calories2){
                        if(cal.getId() == id && cal.getDate().equals(TODAY)){
                            caloriesLeft = cal.getDaily_cal_left();
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        if(oldCalories >= calories){
            caloriesLeft += (oldCalories - calories);
        }
        else {
            caloriesLeft -= (calories - oldCalories);
        }
        addViewModel.updateCaloriesLeft(caloriesLeft, id, TODAY);
        left.setText(String.valueOf(caloriesLeft));
        Toast.makeText(activity, R.string.add_confirm, Toast.LENGTH_SHORT).show();
    }

    private void collectInputTraining() {
        String getInput = txt.getText().toString();
        if (getInput.trim().equals("")){
            Toast.makeText(getContext(), R.string.error_null, Toast.LENGTH_LONG).show();
        } else {
            addTraining(Integer.parseInt(getInput));
        }
    }

    private void addTraining(int calories) {
        int oldCalories = Integer.parseInt(training.getText().toString());
        addViewModel.updateTraining(calories, id, TODAY);
        training.setText(String.valueOf(calories));
        Activity activity = getActivity();
        if(activity != null){
            viewModel.getCaloriesInfo().observe(getActivity(), calories2 -> {
                try{
                    for(ProfileDailyCalories cal : calories2){
                        if(cal.getId() == id && cal.getDate().equals(TODAY)){
                            caloriesLeft = cal.getDaily_cal_left();
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        if(oldCalories >= calories){
            caloriesLeft -= (oldCalories - calories);
        }
        else {
            caloriesLeft += (calories - oldCalories);
        }
        addViewModel.updateCaloriesLeft(caloriesLeft, id, TODAY);
        left.setText(String.valueOf(caloriesLeft));
        Toast.makeText(activity, R.string.add_confirm, Toast.LENGTH_SHORT).show();
    }
}
