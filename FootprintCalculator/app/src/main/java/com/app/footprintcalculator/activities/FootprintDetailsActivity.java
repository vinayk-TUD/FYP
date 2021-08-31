package com.app.footprintcalculator.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.app.footprintcalculator.R;
import com.app.footprintcalculator.models.FoodModel;
import com.app.footprintcalculator.models.FootprintModel;
import com.app.footprintcalculator.models.HomeModel;
import com.app.footprintcalculator.models.TransportModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

public class FootprintDetailsActivity extends BaseActivity {

    DatabaseReference dbRefHome, dbRefTransport, dbRefFood;
    public static FootprintModel model;
    TextView tvElectricityScore, tvGasScore, tvOilScore, tvWasteScore, tvWaterScore, tvTotalScore;
    TextView tvVehicleScore, tvBusScore, tvTrainScore, tvPlaneScore, tvMetroScore, tvTaxiScore, tvTotalTransportScore;
    TextView tvRedMeatScore,tvWhiteMeatScore,tvDairyScore,tvCerealsScore,tvVegetablesScore,tvFruitsScore,tvOilsScore,tvSnacksScore,tvDrinksScore,tvTotalFoodScore;
    TextView tv1, tv2, tv3;
    Button btnCompare;
    public static double homeTotal=0, tranportTotal=0, foodTotal=0, finalTotal=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footprint_details);

        showProgressDialog();
        model = MyFootprintActivity.model;

        dbRefHome = FirebaseDatabase.getInstance().getReference("HomeEmissions").child(model.getId());
        dbRefFood= FirebaseDatabase.getInstance().getReference("FoodEmissions").child(model.getId());
        dbRefTransport = FirebaseDatabase.getInstance().getReference("TransportEmissions").child(model.getId());

        btnCompare = findViewById(R.id.btnCompare);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tvElectricityScore = findViewById(R.id.tvElectricityScore);
        tvGasScore = findViewById(R.id.tvGasScore);
        tvOilScore = findViewById(R.id.tvOilScore);
        tvWasteScore = findViewById(R.id.tvWasteScore);
        tvWaterScore = findViewById(R.id.tvWaterScore);
        tvTotalScore = findViewById(R.id.tvTotalScore);
        tvVehicleScore = findViewById(R.id.tvVehicleScore);
        tvBusScore = findViewById(R.id.tvBusScore);
        tvTrainScore = findViewById(R.id.tvTrainScore);
        tvPlaneScore = findViewById(R.id.tvPlaneScore);
        tvMetroScore = findViewById(R.id.tvMetroScore);
        tvTaxiScore = findViewById(R.id.tvTaxiScore);
        tvTotalTransportScore = findViewById(R.id.tvTotalTransportScore);
        tvRedMeatScore = findViewById(R.id.tvRedMeatScore);
        tvWhiteMeatScore = findViewById(R.id.tvWhiteMeatScore);
        tvDairyScore = findViewById(R.id.tvDairyScore);
        tvCerealsScore = findViewById(R.id.tvCerealsScore);
        tvVegetablesScore = findViewById(R.id.tvVegetablesScore);
        tvFruitsScore = findViewById(R.id.tvFruitsScore);
        tvOilsScore = findViewById(R.id.tvOilsScore);
        tvSnacksScore = findViewById(R.id.tvSnacksScore);
        tvDrinksScore = findViewById(R.id.tvDrinksScore);
        tvTotalFoodScore = findViewById(R.id.tvTotalFoodScore);

        btnCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ComparisonWithOtherCountriesActivity.class));
            }
        });
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), EditHomeFootprintActivity.class));
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), EditTransportFootprintActivity.class));
            }
        });
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), EditFoodFootprintActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        dbRefHome.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getChildrenCount()>0){
                    for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                        HomeModel model = snapshot1.getValue(HomeModel.class);
                        tvElectricityScore.setText("Electricity Emission : "+new DecimalFormat("#.##").format(model.getElcetricityEmission()));
                        tvGasScore.setText("Gas Emission : "+new DecimalFormat("#.##").format(model.getGasEmission()));
                        tvOilScore.setText("Oil Emission : "+new DecimalFormat("#.##").format(model.getOilEmission()));
                        tvWasteScore.setText("Waste Emission : "+new DecimalFormat("#.##").format(model.getWasteEmission()));
                        tvWaterScore.setText("Water Emission : "+new DecimalFormat("#.##").format(model.getWaterEmission()));
                        tvTotalScore.setText("Total Home Emission : "+new DecimalFormat("#.##").format(model.getTotalEmission()));
                        homeTotal = model.getTotalEmission();
                    }
                    hideProgressDialog();
                }else {
                    tv1.setText("No Home Emission Values\n Click to Add Some");
                    tvElectricityScore.setVisibility(View.GONE);
                    tvGasScore.setVisibility(View.GONE);
                    tvOilScore.setVisibility(View.GONE);
                    tvWasteScore.setVisibility(View.GONE);
                    tvWaterScore.setVisibility(View.GONE);
                    tvTotalScore.setVisibility(View.GONE);
                    hideProgressDialog();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                hideProgressDialog();
            }
        });
        dbRefTransport.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getChildrenCount()>0){
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){
                        TransportModel model = snapshot1.getValue(TransportModel.class);
                        tvVehicleScore.setText("Vehicle Emission : "+new DecimalFormat("#.##").format(model.getVehicleEmission()));
                        tvBusScore.setText("Bus Emission : "+new DecimalFormat("#.##").format(model.getBusEmission()));
                        tvTrainScore.setText("Train Emission : "+new DecimalFormat("#.##").format(model.getTrainEmission()));
                        tvPlaneScore.setText("Plane Emission : "+new DecimalFormat("#.##").format(model.getPlaneEmission()));
                        tvMetroScore.setText("Metro Emission : "+new DecimalFormat("#.##").format(model.getMetroEmission()));
                        tvTaxiScore.setText("Taxi Emission : "+new DecimalFormat("#.##").format(model.getTaxiEmission()));
                        tvTotalTransportScore.setText("Total Transport Emission : "+new DecimalFormat("#.##").format(model.getTotalEmission()));
                        tranportTotal = model.getTotalEmission();
                    }
                    hideProgressDialog();
                }else {
                    tv2.setText("No Tranport Emission Values\n Click to Add Some");
                    tvVehicleScore.setVisibility(View.GONE);
                    tvBusScore.setVisibility(View.GONE);
                    tvTrainScore.setVisibility(View.GONE);
                    tvPlaneScore.setVisibility(View.GONE);
                    tvMetroScore.setVisibility(View.GONE);
                    tvTaxiScore.setVisibility(View.GONE);
                    tvTotalTransportScore.setVisibility(View.GONE);

                    hideProgressDialog();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                hideProgressDialog();
            }
        });
        dbRefFood.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getChildrenCount()>0){
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){
                        FoodModel model = snapshot1.getValue(FoodModel.class);
                        tvRedMeatScore.setText("Red Meat Emission : "+new DecimalFormat("#.##").format(model.getRedMeatEmission()));
                        tvWhiteMeatScore.setText("White Meat Emission : "+new DecimalFormat("#.##").format(model.getWhiteMeatEmission()));
                        tvDairyScore.setText("Dairy Emission : "+new DecimalFormat("#.##").format(model.getDairyEmission()));
                        tvCerealsScore.setText("Cereals Emission : "+new DecimalFormat("#.##").format(model.getCerealsEmission()));
                        tvVegetablesScore.setText("Vegetables Emission : "+new DecimalFormat("#.##").format(model.getVegetablesEmission()));
                        tvFruitsScore.setText("Fruit Emission : "+new DecimalFormat("#.##").format(model.getFruitsEmission()));
                        tvOilsScore.setText("Oils Emission : "+new DecimalFormat("#.##").format(model.getOilsEmission()));
                        tvSnacksScore.setText("Snacks Emission : "+new DecimalFormat("#.##").format(model.getSnacksEmission()));
                        tvDrinksScore.setText("Drinks Emission : "+new DecimalFormat("#.##").format(model.getDrinksEmission()));
                        tvTotalFoodScore.setText("Total Food Emission : "+new DecimalFormat("#.##").format(model.getTotalEmission()));

                        foodTotal = model.getTotalEmission();
                    }
                    finalTotal = homeTotal+tranportTotal+foodTotal;
                    hideProgressDialog();
                }else {
                    tv3.setText("No Food Emission Values \n Click to Add Some");
                    tvRedMeatScore.setVisibility(View.GONE);
                    tvWhiteMeatScore.setVisibility(View.GONE);
                    tvDairyScore.setVisibility(View.GONE);
                    tvCerealsScore.setVisibility(View.GONE);
                    tvVegetablesScore.setVisibility(View.GONE);
                    tvFruitsScore.setVisibility(View.GONE);
                    tvOilsScore.setVisibility(View.GONE);
                    tvSnacksScore.setVisibility(View.GONE);
                    tvDrinksScore.setVisibility(View.GONE);
                    tvTotalFoodScore.setVisibility(View.GONE);

                    hideProgressDialog();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                hideProgressDialog();
            }
        });
    }
}
