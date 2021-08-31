package com.app.footprintcalculator.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
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
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//Calcualte transport
public class CalculateTransportFootprintActivity extends AppCompatActivity {

    EditText edtVehicleValue;
    TextView tvtrain, tvBus,tvPlane,tvTaxi,tvMetro;
    ImageView imgBus, imgTrain, imgPlane, imgMetro, imgTaxi;
    public static double vehicleValue, busValue, trainValue, planeValue, metroValue, taxiValue;
    public static boolean busFlag, trainFLag, planeFlag, metroFlag, taxiFlag;
    Button btnViewValues, btnCalculate;
    AlertDialog alertDialog;
    DatabaseReference databaseReference;
    FloatingActionButton fabHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_transport_footprint);

        databaseReference = FirebaseDatabase.getInstance().getReference("TransportEmissions").child(UserActivity.id);

        edtVehicleValue = findViewById(R.id.edtVehicleValue);
        imgBus = findViewById(R.id.imgBus);
        imgTrain = findViewById(R.id.imgTrain);
        imgPlane = findViewById(R.id.imgPlane);
        imgMetro = findViewById(R.id.imgMetro);
        imgTaxi = findViewById(R.id.imgTaxi);
        btnViewValues = findViewById(R.id.btnViewValues);
        btnCalculate = findViewById(R.id.btnCalculate);

        tvBus = findViewById(R.id.tvBus);
        tvtrain = findViewById(R.id.tvTrainn);
        tvTaxi = findViewById(R.id.tvTaxi);
        tvMetro = findViewById(R.id.tvMetro);
        tvPlane = findViewById(R.id.tvPlane);

        //edtVehicleValue.setText(String.format("%.1f",FootprintElementsActivity.vehicleEmission));

        fabHome = findViewById(R.id.fab_home);
        fabHome.setVisibility(View.GONE);

        fabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), UserActivity.class);
                startActivity(i);
            }
        });

        imgBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animate(tvBus);
                animatePic(imgBus);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), GetTransportValuesActivity.class);
                        intent.putExtra("key","Bus"); //pass key to next activity
                        startActivity(intent);
//
                    }
                }, 1500);

            }
        });
        imgTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animate(tvtrain);
                animatePic(imgTrain);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getApplicationContext(), CalculateTransportFootprintActivity.class));
                        Intent intent = new Intent(getApplicationContext(), GetTransportValuesActivity.class);
                        intent.putExtra("key","Train");
                        startActivity(intent);
//
                    }
                }, 1500);

            }
        });
        imgPlane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animatePic(imgPlane);
                animate(tvPlane);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getApplicationContext(), CalculateTransportFootprintActivity.class));
                        Intent intent = new Intent(getApplicationContext(), GetTransportValuesActivity.class);
                        intent.putExtra("key","Plane");
                        startActivity(intent);
//
                    }
                }, 1500);


            }
        });
        imgMetro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animate(tvMetro);
                animatePic(imgMetro);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getApplicationContext(), CalculateTransportFootprintActivity.class));
                        Intent intent = new Intent(getApplicationContext(), GetTransportValuesActivity.class);
                        intent.putExtra("key","Metro");
                        startActivity(intent);
//
                    }
                }, 1500);

            }
        });
        imgTaxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animate(tvTaxi);
                animatePic(imgTaxi);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getApplicationContext(), CalculateTransportFootprintActivity.class));
                        Intent intent = new Intent(getApplicationContext(), GetTransportValuesActivity.class);
                        intent.putExtra("key","Taxi");
                        startActivity(intent);
//
                    }
                }, 1500);

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

                fabHome.setVisibility(View.VISIBLE);

                //*****************************************************
                //Emission factor
                //*****************************************************
                FootprintElementsActivity.vehicleEmission = (vehicleValue*0.85)/525;
                FootprintElementsActivity.busEmission = (busValue*0.85/525);
                FootprintElementsActivity.trainEmission = (trainValue*0.85)/525;
                FootprintElementsActivity.planeEmission = ((planeValue*1.09) *0.85)/500;
                FootprintElementsActivity.metroEmission = (metroValue*0.85)/500;
                FootprintElementsActivity.taxiEmission = (taxiValue*0.85)/500;
                FootprintElementsActivity.totalTransEmission = FootprintElementsActivity.vehicleEmission+FootprintElementsActivity.busEmission+
                        FootprintElementsActivity.trainEmission+ FootprintElementsActivity.planeEmission+FootprintElementsActivity.metroEmission+
                        FootprintElementsActivity.taxiEmission;

                AlertDialog.Builder dailogBuilder = new AlertDialog.Builder(CalculateTransportFootprintActivity.this);
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

                tvVehicleScore.setText("Vehicle Emission : "+String.format("%.2f",FootprintElementsActivity.vehicleEmission));
                tvElectricityScore.setText("Bus Emission : "+String.format("%.2f",FootprintElementsActivity.busEmission));
                tvGasScore.setText("Train Emission : "+String.format("%.2f",FootprintElementsActivity.trainEmission));
                tvOilScore.setText("Plane Emission : "+String.format("%.2f",FootprintElementsActivity.planeEmission));
                tvWasteScore.setText("Metro Emission : "+String.format("%.2f",FootprintElementsActivity.metroEmission));
                tvWaterScore.setText("Taxi Emission : "+String.format("%.2f",FootprintElementsActivity.taxiEmission));
                tvTotalScore.setText("Total  Score : "+String.format("%.2f",FootprintElementsActivity.totalTransEmission));

                btnSub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String id = UserActivity.id;
                        TransportModel model = new TransportModel(id,FootprintElementsActivity.vehicleEmission,FootprintElementsActivity.busEmission,FootprintElementsActivity.trainEmission,
                                FootprintElementsActivity.planeEmission,FootprintElementsActivity.metroEmission,FootprintElementsActivity.taxiEmission,
                                FootprintElementsActivity.totalTransEmission);
                        databaseReference.child(id).setValue(model);
                        Toast.makeText(getApplicationContext(), "Data saved successfully", Toast.LENGTH_SHORT).show();
                        FootprintElementsActivity.tvTransportScore.setText("Score : "+String.format("%.2f",FootprintElementsActivity.totalTransEmission));
                        FootprintElementsActivity.transFlag=true;
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

    public void animate(TextView textView){

        YoYo.with(Techniques.RubberBand).duration(1500).repeat(0).playOn(textView);

    }

    public void animatePic(ImageView imageView){

        YoYo.with(Techniques.RubberBand).duration(1500).repeat(0).playOn(imageView);

    }
}
