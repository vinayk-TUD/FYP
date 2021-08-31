package com.app.footprintcalculator.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.app.footprintcalculator.R;
import com.app.footprintcalculator.models.TransportModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditTransportFootprintActivity extends AppCompatActivity {

    EditText edtVehicleValue;
    ImageView imgBus, imgTrain, imgPlane, imgMetro, imgTaxi;
    public static double vehicleValue, busValue, trainValue, planeValue, metroValue, taxiValue;
    public static boolean busFlag, trainFLag, planeFlag, metroFlag, taxiFlag;
    double vehicleEmission=0.0,busEmission=0.0, trainEmission=0.0,planeEmission=0.0, metroEmission=0.0, taxiEmission=0.0, totalTransEmission=0.0;
    Button btnViewValues, btnCalculate;
    AlertDialog alertDialog;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_transport_footprint);

        databaseReference = FirebaseDatabase.getInstance().getReference("TransportEmissions").child(FootprintDetailsActivity.model.getId());

        edtVehicleValue = findViewById(R.id.edtVehicleValue);
        imgBus = findViewById(R.id.imgBus);
        imgTrain = findViewById(R.id.imgTrain);
        imgPlane = findViewById(R.id.imgPlane);
        imgMetro = findViewById(R.id.imgMetro);
        imgTaxi = findViewById(R.id.imgTaxi);
        btnViewValues = findViewById(R.id.btnViewValues);
        btnCalculate = findViewById(R.id.btnCalculate);

//        edtVehicleValue.setText(String.format("%.1f",FootprintElementsActivity.vehicleEmission));

        imgBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditTransportValuesActivity.class);
                intent.putExtra("key","Bus");
                startActivity(intent);
            }
        });
        imgTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditTransportValuesActivity.class);
                intent.putExtra("key","Train");
                startActivity(intent);
            }
        });
        imgPlane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditTransportValuesActivity.class);
                intent.putExtra("key","Plane");
                startActivity(intent);

            }
        });
        imgMetro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditTransportValuesActivity.class);
                intent.putExtra("key","Metro");
                startActivity(intent);
            }
        });
        imgTaxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditTransportValuesActivity.class);
                intent.putExtra("key","Taxi");
                startActivity(intent);
            }
        });

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtVehicleValue.getText().toString().isEmpty()){
                    vehicleValue = 0;
                }else {
                    vehicleValue = Double.parseDouble(edtVehicleValue.getText().toString());
                }
                if(!busFlag){
                    busValue = 0;
                }
                if(!trainFLag){
                    trainValue = 0;
                }
                if(!planeFlag){
                    planeValue = 0;
                }
                if(!metroFlag){
                    metroValue = 0;
                }
                if(!taxiFlag){
                    taxiValue = 0;
                }

                vehicleEmission = vehicleValue*0.85;
                busEmission = busValue*0.85;
                trainEmission = trainValue*0.85;
                planeEmission = planeValue*0.85;
                metroEmission = metroValue*0.85;
                taxiEmission = taxiValue*0.85;
                totalTransEmission = vehicleEmission+busEmission+ trainEmission+ planeEmission+metroEmission+ taxiEmission;

                AlertDialog.Builder dailogBuilder = new AlertDialog.Builder(EditTransportFootprintActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.dialog_layout4, null);
                dailogBuilder.setView(dialogView);

                final TextView tvVehicleScore = dialogView.findViewById(R.id.tvVehicleScore);
                final TextView tvElectricityScore = dialogView.findViewById(R.id.tvElectricityScore);
                final TextView tvGasScore = dialogView.findViewById(R.id.tvGasScore);
                final TextView tvOilScore = dialogView.findViewById(R.id.tvOilScore);
                final TextView tvWasteScore = dialogView.findViewById(R.id.tvWasteScore);
                final TextView tvWaterScore = dialogView.findViewById(R.id.tvWaterScore);
                final TextView tvTotalScore = dialogView.findViewById(R.id.tvTotalScore);
                final Button btnSub = dialogView.findViewById(R.id.btnSub);

                tvVehicleScore.setText("Vehicle Emission : "+String.format("%.2f",vehicleEmission));
                tvElectricityScore.setText("Bus Emission : "+String.format("%.2f",busEmission));
                tvGasScore.setText("Train Emission : "+String.format("%.2f",trainEmission));
                tvOilScore.setText("Plane Emission : "+String.format("%.2f",planeEmission));
                tvWasteScore.setText("Metro Emission : "+String.format("%.2f",metroEmission));
                tvWaterScore.setText("Taxi Emission : "+String.format("%.2f",taxiEmission));
                tvTotalScore.setText("Total  Score : "+String.format("%.2f",totalTransEmission));

                btnSub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String id = FootprintDetailsActivity.model.getId();
                        TransportModel model = new TransportModel(id,vehicleEmission,busEmission,trainEmission,planeEmission,metroEmission,taxiEmission,totalTransEmission);
                        databaseReference.child(id).setValue(model);
                        Toast.makeText(getApplicationContext(), "Data saved successfully", Toast.LENGTH_SHORT).show();
                        busFlag = false;
                        trainFLag = false;
                        planeFlag = false;
                        metroFlag = false;
                        taxiFlag = false;
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
        btnViewValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ViewTransportValuesActivity.class));
            }
        });
    }
}
