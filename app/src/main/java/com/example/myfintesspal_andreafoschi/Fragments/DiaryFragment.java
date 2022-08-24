package com.example.myfintesspal_andreafoschi.Fragments;

import static com.example.myfintesspal_andreafoschi.Utils.Utilities.TODAY;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.myfintesspal_andreafoschi.Activities.CaptureAct;
import com.example.myfintesspal_andreafoschi.Tables.ProfileDailyCalories;
import com.example.myfintesspal_andreafoschi.R;
import com.example.myfintesspal_andreafoschi.Utils.Utilities;
import com.example.myfintesspal_andreafoschi.ViewModel.AddViewModel;
import com.example.myfintesspal_andreafoschi.ViewModel.ListViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

public class DiaryFragment extends Fragment{

    private AddViewModel addViewModel;
    private ListViewModel viewModel;

    private int id;
    private int goal;
    private int caloriesLeft;

    private String trainingSelected;
    private String buttonPressed;

    private TextView breakfast;
    private TextView lunch;
    private TextView snack;
    private TextView dinner;
    private TextView training;
    private TextView left;
    private TextView trainingIcon;

    private EditText txt;

    private final Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

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
            trainingIcon = view.findViewById(R.id.training_icon);
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
                        if(cal.getAccount_id() == id && cal.getDate().equals(TODAY)){
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
                                buttonPressed = "Breakfast";
                                scanCode();
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
                                buttonPressed = "Lunch";
                                scanCode();
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
                                buttonPressed = "Snack";
                                scanCode();
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
                                buttonPressed = "Dinner";
                                scanCode();
                            }
                        })
                        .create()
                        .show();
            }
        });

        view.findViewById(R.id.add_training).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] listItems = new String[]{getString(R.string.run),
                        getString(R.string.cycling), getString(R.string.cardio),
                        getString(R.string.walk), getString(R.string.swim),
                        getString(R.string.ski)};
                trainingSelected = listItems[0];
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.title_training);
                builder.setSingleChoiceItems(listItems, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        trainingSelected = listItems[i];
                    }
                })
                .setPositiveButton(getString(R.string.next), new DialogInterface.OnClickListener() {
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
                                collectInputTraining();
                            }
                        });
                        builder2.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        builder2.setCancelable(false);
                        builder2.show();
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setCancelable(false)
                .show();
            }
        });
    }

    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt(getString(R.string.flash));
        options.setOrientationLocked(true);
        options.setBeepEnabled(false);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }

    private final ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result ->
    {
        if(result.getContents() != null){
            if(isNumeric(result.getContents())){
                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                builder.setTitle(R.string.result);
                builder.setMessage(getString(R.string.result2) + " " + result.getContents() + getString(R.string.string_continue));
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int number = Math.round(Float.parseFloat(result.getContents()));
                        switch (buttonPressed){
                            case "Breakfast":
                                addBreakfast(number);
                            break;
                            case "Lunch":
                                addLunch(number);
                            break;
                            case "Snack":
                                addSnack(number);
                            break;
                            case "Dinner":
                                addDinner(number);
                            break;
                            default:
                            break;
                        }
                    }
                });
                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
            else {
                Toast.makeText(getContext(), R.string.error_code_reader, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), R.string.no_value_collected, Toast.LENGTH_SHORT).show();
        }
    });

    private boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches() && Float.parseFloat(strNum) > 0;
    }

    private void updateLogo(String logo){
        switch (logo){
            case "Run":
            case "Corsa":
                trainingIcon.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_directions_run_24, 0, 0, 0);
                break;
            case "Cycling":
            case "Ciclismo":
                trainingIcon.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_directions_bike_24, 0, 0, 0);
                break;
            case "Cardio":
            case "Cardiovascolare":
                trainingIcon.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_fitness_center_24, 0, 0, 0);
                break;
            case "Walk":
            case "Camminata":
                trainingIcon.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_directions_walk_24, 0, 0, 0);
                break;
            case "Swim":
            case "Nuoto":
                trainingIcon.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_pool_24, 0, 0, 0);
                break;
            case "Skiing":
            case "Sciata":
                trainingIcon.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_downhill_skiing_24, 0, 0, 0);
                break;
            case "clear":
                trainingIcon.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                break;
            default:
                break;
        }
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TODAY+"-training", logo);
        editor.apply();
    }

    private void updateView(int breakfast_cal, int lunch_cal, int snack_cal, int dinner_cal,
                            int training_cal, int daily_cal_left) {
        breakfast.setText(String.valueOf(breakfast_cal));
        lunch.setText(String.valueOf(lunch_cal));
        snack.setText(String.valueOf(snack_cal));
        dinner.setText(String.valueOf(dinner_cal));
        training.setText(String.valueOf(training_cal));
        left.setText(String.valueOf(daily_cal_left));
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        String logo = sharedPreferences.getString(TODAY+"-training", "null");
        if(!logo.equals("null")){
            updateLogo(logo);
        }
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
                        if(cal.getAccount_id() == id && cal.getDate().equals(TODAY)){
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
                        if(cal.getAccount_id() == id && cal.getDate().equals(TODAY)){
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
                        if(cal.getAccount_id() == id && cal.getDate().equals(TODAY)){
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
                        if(cal.getAccount_id() == id && cal.getDate().equals(TODAY)){
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
                        if(cal.getAccount_id() == id && cal.getDate().equals(TODAY)){
                            caloriesLeft = cal.getDaily_cal_left();
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        if(calories == 0){
            updateLogo("clear");
        } else {
            updateLogo(trainingSelected);
        }
        if(oldCalories >= calories){
            caloriesLeft -= (oldCalories - calories);
        }
        else {
            caloriesLeft += (calories - oldCalories);
        }
        addViewModel.updateCaloriesLeft(caloriesLeft, id, TODAY);
        left.setText(String.valueOf(caloriesLeft));
        Toast.makeText(activity, R.string.training_added, Toast.LENGTH_SHORT).show();
    }
}
