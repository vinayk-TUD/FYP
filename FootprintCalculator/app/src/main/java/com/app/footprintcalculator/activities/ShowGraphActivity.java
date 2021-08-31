package com.app.footprintcalculator.activities;

import android.content.Intent;
import android.os.Bundle;

import com.app.footprintcalculator.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
//Show graphs
public class ShowGraphActivity extends BaseActivity {

    private static final int MAX_X_VALUE = 3;
    private static final int MAX_Y_VALUE = 50;
    private static final int MIN_Y_VALUE = 5;
    private static String SET_LABEL = "Footprints Comparison";
    BarChart barChart;
    int i = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_graph);

        barChart = findViewById(R.id.barChart);

        barChart.getDescription().setEnabled(false);
        barChart.setDrawValueAboveBar(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(ComparisonGraphActivity.years.size());
        xAxis.setGranularity(1f);
        xAxis.setTextSize(14f);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int idex = (int) (value-1);
                return ComparisonGraphActivity.years.get(idex);
            }
        });


        YAxis axisLeft = barChart.getAxisLeft();
        axisLeft.setGranularity(10f);
        axisLeft.setAxisMinimum(0);

        YAxis axisRight = barChart.getAxisRight();
        axisRight.setGranularity(10f);
        axisRight.setAxisMinimum(0);

        //Bar data on graph getting the list from previous screen
        BarDataSet set1 = new BarDataSet(ComparisonGraphActivity.values, SET_LABEL);
        set1.setColors(ColorTemplate.JOYFUL_COLORS);
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        BarData data = new BarData(dataSets);
        data.setValueTextSize(12f);

//        Random random = new Random();
//        for(int i =0;i<MAX_X_VALUE;i++){
//            float x = i;
//            float y = random.nextFloat()*(MAX_Y_VALUE-MIN_Y_VALUE)+MIN_Y_VALUE;
//            values.add(new BarEntry(x,y));
//        }
//        BarDataSet set = new BarDataSet(values, SET_LABEL);
//        ArrayList<IBarDataSet> dataSets1 = new ArrayList<>();
//        dataSets1.add(set);
//        BarData data1 = new BarData(dataSets1);
//        data1.setValueTextSize(12f);

        barChart.setData(data);
        barChart.setFitBars(true);
        barChart.animateY(2000);
        barChart.getDescription().setText("Comparisson ");
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
