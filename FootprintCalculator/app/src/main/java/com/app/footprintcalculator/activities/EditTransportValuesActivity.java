package com.app.footprintcalculator.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.app.footprintcalculator.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditTransportValuesActivity extends AppCompatActivity {

    String key="";
    TextView tvMode, tvPlane, tv2;
    ImageView imgPlane;
    EditText edtNumTrips, edtDistanceTravel;
    TextInputLayout numberTrips, trips;
    Button btnSubmit;
    public static List<Double> planeValues;
    public static double planeValue = 0.0;
    Spinner spnChoice, spnDistance;
    public static boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_transport_values);

        Intent intent = getIntent();
        key = intent.getStringExtra("key");

        tvMode = findViewById(R.id.tvMode);
        tvPlane = findViewById(R.id.tvPlane);
        tv2 = findViewById(R.id.tv2);
        imgPlane = findViewById(R.id.imgPlane);
        edtNumTrips = findViewById(R.id.edtNumTrips);
        numberTrips = findViewById(R.id.numberTrips);
        edtDistanceTravel = findViewById(R.id.edtDistanceTravel);
        trips = findViewById(R.id.trips);
        btnSubmit = findViewById(R.id.btnSubmit);
        spnChoice = findViewById(R.id.spnChoice);
        spnDistance = findViewById(R.id.spnDistance);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Info");
        builder.setMessage("Please select Accurate values for each\n consumption from input choice for accurate results\nSigle estimated value did not provide accurate results");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        final List<String> type = Arrays.asList(getResources().getStringArray(R.array.choice));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item,type);
        spnChoice.setAdapter(adapter);

        final List<String> type2 = new ArrayList<>();
        type2.add("Select distance in KM");
        for(int i=5;i<=100;i=i+5){
            type2.add(""+i);
        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_item,type2);
        spnDistance.setAdapter(adapter2);

        spnChoice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str = type.get(position);
                if(str.equals("Estimated average(Single value)")){
                    tv2.setText("Single estimated value\nfor all trips of month");
                    flag = false;
                    edtDistanceTravel.setKeyListener(DigitsKeyListener.getInstance("0123456789"));

                    tvMode.setVisibility(View.VISIBLE);
                    btnSubmit.setVisibility(View.VISIBLE);
                    if(key.equals("Bus")){
                        tvMode.setText("Bus");
                        spnDistance.setVisibility(View.VISIBLE);
                        numberTrips.setVisibility(View.GONE);
                        trips.setVisibility(View.GONE);
                    }else if(key.equals("Train")){
                        tvMode.setText("Train");
                        spnDistance.setVisibility(View.VISIBLE);
                        numberTrips.setVisibility(View.GONE);
                        trips.setVisibility(View.GONE);
                    }else if(key.equals("Metro")){
                        tvMode.setText("Metro");
                        spnDistance.setVisibility(View.VISIBLE);
                        numberTrips.setVisibility(View.GONE);
                        trips.setVisibility(View.GONE);
                    }else if(key.equals("Taxi")){
                        tvMode.setText("Taxi");
                        spnDistance.setVisibility(View.VISIBLE);
                        numberTrips.setVisibility(View.GONE);
                        trips.setVisibility(View.GONE);
                    }else if(key.equals("Plane")){
                        tvMode.setText("Plane");
                        trips.setVisibility(View.GONE);
                        numberTrips.setVisibility(View.GONE);
                        spnDistance.setVisibility(View.GONE);
                        tvPlane.setVisibility(View.VISIBLE);
                        imgPlane.setVisibility(View.VISIBLE);
                    }

                }else if(str.equals("Accurate values for each consumption")){

                    tv2.setText("Enter distance(km) for each trip by comma separated\ne.g. 5,10 means 1st trip is 5km and second is 10km.");
                    flag = true;
                    edtDistanceTravel.setKeyListener(DigitsKeyListener.getInstance("0123456789,"));

                    tvMode.setVisibility(View.VISIBLE);
                    btnSubmit.setVisibility(View.VISIBLE);
                    if(key.equals("Bus")){
                        tvMode.setText("Bus");
                        spnDistance.setVisibility(View.GONE);
                        numberTrips.setVisibility(View.VISIBLE);
                        trips.setVisibility(View.VISIBLE);
                    }else if(key.equals("Train")){
                        tvMode.setText("Train");
                        spnDistance.setVisibility(View.GONE);
                        numberTrips.setVisibility(View.VISIBLE);
                        trips.setVisibility(View.VISIBLE);
                    }else if(key.equals("Metro")){
                        tvMode.setText("Metro");
                        spnDistance.setVisibility(View.GONE);
                        numberTrips.setVisibility(View.VISIBLE);
                        trips.setVisibility(View.VISIBLE);
                    }else if(key.equals("Taxi")){
                        tvMode.setText("Taxi");
                        spnDistance.setVisibility(View.GONE);
                        numberTrips.setVisibility(View.VISIBLE);
                        trips.setVisibility(View.VISIBLE);
                    }else if(key.equals("Plane")){
                        tvMode.setText("Plane");
                        trips.setVisibility(View.GONE);
                        numberTrips.setVisibility(View.VISIBLE);
                        spnDistance.setVisibility(View.GONE);

                        tvPlane.setVisibility(View.VISIBLE);
                        imgPlane.setVisibility(View.VISIBLE);
                    }
                }else {
                    tv2.setText("Select value input choice from \nbelow dropdown");
                    edtDistanceTravel.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
                    tvMode.setVisibility(View.GONE);
                    trips.setVisibility(View.GONE);
                    numberTrips.setVisibility(View.GONE);
                    spnDistance.setVisibility(View.GONE);
                    btnSubmit.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }});

        planeValues = new ArrayList<>();

        imgPlane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditPlaneValuesActivity.class);
                startActivity(intent);
            }
        });

        edtDistanceTravel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(flag){
                    if(!key.equals("Plane")){
                        int numberOfTrips = Integer.parseInt(edtNumTrips.getText().toString());
                        if(edtDistanceTravel.getText().toString().endsWith(",")){
                            edtDistanceTravel.setError("Incorrect number of values!");
                            edtDistanceTravel.requestFocus();
                            return;
                        }
                        String value = edtDistanceTravel.getText().toString();
                        String[] arr = value.split(",");
                        if(numberOfTrips != arr.length){
                            edtDistanceTravel.setError("Incorrect number of values!");
                            edtDistanceTravel.requestFocus();
                            return;
                        }
                    }
                }else {

                }
            }

            @Override
            public void afterTextChanged(Editable s) { }});

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag){
                    if(edtNumTrips.getText().toString().isEmpty()){
                        edtNumTrips.setError("Required!");
                        edtNumTrips.requestFocus();
                        return;
                    }
                    if(key.equals("Bus")){
                        if(edtDistanceTravel.getText().toString().trim().isEmpty()){
                            edtDistanceTravel.setError("Required!");
                            edtDistanceTravel.requestFocus();
                            return;
                        }
                        if(edtDistanceTravel.getText().toString().endsWith(",")){
                            edtDistanceTravel.setError("Incorrect number of values!");
                            edtDistanceTravel.requestFocus();
                            return;
                        }
                        int numberOfTrips = Integer.parseInt(edtNumTrips.getText().toString());
                        String values = edtDistanceTravel.getText().toString();
                        String[] arr = values.split(",");
                        double sum = 0;
                        if(numberOfTrips == arr.length){
                            for(int i=0;i<arr.length;i++){
                                sum = sum + Double.parseDouble(arr[i]);
                            }
                            EditTransportFootprintActivity.busValue = sum/numberOfTrips;
                            EditTransportFootprintActivity.busFlag = true;
                            Toast.makeText(EditTransportValuesActivity.this, "Values entered for bus", Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            edtDistanceTravel.setError("Incorrect number of values!");
                            edtDistanceTravel.requestFocus();
                        }
                    }else if(key.equals("Train")){
                        if(edtDistanceTravel.getText().toString().trim().isEmpty()){
                            edtDistanceTravel.setError("Required!");
                            edtDistanceTravel.requestFocus();
                            return;
                        }
                        if(edtDistanceTravel.getText().toString().endsWith(",")){
                            edtDistanceTravel.setError("Incorrect number of values!");
                            edtDistanceTravel.requestFocus();
                            return;
                        }
                        int numberOfTrips = Integer.parseInt(edtNumTrips.getText().toString());
                        String values = edtDistanceTravel.getText().toString();
                        String[] arr = values.split(",");
                        double sum = 0;
                        if(numberOfTrips == arr.length){
                            for(int i=0;i<arr.length;i++){
                                sum = sum + Double.parseDouble(arr[i]);
                            }
                            EditTransportFootprintActivity.trainValue = sum/numberOfTrips;
                            EditTransportFootprintActivity.trainFLag = true;
                            Toast.makeText(EditTransportValuesActivity.this, "Values entered for train", Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            edtDistanceTravel.setError("Entered incorrect number of values!");
                            edtDistanceTravel.requestFocus();
                        }
                    }else if(key.equals("Metro")){
                        if(edtDistanceTravel.getText().toString().trim().isEmpty()){
                            edtDistanceTravel.setError("Required!");
                            edtDistanceTravel.requestFocus();
                            return;
                        }
                        if(edtDistanceTravel.getText().toString().endsWith(",")){
                            edtDistanceTravel.setError("Incorrect number of values!");
                            edtDistanceTravel.requestFocus();
                            return;
                        }
                        int numberOfTrips = Integer.parseInt(edtNumTrips.getText().toString());
                        String values = edtDistanceTravel.getText().toString();
                        String[] arr = values.split(",");
                        double sum = 0;
                        if(numberOfTrips == arr.length){
                            for(int i=0;i<arr.length;i++){
                                sum = sum + Double.parseDouble(arr[i]);
                            }
                            EditTransportFootprintActivity.metroValue = sum/numberOfTrips;
                            EditTransportFootprintActivity.metroFlag = true;
                            Toast.makeText(EditTransportValuesActivity.this, "Values entered for metro", Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            edtDistanceTravel.setError("Incorrect number of values!");
                            edtDistanceTravel.requestFocus();
                        }
                    }else if(key.equals("Taxi")){
                        if(edtDistanceTravel.getText().toString().trim().isEmpty()){
                            edtDistanceTravel.setError("Required!");
                            edtDistanceTravel.requestFocus();
                            return;
                        }
                        if(edtDistanceTravel.getText().toString().endsWith(",")){
                            edtDistanceTravel.setError("Incorrect number of values!");
                            edtDistanceTravel.requestFocus();
                            return;
                        }
                        int numberOfTrips = Integer.parseInt(edtNumTrips.getText().toString());
                        String values = edtDistanceTravel.getText().toString();
                        String[] arr = values.split(",");
                        double sum = 0;
                        if(numberOfTrips == arr.length){
                            for(int i=0;i<arr.length;i++){
                                sum = sum + Double.parseDouble(arr[i]);
                            }
                            EditTransportFootprintActivity.taxiValue = sum/numberOfTrips;
                            EditTransportFootprintActivity.taxiFlag = true;
                            Toast.makeText(EditTransportValuesActivity.this, "Values entered for taxi", Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            edtDistanceTravel.setError("Incorrect number of values!");
                            edtDistanceTravel.requestFocus();
                        }
                    }else if(key.equals("Plane")){
                        int numberOfTrips = Integer.parseInt(edtNumTrips.getText().toString());
                        double sum = 0;
                        if (numberOfTrips == planeValues.size()){
                            for(double val : planeValues){
                                sum = sum+val;
                            }
                            EditTransportFootprintActivity.planeValue = sum/numberOfTrips;
                            EditTransportFootprintActivity.planeFlag = true;
                            Toast.makeText(EditTransportValuesActivity.this, "Values entered for plane", Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(EditTransportValuesActivity.this, "Please select all trip location points!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {

                    if(key.equals("Bus")){
                        if(spnDistance.getSelectedItem().toString().equals("Select distance in KM")){
                            Toast.makeText(EditTransportValuesActivity.this, "Please select distance!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        double value = Double.parseDouble(spnDistance.getSelectedItem().toString());
                        EditTransportFootprintActivity.busValue = value;
                        EditTransportFootprintActivity.busFlag = true;
                        Toast.makeText(EditTransportValuesActivity.this, "Values entered for bus", Toast.LENGTH_SHORT).show();
                        finish();
                    }else if(key.equals("Train")){
                        if(spnDistance.getSelectedItem().toString().equals("Select distance in KM")){
                            Toast.makeText(EditTransportValuesActivity.this, "Please select distance!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        double value = Double.parseDouble(spnDistance.getSelectedItem().toString());
                        EditTransportFootprintActivity.trainValue = value;
                        EditTransportFootprintActivity.trainFLag = true;
                        Toast.makeText(EditTransportValuesActivity.this, "Values entered for train", Toast.LENGTH_SHORT).show();
                        finish();
                    }else if(key.equals("Metro")){
                        if(spnDistance.getSelectedItem().toString().equals("Select distance in KM")){
                            Toast.makeText(EditTransportValuesActivity.this, "Please select distance!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        double value = Double.parseDouble(spnDistance.getSelectedItem().toString());
                        EditTransportFootprintActivity.metroValue = value;
                        EditTransportFootprintActivity.metroFlag = true;
                        Toast.makeText(EditTransportValuesActivity.this, "Values entered for metro", Toast.LENGTH_SHORT).show();
                        finish();
                    }else if(key.equals("Taxi")){
                        if(spnDistance.getSelectedItem().toString().equals("Select distance in KM")){
                            Toast.makeText(EditTransportValuesActivity.this, "Please select distance!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        double value = Double.parseDouble(spnDistance.getSelectedItem().toString());
                        EditTransportFootprintActivity.taxiValue = value;
                        EditTransportFootprintActivity.taxiFlag = true;
                        Toast.makeText(EditTransportValuesActivity.this, "Values entered for taxi", Toast.LENGTH_SHORT).show();
                        finish();
                    }else if(key.equals("Plane")){
                        EditTransportFootprintActivity.planeValue = planeValue;
                        EditTransportFootprintActivity.planeFlag = true;
                        Toast.makeText(EditTransportValuesActivity.this, "Values entered for plane", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });
    }
}
