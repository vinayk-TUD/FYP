package com.app.footprintcalculator.activities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.app.footprintcalculator.R;
import com.app.footprintcalculator.models.HomeModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditHomeFootprintActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    EditText edtElectricity, edtGas, edtOil, edtWaste, edtWater;
    double electricity, gas, oil, waste, water;
    Button btnCalculate;
    AlertDialog alertDialog;
    double totalEmission = 0.0, elecEmission=0.0, gasEmission=0.0, oilEmission=0.0, wasteEmission=0.0, waterEmission=0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_home_footprint);

        databaseReference = FirebaseDatabase.getInstance().getReference("HomeEmissions").child(FootprintDetailsActivity.model.getId());
        edtElectricity = findViewById(R.id.edtElectricity);
        edtGas = findViewById(R.id.edtGas);
        edtOil = findViewById(R.id.edtOil);
        edtWaste = findViewById(R.id.edtWaste);
        edtWater = findViewById(R.id.edtWater);
        btnCalculate = findViewById(R.id.btnCalculate);

//        edtElectricity.setText(String.format("%.1f",FootprintElementsActivity.elecEmission));
//        edtGas.setText(String.format("%.1f",FootprintElementsActivity.gasEmission));
//        edtOil.setText(String.format("%.1f",FootprintElementsActivity.oilEmission));
//        edtWaste.setText(String.format("%.1f",FootprintElementsActivity.wasteEmission));
//        edtWater.setText(String.format("%.1f",FootprintElementsActivity.waterEmission));

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtElectricity.getText().toString().isEmpty()){
                    electricity = 0;
                }else {
                    electricity = Double.parseDouble(edtElectricity.getText().toString())*6;
                }
                if(edtGas.getText().toString().isEmpty()){
                    gas = 0;
                }else {
                    gas = Double.parseDouble(edtGas.getText().toString())*6;
                }
                if(edtOil.getText().toString().isEmpty()){
                   oil = 0;
                }else {
                    oil = Double.parseDouble(edtOil.getText().toString())*6;
                }
                if(edtWaste.getText().toString().isEmpty()){
                    waste = 0;
                }else {
                    waste = Double.parseDouble(edtWaste.getText().toString())*6;
                }
                if(edtWater.getText().toString().isEmpty()){
                   water = 0;
                }else {
                    water = Double.parseDouble(edtWater.getText().toString())*6;
                }

                elecEmission = electricity*0.85;
                gasEmission = gas*0.85;
                oilEmission = oil*0.85;
                wasteEmission = waste*0.85;
                waterEmission = water*0.344 + water*0.708;
                totalEmission = elecEmission+gasEmission+oilEmission+wasteEmission+waterEmission;

                AlertDialog.Builder dailogBuilder = new AlertDialog.Builder(EditHomeFootprintActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.dialog_layout, null);
                dailogBuilder.setView(dialogView);

                final TextView tvElectricityScore = dialogView.findViewById(R.id.tvElectricityScore);
                final TextView tvGasScore = dialogView.findViewById(R.id.tvGasScore);
                final TextView tvOilScore = dialogView.findViewById(R.id.tvOilScore);
                final TextView tvWasteScore = dialogView.findViewById(R.id.tvWasteScore);
                final TextView tvWaterScore = dialogView.findViewById(R.id.tvWaterScore);
                final TextView tvTotalScore = dialogView.findViewById(R.id.tvTotalScore);
                final Button btnSub = dialogView.findViewById(R.id.btnSub);

                tvElectricityScore.setText("Electricity Emission : "+String.format("%.2f",elecEmission));
                tvGasScore.setText("Gas Emission : "+String.format("%.2f",gasEmission));
                tvOilScore.setText("Oil Emission : "+String.format("%.2f",oilEmission));
                tvWasteScore.setText("Waste Emission : "+String.format("%.2f",wasteEmission));
                tvWaterScore.setText("Water Emission : "+String.format("%.2f",waterEmission));
                tvTotalScore.setText("Total Score : "+String.format("%.2f",totalEmission));

                btnSub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String id = FootprintDetailsActivity.model.getId();
                        HomeModel model = new HomeModel(id, elecEmission,gasEmission,oilEmission,wasteEmission,waterEmission,totalEmission);
                        databaseReference.child(id).setValue(model);
                        Toast.makeText(EditHomeFootprintActivity.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                        finish();
                    }
                });
                alertDialog = dailogBuilder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
            }
        });
    }
}
