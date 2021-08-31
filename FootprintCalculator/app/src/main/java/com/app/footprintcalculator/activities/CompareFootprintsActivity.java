package com.app.footprintcalculator.activities;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

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
import java.util.List;

//Compare the footprints - Set all the data to the views
public class CompareFootprintsActivity extends BaseActivity {

    FootprintModel footprint1, footprint2;
    TextView tvFootprint1, tvFootprint2;
    TextView tvElectricityScore, tvGasScore, tvOilScore, tvWasteScore, tvWaterScore, tvTotalScore;
    TextView tvElectricityScore2, tvGasScore2, tvOilScore2, tvWasteScore2, tvWaterScore2, tvTotalScore2;
    TextView tvVehicleScore, tvBusScore, tvTrainScore, tvPlaneScore, tvMetroScore, tvTaxiScore, tvTotalTransportScore;
    TextView tvVehicleScore2, tvBusScore2, tvTrainScore2, tvPlaneScore2, tvMetroScore2, tvTaxiScore2, tvTotalTransportScore2;
    TextView tvRedMeatScore,tvWhiteMeatScore,tvDairyScore,tvCerealsScore,tvVegetablesScore,tvFruitsScore,tvOilsScore,tvSnacksScore,tvDrinksScore,tvTotalFoodScore;
    TextView tvRedMeatScore2,tvWhiteMeatScore2,tvDairyScore2,tvCerealsScore2,tvVegetablesScore2,tvFruitsScore2,tvOilsScore2,tvSnacksScore2,tvDrinksScore2,tvTotalFoodScore2;
    TextView f1, f2;
    DatabaseReference dbRefHome, dbRefTransport, dbRefFood;
    DatabaseReference dbRefHome2, dbRefTransport2, dbRefFood2;

    double footprint1Total=0;
    double footprint2Total=0;


    double home = 0, trans = 0, food=0;
    double home2 = 0, trans2 = 0, food2 =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_footprints);

        showProgressDialog();

        footprint1 = CovidPointsActivity.selectList.get(0);
        footprint2 = CovidPointsActivity.selectList.get(1);

        //Footprint 1
        dbRefHome = FirebaseDatabase.getInstance().getReference("HomeEmissions").child(footprint1.getId());
        dbRefFood = FirebaseDatabase.getInstance().getReference("FoodEmissions").child(footprint1.getId());
        dbRefTransport = FirebaseDatabase.getInstance().getReference("TransportEmissions").child(footprint1.getId());

        //Footprint 2
        dbRefHome2 = FirebaseDatabase.getInstance().getReference("HomeEmissions").child(footprint2.getId());
        dbRefFood2 = FirebaseDatabase.getInstance().getReference("FoodEmissions").child(footprint2.getId());
        dbRefTransport2 = FirebaseDatabase.getInstance().getReference("TransportEmissions").child(footprint2.getId());

        tvFootprint1 = findViewById(R.id.tvFootprint1);
        tvFootprint2 = findViewById(R.id.tvFootprint2);
        //Set the name and date
        tvFootprint1.setText(footprint1.getName()+"\n("+footprint1.getDate()+")");
        tvFootprint2.setText(footprint2.getName()+"\n("+footprint2.getDate()+")");

        tvElectricityScore = findViewById(R.id.tvElectricityScore);
        tvGasScore = findViewById(R.id.tvGasScore);
        tvOilScore = findViewById(R.id.tvOilScore);
        tvWasteScore = findViewById(R.id.tvWasteScore);
        tvWaterScore = findViewById(R.id.tvWaterScore);
        tvTotalScore = findViewById(R.id.tvTotalScore);

        tvElectricityScore2 = findViewById(R.id.tvElectricityScore2);
        tvGasScore2 = findViewById(R.id.tvGasScore2);
        tvOilScore2 = findViewById(R.id.tvOilScore2);
        tvWasteScore2 = findViewById(R.id.tvWasteScore2);
        tvWaterScore2 = findViewById(R.id.tvWaterScore2);
        tvTotalScore2 = findViewById(R.id.tvTotalScore2);

        tvVehicleScore = findViewById(R.id.tvVehicleScore);
        tvBusScore = findViewById(R.id.tvBusScore);
        tvTrainScore = findViewById(R.id.tvTrainScore);
        tvPlaneScore = findViewById(R.id.tvPlaneScore);
        tvMetroScore = findViewById(R.id.tvMetroScore);
        tvTaxiScore = findViewById(R.id.tvTaxiScore);
        tvTotalTransportScore = findViewById(R.id.tvTotalTransportScore);

        tvVehicleScore2 = findViewById(R.id.tvVehicleScore2);
        tvBusScore2 = findViewById(R.id.tvBusScore2);
        tvTrainScore2 = findViewById(R.id.tvTrainScore2);
        tvPlaneScore2 = findViewById(R.id.tvPlaneScore2);
        tvMetroScore2 = findViewById(R.id.tvMetroScore2);
        tvTaxiScore2 = findViewById(R.id.tvTaxiScore2);
        tvTotalTransportScore2 = findViewById(R.id.tvTotalTransportScore2);


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

        tvRedMeatScore2 = findViewById(R.id.tvRedMeatScore2);
        tvWhiteMeatScore2 = findViewById(R.id.tvWhiteMeatScore2);
        tvDairyScore2 = findViewById(R.id.tvDairyScore2);
        tvCerealsScore2 = findViewById(R.id.tvCerealsScore2);
        tvVegetablesScore2 = findViewById(R.id.tvVegetablesScore2);
        tvFruitsScore2 = findViewById(R.id.tvFruitsScore2);
        tvOilsScore2 = findViewById(R.id.tvOilsScore2);
        tvSnacksScore2 = findViewById(R.id.tvSnacksScore2);
        tvDrinksScore2 = findViewById(R.id.tvDrinksScore2);
        tvTotalFoodScore2 = findViewById(R.id.tvTotalFoodScore2);

        loadFootprint1();
        loadFootprint2();

        //Compare popup
         f1 = findViewById(R.id.FP1Total);
         f2 = findViewById(R.id.FP2Total);


        //f2.setText("Combined Total : " + new DecimalFormat("#.##").format(f2));

        f1.setVisibility(View.VISIBLE);
        f2.setVisibility(View.VISIBLE);


    }// End onCreate
    //set the values for the 1st prnt
    private void loadFootprint1() {
        dbRefHome.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                    HomeModel model = snapshot1.getValue(HomeModel.class);
                    tvElectricityScore.setText("Electricity : "+new DecimalFormat("#.##").format(model.getElcetricityEmission()));
                    tvGasScore.setText("Gas : "+new DecimalFormat("#.##").format(model.getGasEmission()));
                    tvOilScore.setText("Oil : "+new DecimalFormat("#.##").format(model.getOilEmission()));
                    tvWasteScore.setText("Waste : "+new DecimalFormat("#.##").format(model.getWasteEmission()));
                    tvWaterScore.setText("Water : "+new DecimalFormat("#.##").format(model.getWaterEmission()));
                    tvTotalScore.setText("Total : "+new DecimalFormat("#.##").format(model.getTotalEmission()));

                    footprint1Total = footprint1Total + model.getTotalEmission();

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
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    TransportModel model = snapshot1.getValue(TransportModel.class);
                    tvVehicleScore.setText("Vehicle : "+new DecimalFormat("#.##").format(model.getVehicleEmission()));
                    tvBusScore.setText("Bus : "+new DecimalFormat("#.##").format(model.getBusEmission()));
                    tvTrainScore.setText("Train : "+new DecimalFormat("#.##").format(model.getTrainEmission()));
                    tvPlaneScore.setText("Plane : "+new DecimalFormat("#.##").format(model.getPlaneEmission()));
                    tvMetroScore.setText("Metro : "+new DecimalFormat("#.##").format(model.getMetroEmission()));
                    tvTaxiScore.setText("Taxi : "+new DecimalFormat("#.##").format(model.getTaxiEmission()));
                    tvTotalTransportScore.setText("Total : "+new DecimalFormat("#.##").format(model.getTotalEmission()));

                    footprint1Total = footprint1Total + model.getTotalEmission();
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
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    FoodModel model = snapshot1.getValue(FoodModel.class);
                    tvRedMeatScore.setText("Red Meat : "+new DecimalFormat("#.##").format(model.getRedMeatEmission()));
                    tvWhiteMeatScore.setText("White Meat : "+new DecimalFormat("#.##").format(model.getWhiteMeatEmission()));
                    tvDairyScore.setText("Dairy : "+new DecimalFormat("#.##").format(model.getDairyEmission()));
                    tvCerealsScore.setText("Cereals : "+new DecimalFormat("#.##").format(model.getCerealsEmission()));
                    tvVegetablesScore.setText("Vegetables : "+new DecimalFormat("#.##").format(model.getVegetablesEmission()));
                    tvFruitsScore.setText("Fruit : "+new DecimalFormat("#.##").format(model.getFruitsEmission()));
                    tvOilsScore.setText("Oils : "+new DecimalFormat("#.##").format(model.getOilsEmission()));
                    tvSnacksScore.setText("Snacks : "+new DecimalFormat("#.##").format(model.getSnacksEmission()));
                    tvDrinksScore.setText("Drinks : "+new DecimalFormat("#.##").format(model.getDrinksEmission()));
                    tvTotalFoodScore.setText("Total : "+new DecimalFormat("#.##").format(model.getTotalEmission()));

                    footprint1Total = footprint1Total + model.getTotalEmission();
                    f1.setText("Combined Total : " + new DecimalFormat("#.##").format(footprint1Total));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                hideProgressDialog();
            }
        });

    }
    //Load in 2nd print
    private void loadFootprint2() {
        dbRefHome2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    HomeModel model = snapshot1.getValue(HomeModel.class);
                    tvElectricityScore2.setText("Electricity : "+new DecimalFormat("#.##").format(model.getElcetricityEmission()));
                    tvGasScore2.setText("Gas : "+new DecimalFormat("#.##").format(model.getGasEmission()));
                    tvOilScore2.setText("Oil : "+new DecimalFormat("#.##").format(model.getOilEmission()));
                    tvWasteScore2.setText("Waste : "+new DecimalFormat("#.##").format(model.getWasteEmission()));
                    tvWaterScore2.setText("Water : "+new DecimalFormat("#.##").format(model.getWaterEmission()));
                    tvTotalScore2.setText("Total : "+new DecimalFormat("#.##").format(model.getTotalEmission()));

                    footprint2Total = footprint2Total + model.getTotalEmission();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                hideProgressDialog();
            }
        });
        dbRefTransport2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    TransportModel model = snapshot1.getValue(TransportModel.class);
                    tvVehicleScore2.setText("Vehicle : "+new DecimalFormat("#.##").format(model.getVehicleEmission()));
                    tvBusScore2.setText("Bus : "+new DecimalFormat("#.##").format(model.getBusEmission()));
                    tvTrainScore2.setText("Train : "+new DecimalFormat("#.##").format(model.getTrainEmission()));
                    tvPlaneScore2.setText("Plane : "+new DecimalFormat("#.##").format(model.getPlaneEmission()));
                    tvMetroScore2.setText("Metro : "+new DecimalFormat("#.##").format(model.getMetroEmission()));
                    tvTaxiScore2.setText("Taxi : "+new DecimalFormat("#.##").format(model.getTaxiEmission()));
                    tvTotalTransportScore2.setText("Total : "+new DecimalFormat("#.##").format(model.getTotalEmission()));

                    footprint2Total = footprint2Total + model.getTotalEmission();;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                hideProgressDialog();
            }
        });
        dbRefFood2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    FoodModel model = snapshot1.getValue(FoodModel.class);
                    tvRedMeatScore2.setText("Red Meat : "+new DecimalFormat("#.##").format(model.getRedMeatEmission()));
                    tvWhiteMeatScore2.setText("White Meat : "+new DecimalFormat("#.##").format(model.getWhiteMeatEmission()));
                    tvDairyScore2.setText("Dairy : "+new DecimalFormat("#.##").format(model.getDairyEmission()));
                    tvCerealsScore2.setText("Cereals : "+new DecimalFormat("#.##").format(model.getCerealsEmission()));
                    tvVegetablesScore2.setText("Vegetables : "+new DecimalFormat("#.##").format(model.getVegetablesEmission()));
                    tvFruitsScore2.setText("Fruit : "+new DecimalFormat("#.##").format(model.getFruitsEmission()));
                    tvOilsScore2.setText("Oils : "+new DecimalFormat("#.##").format(model.getOilsEmission()));
                    tvSnacksScore2.setText("Snacks : "+new DecimalFormat("#.##").format(model.getSnacksEmission()));
                    tvDrinksScore2.setText("Drinks : "+new DecimalFormat("#.##").format(model.getDrinksEmission()));
                    tvTotalFoodScore2.setText("Total : "+new DecimalFormat("#.##").format(model.getTotalEmission()));

                    footprint2Total = footprint2Total + model.getTotalEmission();
                    f2.setText("Combined Total : " + new DecimalFormat("#.##").format(footprint2Total));
                }

                hideProgressDialog();

                if (footprint2Total>footprint1Total){

                    notifyUserCalc("Looks like your emissions have increased!\nWork on reducing your footpint...");
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sad_trumpet);
                    mediaPlayer.start();
                }
                else{
                    notifyUserCalc("Congrats your emissions descreased!\nKeep up the good work");
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.applause6);
                    mediaPlayer.start();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                hideProgressDialog();
            }
        });
    }

    private void notifyUserCalc(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Comparisson");
        builder.setIcon(R.drawable.ic_compare_icon);
        builder.setMessage(message);
        builder.setPositiveButton("Got it", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

//    public void Compare(){
//        String f1 = tvTotalScore.getText().toString() + tvTotalFoodScore.getText().toString() + tvTotalTransportScore.getText().toString();
//        TextView fp1 = findViewById(R.id.FP1Total);
//
//
//    }

    @Override
    public void onBackPressed() {
        CovidPointsActivity.selectList.clear();
        finish();
        super.onBackPressed();

    }
}
