package com.app.footprintcalculator.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.footprintcalculator.R;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FootprintElementsActivity extends AppCompatActivity {

    MaterialCardView cardHome, cardTransport, cardFood;
    private final int TIME = 1000;
    public static TextView tvHomeScore, tvTransportScore, tvFoodScore;
    TextView tv;
    double totalscore;
    Button btnCalculateTotal;
    //Static variables
    public static double totalEmission = 0.0, elecEmission=0.0, gasEmission=0.0, oilEmission=0.0, wasteEmission=0.0, waterEmission=0.0;
    public static double vehicleEmission=0.0,busEmission=0.0, trainEmission=0.0,planeEmission=0.0, metroEmission=0.0, taxiEmission=0.0, totalTransEmission=0.0;
    public static double redMeatEmission=0.0, whiteMeatEmission=0.0, dairyEmissin=0.0, cerealsEmission=0.0, vegetablesEmission=0.0, fruitEmission=0.0,
            oilsEmission=0.0, snacksEmission=0.0, drinksEmission=0.0, totalFoodEmission=0.0;
    public static boolean homeFlag=false, transFlag=false, foodFLag=false;

    FloatingActionButton fabHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footprint_elements);

        tv = findViewById(R.id.txtInfo);
        tv.setVisibility(View.INVISIBLE);
        cardHome = findViewById(R.id.cardHome);
        cardTransport = findViewById(R.id.cardTransport);
        cardFood = findViewById(R.id.cardFood);
        tvHomeScore = findViewById(R.id.tvHomeScore);
        tvTransportScore = findViewById(R.id.tvTransportScore);
        tvFoodScore = findViewById(R.id.tvFoodScore);
        btnCalculateTotal = findViewById(R.id.btnCalculateTotal);

        btnCalculateTotal.setVisibility(View.GONE);
        tv = findViewById(R.id.txtInfo);
        //tv.setVisibility(View.GONE);
        tv.setSelected(true);

        final MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.button_);

        fabHome = findViewById(R.id.fab_home);
        YoYo.with(Techniques.StandUp).duration(2000).delay(2000).playOn(fabHome);
        //fabHome.setVisibility(View.GONE);
        fabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), UserActivity.class);
                startActivity(i);
            }
        });

        cardHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                animateCard(cardHome);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getApplicationContext(), CalculateHomeFootprintActivity.class));
//                        Intent i = new Intent(getApplicationContext(), MyFootprintActivity.class);
//                        startActivity(i);
                        //startActivity(new Intent(getApplicationContext(), MyFootprintActivity.class));
                    }
                }, TIME);



            }
        });
        cardTransport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateCard(cardTransport);
                mediaPlayer.start();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getApplicationContext(), CalculateTransportFootprintActivity.class));
//
                    }
                }, TIME);
                //startActivity(new Intent(getApplicationContext(), CalculateTransportFootprintActivity.class));

            }
        });
        cardFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateCard(cardFood);
                mediaPlayer.start();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getApplicationContext(), CalculateFoodFootprintActivity.class));
                        checkHomeScore();
//
                    }
                }, TIME);

            }
        });

        btnCalculateTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(FootprintElementsActivity.this, "Calculate total on click", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void animateCard(MaterialCardView mcv){
        YoYo.with(Techniques.Pulse)
                .duration(500)
                .repeat(0)
                .playOn(mcv);

    }

    @Override
    public void onBackPressed() {
        if(!homeFlag && !foodFLag && !transFlag){
            DatabaseReference db = FirebaseDatabase.getInstance().getReference("Footprints").child(UserActivity.id);
            db.removeValue();
            finish();
        }

//        if(!homeFlag ){
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("Info");
//            builder.setMessage("Home emission values are not calculated. Do you want to back? Created footprint and its other calculations also delete.");
//            builder.setPositiveButton("Yes Back", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    DatabaseReference db = FirebaseDatabase.getInstance().getReference("Footprints").child(UserActivity.id);
//                    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("HomeEmissions").child(UserActivity.id);
//                    DatabaseReference dbRef2 = FirebaseDatabase.getInstance().getReference("TransportEmissions").child(UserActivity.id);
//                    DatabaseReference dbRef3 = FirebaseDatabase.getInstance().getReference("FoodEmissions").child(UserActivity.id);
//
//                    db.removeValue();
//                    dbRef.removeValue();
//                    dbRef2.removeValue();
//                    dbRef3.removeValue();
//
//                    Toast.makeText(FootprintElementsActivity.this, "Footprint not completed", Toast.LENGTH_SHORT).show();
//                    dialog.dismiss();
//                    finish();
//                }
//            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            });
//            AlertDialog alertDialog = builder.create();
//            alertDialog.show();
//
//            return;
//        }else if(!transFlag){
//            Toast.makeText(this, "Please calculate home emmission", Toast.LENGTH_SHORT).show();
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("Info");
//            builder.setMessage("Transport emission values are not calculated. Do you want to back? Created footprint and its other calculations also delete.");
//            builder.setPositiveButton("Yes Back", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    DatabaseReference db = FirebaseDatabase.getInstance().getReference("Footprints").child(UserActivity.id);
//                    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("HomeEmissions").child(UserActivity.id);
//                    DatabaseReference dbRef2 = FirebaseDatabase.getInstance().getReference("TransportEmissions").child(UserActivity.id);
//                    DatabaseReference dbRef3 = FirebaseDatabase.getInstance().getReference("FoodEmissions").child(UserActivity.id);
//
//                    db.removeValue();
//                    dbRef.removeValue();
//                    dbRef2.removeValue();
//                    dbRef3.removeValue();
//
//                    Toast.makeText(FootprintElementsActivity.this, "Footprint not completed", Toast.LENGTH_SHORT).show();
//                    dialog.dismiss();
//                    finish();
//                }
//            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            });
//            AlertDialog alertDialog = builder.create();
//            alertDialog.show();
//            return;
//        }else if(!foodFLag){
//            Toast.makeText(this, "Please calculate home emmission", Toast.LENGTH_SHORT).show();
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("Info");
//            builder.setMessage("Food emission values are not calculated. Do you want to back? Created footprint and its other calculations also delete.");
//            builder.setPositiveButton("Yes Back", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    DatabaseReference db = FirebaseDatabase.getInstance().getReference("Footprints").child(UserActivity.id);
//                    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("HomeEmissions").child(UserActivity.id);
//                    DatabaseReference dbRef2 = FirebaseDatabase.getInstance().getReference("TransportEmissions").child(UserActivity.id);
//                    DatabaseReference dbRef3 = FirebaseDatabase.getInstance().getReference("FoodEmissions").child(UserActivity.id);
//
//                    db.removeValue();
//                    dbRef.removeValue();
//                    dbRef2.removeValue();
//                    dbRef3.removeValue();
//
//                    Toast.makeText(FootprintElementsActivity.this, "Footprint not completed", Toast.LENGTH_SHORT).show();
//                    dialog.dismiss();
//                    finish();
//                }
//            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            });
//            AlertDialog alertDialog = builder.create();
//            alertDialog.show();
//            return;
//        }
        //super.onBackPressed();
    }

    public void checkHomeScore(){
       if(!tvFoodScore.getText().toString().isEmpty()){
           tv.setVisibility(View.VISIBLE);
           YoYo.with(Techniques.FlipInX).duration(2000).repeat(1).playOn(tv);
           totalscore = totalEmission+totalFoodEmission+totalTransEmission;
           tv.setText("To see your total footprint head over to MyFootrpints on your dash to see a breakdown of your figures");


       }//End if
    }
}
