package com.app.footprintcalculator.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.footprintcalculator.R;

import java.text.DecimalFormat;

public class ViewTransportValuesActivity extends AppCompatActivity {

    TextView tvBusVal, tvTrainVal, tvPlantVal, tvMetroVal, tvTaxiVal;
    Button btnDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_transport_values);

        tvBusVal = findViewById(R.id.tvBusVal);
        tvTrainVal = findViewById(R.id.tvTrainVal);
        tvPlantVal = findViewById(R.id.tvPlantVal);
        tvMetroVal = findViewById(R.id.tvMetroVal);
        tvTaxiVal = findViewById(R.id.tvTaxiVal);
        btnDone = findViewById(R.id.btnDone);

        tvBusVal.setText(new DecimalFormat("##.##").format(FootprintElementsActivity.busEmission)+"");
        tvTrainVal.setText(new DecimalFormat("##.##").format(FootprintElementsActivity.trainEmission)+"");
        tvPlantVal.setText(new DecimalFormat("##.##").format(FootprintElementsActivity.planeEmission)+"");
        tvMetroVal.setText(new DecimalFormat("##.##").format(FootprintElementsActivity.metroEmission)+"");
        tvTaxiVal.setText(new DecimalFormat("##.##").format(FootprintElementsActivity.taxiEmission)+"");

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //Intent i = new Intent(getApplicationContext(), FootprintElementsActivity.class);



            }
        });

    }
}
