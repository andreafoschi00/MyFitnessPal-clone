package com.example.myfintesspal_andreafoschi;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.myfintesspal_andreafoschi.ViewModel.AddViewModel;
import com.example.myfintesspal_andreafoschi.ViewModel.ListViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DashboardFragment extends Fragment {

    private static final String TODAY = new SimpleDateFormat("dd-MM-yyyy", Locale.ITALY).format(new Date());

    private PieChart pieChart;
    private LineChart lineChart;

    private TextView goalText;
    private TextView caloriesText;
    private TextView trainingText;

    private AddViewModel addViewModel;

    private int id;
    private int goal;
    private int weight;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentActivity activity = getActivity();
        if(activity != null){
            Utilities.setUpToolbar((AppCompatActivity) activity, getString(R.string.dashboard));
            lineChart = view.findViewById(R.id.weight_chart);
            pieChart = view.findViewById(R.id.calories_chart);

            goalText = view.findViewById(R.id.goal_count);
            caloriesText = view.findViewById(R.id.food_count);
            trainingText = view.findViewById(R.id.exercise_count);

            SharedPreferences sharedPreferences = activity.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
            String pref_id = sharedPreferences.getString("id", "default");
            String pref_goal = sharedPreferences.getString("goal", "default");
            String pref_weight = sharedPreferences.getString("weight", "default");
            if (!pref_id.equals("default") && !pref_goal.equals("default") && !pref_weight.equals(("default"))) {
                id = Integer.parseInt(pref_id);
                goal = Integer.parseInt(pref_goal);
                weight = Integer.parseInt(pref_weight);
            }
            addViewModel = new ViewModelProvider(activity).get(AddViewModel.class);
            ListViewModel listViewModel = new ViewModelProvider(activity).get(ListViewModel.class);
            //Pie chart
            listViewModel.getCaloriesInfo().observe(activity, calories -> {
                try {
                    boolean found = false;
                    for (ProfileDailyCalories cal : calories) {
                        if (cal.getAccount_id() == id && cal.getDate().equals(TODAY)) {
                            found = true;
                            updatePieChart(cal.getBreakfast_cal(), cal.getLunch_cal(), cal.getSnack_cal(),
                                    cal.getDinner_cal(), cal.getTraining_cal(), cal.getDaily_cal_left());
                            break;
                        }
                    }
                    if (!found) {
                        addViewModel.addCalories(new ProfileDailyCalories(TODAY, goal, id));
                        updatePieChart(0, 0, 0, 0, 0, goal);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            //Line chart
            listViewModel.getWeightInfo().observe(activity, weights -> {
                try {
                    List<Pair<String, Integer>> weightEntries = new ArrayList<>();
                    for (ProfileWeight weight : weights) {
                        if (weight.getProfile_id() == id) {
                            weightEntries.add(new Pair<>(weight.getDate(), weight.getWeight()));
                        }
                    }
                    if (weightEntries.isEmpty()) {
                        weightEntries.add(new Pair<>(TODAY, weight));
                        addViewModel.addWeight(new ProfileWeight(TODAY, weight, id));
                    }
                    updateLineChart(weightEntries);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void updateLineChart(List<Pair<String, Integer>> weightEntries) {
        List<Entry> lineEntries = new ArrayList<>();
        for (Pair<String, Integer> entry : weightEntries) {
            String value = entry.get1().substring(0, entry.get1().indexOf("-"));
            int day = Integer.parseInt(value);
            lineEntries.add(new Entry(day, entry.get2()));
        }

        LineDataSet dataSet = new LineDataSet(lineEntries, "Monthly weight progression");
        LineData lineData = new LineData(dataSet);

        lineChart.getAxisLeft().setGranularity(1f);
        lineChart.getXAxis().setGranularity(1f);
        lineChart.setData(lineData);
        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setEnabled(false);
        lineChart.getAxisLeft().setTextColor(Color.WHITE);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getXAxis().setTextColor(Color.WHITE);
        lineChart.getViewPortHandler().setMinMaxScaleX(1, 5);
        lineChart.getViewPortHandler().setMinMaxScaleY(1, 3);
        lineChart.setScaleEnabled(false);
        lineChart.setTouchEnabled(false);
        dataSet.setColors(Color.WHITE);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(10f);
        lineChart.invalidate();
    }

    private void updatePieChart(int breakfast_cal, int lunch_cal, int snack_cal, int dinner_cal, int training_cal, int left_cal) {
        List<PieEntry> entries = new ArrayList<>();
        if(breakfast_cal != 0)
            entries.add(new PieEntry(breakfast_cal, getString(R.string.breakfast)));
        if(lunch_cal != 0)
            entries.add(new PieEntry(lunch_cal, getString(R.string.lunch)));
        if(snack_cal != 0)
            entries.add(new PieEntry(snack_cal, getString(R.string.snack)));
        if(dinner_cal != 0)
            entries.add(new PieEntry(dinner_cal, getString(R.string.dinner)));
        if(training_cal != 0)
            entries.add(new PieEntry(training_cal, getString(R.string.training)));
        PieDataSet set = new PieDataSet(entries, "Daily calories");
        set.setDrawIcons(false);
        set.setColors(new int[]{R.color.breakfast, R.color.lunch, R.color.snack, R.color.dinner,
                R.color.training, R.color.black}, getActivity());
        PieData data = new PieData(set);
        data.setValueTextSize(16f);
        data.setValueTextColor(Color.WHITE);
        pieChart.setEntryLabelTextSize(12f);
        pieChart.setData(data);
        pieChart.setRotationEnabled(false);
        pieChart.setTouchEnabled(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.setHoleColor(Color.TRANSPARENT);
        pieChart.setHoleRadius(70);
        if(left_cal >= 0)
            pieChart.setCenterText(String.valueOf(left_cal) + " " + getString(R.string.left));
        else
            pieChart.setCenterText(getString(R.string.excess) + " " + Math.abs(left_cal));
        pieChart.setCenterTextColor(Color.WHITE);
        pieChart.setCenterTextSize(22f);
        pieChart.setCenterTextTypeface(Typeface.DEFAULT_BOLD);
        pieChart.invalidate(); // refresh

        int totalCal = breakfast_cal + lunch_cal + snack_cal + dinner_cal;

        goalText.setText(String.valueOf(goal));
        caloriesText.setText(String.valueOf(totalCal));
        trainingText.setText(String.valueOf(training_cal));
    }
}
