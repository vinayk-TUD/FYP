package com.app.footprintcalculator.activities;

import android.content.Intent;
import android.os.Bundle;

import com.app.footprintcalculator.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class ShowGraphPlannerActivity extends BaseActivity {

    private static String SET_LABEL = "Footprints Comparison";
    BarChart barChart;
    int i = 1;
    ArrayList<BarEntry> values;
    List<String> years;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_graph);

        values = new ArrayList<>();
        years = new ArrayList<>();

        years.add("Bus");
        years.add("Train");
        years.add("Plane");
        years.add("Metro");
        years.add("Taxi");

        values.add(new BarEntry(i, (float) JourneyPlannerActivity.bus));
        i = i + 1;
        values.add(new BarEntry(i, (float) JourneyPlannerActivity.train));
        i = i + 1;
        values.add(new BarEntry(i, (float) JourneyPlannerActivity.plane));
        i = i + 1;
        values.add(new BarEntry(i, (float) JourneyPlannerActivity.metro));
        i = i + 1;
        values.add(new BarEntry(i, (float) JourneyPlannerActivity.taxi));

        barChart = findViewById(R.id.barChart);

        barChart.getDescription().setEnabled(false);
        barChart.setDrawValueAboveBar(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(years.size());
        xAxis.setGranularity(1f);
        //xAxis.setTextSize(14f);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int idex = (int) (value-1);
                return years.get(idex);
            }
        });


        YAxis axisLeft = barChart.getAxisLeft();
        axisLeft.setGranularity(10f);
        axisLeft.setAxisMinimum(0);

        YAxis axisRight = barChart.getAxisRight();
        axisRight.setGranularity(10f);
        axisRight.setAxisMinimum(0);

        BarDataSet set1 = new BarDataSet(values, SET_LABEL);
        set1.setColors(ColorTemplate.MATERIAL_COLORS);
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        BarData data = new BarData(dataSets);
        data.setValueTextSize(12f);

        barChart.setData(data);
        barChart.setFitBars(true);
        barChart.animateY(2000);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), UserActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}
