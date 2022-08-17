package com.example.myfintesspal_andreafoschi;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DashboardFragment extends Fragment {

    PieChart pieChart;
    LineChart lineChart;
    TextView goalText;
    TextView caloriesText;
    TextView trainingText;

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
        Activity activity = getActivity();
        if(activity != null){
            Utilities.setUpToolbar((AppCompatActivity) activity, getString(R.string.dashboard));
            lineChart = view.findViewById(R.id.weight_chart);
            pieChart = view.findViewById(R.id.calories_chart);

            goalText = view.findViewById(R.id.goal_count);
            caloriesText = view.findViewById(R.id.food_count);
            trainingText = view.findViewById(R.id.exercise_count);

            updatePieChart();
            updateLineChart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updatePieChart();
        updateLineChart();
    }

    private void updateLineChart() {
        List<Entry> lineEntries = new ArrayList<Entry>();
        lineEntries.add(new Entry(1, 90));
        lineEntries.add(new Entry(4, 88));
        lineEntries.add(new Entry(7, 85));
        lineEntries.add(new Entry(10, 83));
        lineEntries.add(new Entry(13, 82));
        lineEntries.add(new Entry(17, 79));
        LineDataSet dataSet = new LineDataSet(lineEntries, "Monthly weight progression");
        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setEnabled(false);
        lineChart.getAxisLeft().setTextColor(Color.WHITE);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getXAxis().setTextColor(Color.WHITE);
        lineChart.setClickable(false);
        dataSet.setColors(Color.WHITE);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(10f);
    }

    private void updatePieChart() {
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(200, "Breakfast"));
        entries.add(new PieEntry(1200, "Lunch"));
        entries.add(new PieEntry(500, "Snack"));
        entries.add(new PieEntry(700, "Dinner"));
        entries.add(new PieEntry(500, "Training"));
        entries.add(new PieEntry(400, "Left"));
        PieDataSet set = new PieDataSet(entries, "Daily calories");
        set.setDrawIcons(false);
        set.setSliceSpace(2f);
        set.setIconsOffset(new MPPointF(0, 40));
        set.setSelectionShift(5f);
        set.setColors(new int[]{R.color.breakfast, R.color.lunch, R.color.snack, R.color.dinner,
                R.color.training, R.color.black}, getActivity());
        PieData data = new PieData(set);
        data.setValueTextSize(18f);
        data.setValueTextColor(Color.WHITE);
        pieChart.setData(data);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.setHoleColor(Color.TRANSPARENT);
        pieChart.setHoleRadius(70);
        pieChart.setCenterTextColor(Color.WHITE);
        pieChart.setCenterTextSize(30f);
        pieChart.setCenterTextTypeface(Typeface.DEFAULT_BOLD);
        pieChart.invalidate(); // refresh

        goalText.setText("3000");
        caloriesText.setText("3100");
        trainingText.setText("500");
    }
}
