package com.app.footprintcalculator.activities;

import android.content.Intent;
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
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
// Calculate home footprint
public class CalculateHomeFootprintActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    EditText edtElectricity, edtGas, edtOil, edtWaste, edtWater;
    double electricity, gas, oil, waste, water;
    Button btnCalculate;
    AlertDialog alertDialog;
    FloatingActionButton fabHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_home_footprint);

        databaseReference = FirebaseDatabase.getInstance().getReference("HomeEmissions").child(UserActivity.id);
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

        fabHome = findViewById(R.id.fab_home);

        TextView txtHomeDesc = findViewById(R.id.txtHomeDesc);

        YoYo.with(Techniques.RubberBand).duration(1500).repeat(1).playOn(txtHomeDesc);
//
//        //fabHome.setVisibility(View.GONE);
//        fabHome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getApplicationContext(), UserActivity.class);
//                startActivity(i);
//            }
//        });

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Check to see if anything is empty to default to 0
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
                //*********************************************
                //Emission factor calculations
                //******************************************************
                FootprintElementsActivity.elecEmission = (electricity*0.3245)/250;
                FootprintElementsActivity.gasEmission = (gas*0.2047)/250;
                FootprintElementsActivity.oilEmission = (oil*0.2570)/250;
                FootprintElementsActivity.wasteEmission = (waste*0.85)/250;
                double waterEF = (water*0.344 + water*0.708)/1000;
                FootprintElementsActivity.waterEmission = waterEF/250;
                FootprintElementsActivity.totalEmission = (FootprintElementsActivity.elecEmission+FootprintElementsActivity.gasEmission+
                        FootprintElementsActivity.oilEmission+FootprintElementsActivity.wasteEmission+FootprintElementsActivity.waterEmission)/6;

                //Dialog to show user results
                AlertDialog.Builder dailogBuilder = new AlertDialog.Builder(CalculateHomeFootprintActivity.this);
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

                tvElectricityScore.setText("Electricity Emission : "+String.format("%.2f",FootprintElementsActivity.elecEmission));
                tvGasScore.setText("Gas Emission : "+String.format("%.2f",FootprintElementsActivity.gasEmission));
                tvOilScore.setText("Oil Emission : "+String.format("%.2f",FootprintElementsActivity.oilEmission));
                tvWasteScore.setText("Waste Emission : "+String.format("%.2f",FootprintElementsActivity.wasteEmission));
                tvWaterScore.setText("Water Emission : "+String.format("%.2f",FootprintElementsActivity.waterEmission));
                tvTotalScore.setText("Total Score : "+String.format("%.2f",FootprintElementsActivity.totalEmission));


                //button on the dialog to save the data to firebase
                btnSub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String id = UserActivity.id;
                        HomeModel model = new HomeModel(id, FootprintElementsActivity.elecEmission,FootprintElementsActivity.gasEmission,
                                FootprintElementsActivity.oilEmission,FootprintElementsActivity.wasteEmission,FootprintElementsActivity.waterEmission,
                                FootprintElementsActivity.totalEmission);
                        databaseReference.child(id).setValue(model);
                        Toast.makeText(CalculateHomeFootprintActivity.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                        FootprintElementsActivity.tvHomeScore.setText("Score : "+String.format("%.2f",FootprintElementsActivity.totalEmission));
                        FootprintElementsActivity.homeFlag=true;
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
