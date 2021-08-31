package com.app.footprintcalculator.activities;

import android.content.Intent;
import android.os.Bundle;

import com.app.footprintcalculator.R;
import com.app.footprintcalculator.models.CountriesModel;
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

public class ShowGraphCountriesActivity extends BaseActivity {

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

        //years.add(FootprintDetailsActivity.model.getDate());
        years.add(UserActivity.userName);
        values.add(new BarEntry(i, (float) FootprintDetailsActivity.finalTotal));
        i=i+1;
        for(CountriesModel model : ComparisonWithOtherCountriesActivity.selectList){
            years.add(model.getName());
            values.add(new BarEntry(i, (float) model.getValue()));
             i = i + 1;
        }
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
        set1.setColors(ColorTemplate.VORDIPLOM_COLORS);
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
