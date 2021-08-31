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
import com.app.footprintcalculator.models.FoodModel;
import com.app.footprintcalculator.models.HomeModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//Food Footprint
public class CalculateFoodFootprintActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    EditText edtRedMeat, edtWhiteMeat, edtDairy, edtCereals, edtVegetables, edtFruit, edtOils, edtSnacks, edtDrinks;
    Button btnCalculate;
    double redMeat, whiteMeat, dairy, cereals, vegetables, fruit, oils, snacks, drinks;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_food_footprint);

        databaseReference = FirebaseDatabase.getInstance().getReference("FoodEmissions").child(UserActivity.id);

        edtRedMeat = findViewById(R.id.edtRedMeat);
        edtWhiteMeat = findViewById(R.id.edtWhiteMeat);
        edtDairy = findViewById(R.id.edtDairy);
        edtCereals = findViewById(R.id.edtCereals);
        edtVegetables = findViewById(R.id.edtVegetables);
        edtFruit = findViewById(R.id.edtFruit);
        edtOils = findViewById(R.id.edtOils);
        edtSnacks = findViewById(R.id.edtSnacks);
        edtDrinks = findViewById(R.id.edtDrinks);
        btnCalculate = findViewById(R.id.btnCalculate);

//        edtRedMeat.setText(String.format("%.1f",FootprintElementsActivity.redMeatEmission));
//        edtWhiteMeat.setText(String.format("%.1f",FootprintElementsActivity.whiteMeatEmission));
//        edtDairy.setText(String.format("%.1f",FootprintElementsActivity.dairyEmissin));
//        edtCereals.setText(String.format("%.1f",FootprintElementsActivity.cerealsEmission));
//        edtVegetables.setText(String.format("%.1f",FootprintElementsActivity.vegetablesEmission));
//        edtFruit.setText(String.format("%.1f",FootprintElementsActivity.fruitEmission));
//        edtOils.setText(String.format("%.1f",FootprintElementsActivity.oilsEmission));
//        edtSnacks.setText(String.format("%.1f",FootprintElementsActivity.snacksEmission));
//        edtDrinks.setText(String.format("%.1f",FootprintElementsActivity.drinksEmission));

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Check if they are empty to default to 0
                if(edtRedMeat.getText().toString().isEmpty()){
                    redMeat = 0;
                }else {
                    redMeat = Double.parseDouble(edtRedMeat.getText().toString());
                }
                if(edtWhiteMeat.getText().toString().isEmpty()){
                    whiteMeat = 0;
                }else {
                    whiteMeat = Double.parseDouble(edtWhiteMeat.getText().toString());
                }
                if(edtDairy.getText().toString().isEmpty()){
                    dairy = 0;
                }else {
                    dairy = Double.parseDouble(edtDairy.getText().toString());
                }
                if(edtCereals.getText().toString().isEmpty()){
                    cereals = 0;
                }else {
                    cereals = Double.parseDouble(edtCereals.getText().toString());
                }
                if(edtVegetables.getText().toString().isEmpty()){
                    vegetables = 0;
                }else {
                    vegetables = Double.parseDouble(edtVegetables.getText().toString());
                }
                if(edtFruit.getText().toString().isEmpty()){
                    fruit = 0;
                }else {
                    fruit = Double.parseDouble(edtFruit.getText().toString());
                }
                if(edtOils.getText().toString().isEmpty()){
                    oils = 0;
                }else {
                    oils = Double.parseDouble(edtOils.getText().toString());
                }
                if(edtSnacks.getText().toString().isEmpty()){
                    snacks = 0;
                }else {
                    snacks = Double.parseDouble(edtSnacks.getText().toString());
                }
                if(edtDrinks.getText().toString().isEmpty()){
                    drinks = 0;
                }else {
                    drinks = Double.parseDouble(edtDrinks.getText().toString());
                }

                //*****************************************
                //Emission Factors Food
                //**************************************
                FootprintElementsActivity.redMeatEmission = ((redMeat*365)* 0.006)/1000;
                FootprintElementsActivity.whiteMeatEmission = ((whiteMeat*365)* 0.006)/1000;
                FootprintElementsActivity.dairyEmissin = ((dairy*365)*0.006)/1000;
                FootprintElementsActivity.cerealsEmission = ((cereals*365)* 0.006)/1000;
                FootprintElementsActivity.vegetablesEmission = ((vegetables*365)* 0.006)/1000;
                FootprintElementsActivity.fruitEmission = ((fruit*365)* 0.006)/1000;
                FootprintElementsActivity.oilsEmission = ((oils*365)* 0.006)/1000;
                FootprintElementsActivity.snacksEmission = ((snacks*365)* 0.006)/1000;
                FootprintElementsActivity.drinksEmission = ((drinks*365)* 0.006)/1000;

                FootprintElementsActivity.totalFoodEmission = FootprintElementsActivity.redMeatEmission+FootprintElementsActivity.whiteMeatEmission+
                        FootprintElementsActivity.dairyEmissin+FootprintElementsActivity.cerealsEmission+FootprintElementsActivity.vegetablesEmission+
                        FootprintElementsActivity.fruitEmission+FootprintElementsActivity.oilsEmission+FootprintElementsActivity.snacksEmission+FootprintElementsActivity.drinksEmission;

                //Show save dialog
                AlertDialog.Builder dailogBuilder = new AlertDialog.Builder(CalculateFoodFootprintActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.dialog_layout3, null);
                dailogBuilder.setView(dialogView);

                final TextView tvRedMeatScore = dialogView.findViewById(R.id.tvRedMeatScore);
                final TextView tvWhiteMeatScore = dialogView.findViewById(R.id.tvWhiteMeatScore);
                final TextView tvDairyScore = dialogView.findViewById(R.id.tvDairyScore);
                final TextView tvCerealsScore = dialogView.findViewById(R.id.tvCerealsScore);
                final TextView tvVegetablesScore = dialogView.findViewById(R.id.tvVegetablesScore);
                final TextView tvFruitsScore = dialogView.findViewById(R.id.tvFruitsScore);
                final TextView tvOilsScore = dialogView.findViewById(R.id.tvOilsScore);
                final TextView tvSnacksScore = dialogView.findViewById(R.id.tvSnacksScore);
                final TextView tvDrinksScore = dialogView.findViewById(R.id.tvDrinksScore);
                final TextView tvTotalScore = dialogView.findViewById(R.id.tvTotalScore);
                final Button btnSub = dialogView.findViewById(R.id.btnSub);

                tvRedMeatScore.setText("Red Meat Emission : "+String.format("%.2f",FootprintElementsActivity.redMeatEmission));
                tvWhiteMeatScore.setText("White Meat Emission : "+String.format("%.2f",FootprintElementsActivity.whiteMeatEmission));
                tvDairyScore.setText("Dairy Emission : "+String.format("%.2f",FootprintElementsActivity.dairyEmissin));
                tvCerealsScore.setText("Cereals Emission : "+String.format("%.2f",FootprintElementsActivity.cerealsEmission));
                tvVegetablesScore.setText("Vegetables Emission : "+String.format("%.2f",FootprintElementsActivity.vegetablesEmission));
                tvFruitsScore.setText("Fruits Emission : "+String.format("%.2f",FootprintElementsActivity.fruitEmission));
                tvOilsScore.setText("Oils Emission : "+String.format("%.2f",FootprintElementsActivity.oilsEmission));
                tvSnacksScore.setText("Snacks Emission : "+String.format("%.2f",FootprintElementsActivity.snacksEmission));
                tvDrinksScore.setText("Drinks Emission : "+String.format("%.2f",FootprintElementsActivity.drinksEmission));
                tvTotalScore.setText("Total Score : "+String.format("%.2f",FootprintElementsActivity.totalFoodEmission));

                btnSub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String id = UserActivity.id;
                        FoodModel model = new FoodModel(id,FootprintElementsActivity.redMeatEmission,FootprintElementsActivity.whiteMeatEmission,
                                FootprintElementsActivity.dairyEmissin,FootprintElementsActivity.cerealsEmission,FootprintElementsActivity.vegetablesEmission,
                                FootprintElementsActivity.fruitEmission,FootprintElementsActivity.oilsEmission,FootprintElementsActivity.snacksEmission,
                                FootprintElementsActivity.drinksEmission,FootprintElementsActivity.totalFoodEmission);
                        databaseReference.child(id).setValue(model);
                        Toast.makeText(getApplicationContext(), "Data saved successfully", Toast.LENGTH_SHORT).show();
                        FootprintElementsActivity.tvFoodScore.setText("Score : "+String.format("%.2f",FootprintElementsActivity.totalFoodEmission));
                        FootprintElementsActivity.foodFLag=true;
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


