package com.app.footprintcalculator.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.app.footprintcalculator.HttpRequest;
import com.app.footprintcalculator.MainActivity;
import com.app.footprintcalculator.MessageReceiver;
import com.app.footprintcalculator.R;
import com.app.footprintcalculator.models.FootprintModel;
import com.app.footprintcalculator.models.HomeLocationModel;
import com.app.footprintcalculator.models.UsersModel;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
// Dashboard
public class UserActivity extends BaseActivity {

    String API = "8118ed6ee68db2debfaaa5a44c832918";
    DatabaseReference databaseReference, dbRef, dbRef2;
    MaterialCardView calc, myfoot, comp, comGraph, journey, weather, profile;
    Button btnCalculateFootprint, btnMyFootprint, btnCompareFootprints,
            btnCompareOnGraph, btnJourneyPlanner, btnLogout, btnHomeWeather, btnProfile;
    AlertDialog alertDialog;
    public static String id, userName="", userPhone, password, url;
    public static HomeLocationModel model;
    String userId;
    boolean flag = false, keyLaundry, keyEnergy;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPrefLaundry, sharedPrefEnergy, sharedPrefScore;
    SharedPreferences.Editor editor2, editor3, editor4;
    boolean laundry, energy;
    TextView scroll, dashName, dash_label, full_name;
    private  final int TIME = 2000;
    MaterialCardView news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        animate();

        databaseReference = FirebaseDatabase.getInstance().getReference("Footprints"); //reference to footprints
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dbRef = FirebaseDatabase.getInstance().getReference("UsersData").child(userId); //Reference to users data
        dbRef2 = FirebaseDatabase.getInstance().getReference("HomeLocation").child(userId); //users home locations in DB

        sharedPreferences = getSharedPreferences("notification",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        //local store of small data for opt in features
        sharedPrefLaundry = getSharedPreferences("Laundry",MODE_PRIVATE);
        editor2 = sharedPrefLaundry.edit();
        sharedPrefEnergy = getSharedPreferences("Energy",MODE_PRIVATE);
        editor3 = sharedPrefEnergy.edit();
        sharedPrefScore = getSharedPreferences("Scores",MODE_PRIVATE);
        editor4 = sharedPrefScore.edit();

        btnCalculateFootprint = findViewById(R.id.btnCalculateFootprint);
        btnMyFootprint = findViewById(R.id.btnMyFootprint);
        btnCompareFootprints = findViewById(R.id.btnCompareFootprints);
        btnCompareOnGraph = findViewById(R.id.btnCompareOnGraph);
        btnJourneyPlanner = findViewById(R.id.btnJourneyPlanner);
        btnHomeWeather = findViewById(R.id.btnHomeWeather);
        btnProfile = findViewById(R.id.btnProfile);
        btnLogout = findViewById(R.id.btnLogout);
        scroll = findViewById(R.id.scrollText);
        scroll.setSelected(true);
        news = findViewById(R.id.news_card);
        full_name = findViewById(R.id.full_name);

        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserActivity.this, NewsActivity.class);
                startActivity(i);
            }
        });

        btnCalculateFootprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calc = findViewById(R.id.cardCalc);
                animateCard(calc);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        //Show the pop up diaog for the print name
                        AlertDialog.Builder dailogBuilder = new AlertDialog.Builder(UserActivity.this);
                        LayoutInflater inflater = getLayoutInflater();
                        final View dialogView = inflater.inflate(R.layout.dialog_layout2, null);
                        dailogBuilder.setView(dialogView);

                        final EditText edtName = dialogView.findViewById(R.id.edtName);
                        final Button btnSub = dialogView.findViewById(R.id.btnSub);

                        btnSub.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String name = edtName.getText().toString().trim();
                                if(name.isEmpty()){
                                    edtName.setError("Required!");
                                    edtName.requestFocus();
                                    return;
                                }
                                id = databaseReference.push().getKey();
                                String cDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                                String cTime = new SimpleDateFormat("hh:mm:ss a", Locale.getDefault()).format(new Date());
                                FootprintModel model = new FootprintModel(id,name,cDate,cTime,userId);
                                databaseReference.child(id).setValue(model);
                                Toast.makeText(UserActivity.this, "Created successfully, Continue with calculations", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                                startActivity(new Intent(getApplicationContext(), FootprintElementsActivity.class));
                            }
                        });
                        alertDialog = dailogBuilder.create();
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                    }
                }, TIME);
//                AlertDialog.Builder dailogBuilder = new AlertDialog.Builder(Dashboard.this);
//                LayoutInflater inflater = getLayoutInflater();
//                final View dialogView = inflater.inflate(R.layout.dialog_layout2, null);
//                dailogBuilder.setView(dialogView);

//                final EditText edtName = dialogView.findViewById(R.id.edtName);
//                final Button btnSub = dialogView.findViewById(R.id.btnSub);

//                btnSub.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        String name = edtName.getText().toString().trim();
//                        if(name.isEmpty()){
//                            edtName.setError("Required!");
//                            edtName.requestFocus();
//                            return;
//                        }
//                        id = databaseReference.push().getKey();
//                        String cDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
//                        String cTime = new SimpleDateFormat("hh:mm:ss a", Locale.getDefault()).format(new Date());
//                        FootprintModel model = new FootprintModel(id,name,cDate,cTime,userId);
//                        databaseReference.child(id).setValue(model);
//                        Toast.makeText(Dashboard.this, "Created successfully, Please do your calculations now", Toast.LENGTH_SHORT).show();
//                        alertDialog.dismiss();
//                        startActivity(new Intent(getApplicationContext(), FootprintElementsActivity.class));
//                    }
//                });
//                alertDialog = dailogBuilder.create();
//                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                alertDialog.setCanceledOnTouchOutside(false);
//                alertDialog.show();
            }
        });

        btnMyFootprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myfoot = findViewById(R.id.cardFoot);
                animateCard(myfoot);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent i = new Intent(getApplicationContext(), MyFootprintActivity.class);
                        startActivity(i);
                        //startActivity(new Intent(getApplicationContext(), MyFootprintActivity.class));
                    }
                }, TIME);

            }
        });
        btnCompareFootprints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comp = findViewById(R.id.cardComp);
                animateCard(comp);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent i = new Intent(getApplicationContext(), CovidPointsActivity.class);
                        startActivity(i);
                        //startActivity(new Intent(getApplicationContext(), MyFootprintActivity.class));
                    }
                }, TIME);
            }
        });
        btnCompareOnGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comGraph = findViewById(R.id.cardCompG);
                animateCard(comGraph);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent i = new Intent(getApplicationContext(), ComparisonGraphActivity.class);
                        startActivity(i);
                        //startActivity(new Intent(getApplicationContext(), MyFootprintActivity.class));
                    }
                }, TIME);
            }
        });
        btnJourneyPlanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                journey = findViewById(R.id.cardJourney);
                animateCard(journey);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent i = new Intent(getApplicationContext(), JourneyPlannerActivity.class);
                        startActivity(i);
                        //startActivity(new Intent(getApplicationContext(), MyFootprintActivity.class));
                    }
                }, TIME);
            }
        });
        btnHomeWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag){
                    weather = findViewById(R.id.cardWeather);
                    animateCard(weather);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                            //startActivity(new Intent(getApplicationContext(), MyFootprintActivity.class));
                        }
                    }, TIME);

                }else {
                    Toast.makeText(UserActivity.this, "Home location not set!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile = findViewById(R.id.cardprofile);
                animateCard(profile);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(i);
                    }
                }, TIME);

            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor2.clear();
                editor2.apply();
                editor3.clear();
                editor3.apply();
                editor4.clear();
                editor4.apply();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), UserLoginActivity.class));
                finish();
                Toast.makeText(getApplicationContext(), "Sign out", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        //Get laundry and energy shared pref
        keyLaundry = sharedPreferences.getBoolean("keyLaundry",false);
        keyEnergy = sharedPreferences.getBoolean("keyEnergy",false);

        laundry = sharedPrefLaundry.getBoolean("notify",false);
        energy = sharedPrefEnergy.getBoolean("notify",false);

        if(energy){
            String time = sharedPrefEnergy.getString("time","");
            String cTime = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());

            String[] arr1 = time.split(" ");
            String[] arr2 = arr1[0].split("-");

            String[] arr3 = cTime.split(":");

            if(arr3[0].substring(1).equals(arr2[0]) || arr3[0].substring(1).equals(arr2[1])){
                if(!keyEnergy){
                    Toast.makeText(this, "true", Toast.LENGTH_SHORT).show();
                    notifyUserEnergySave();
                }
            }
        }

        showProgressDialog();
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UsersModel model = snapshot.getValue(UsersModel.class);
                userName = model.getFullName();
                userPhone = model.getPhone();
                password = model.getPassword();
                url = model.getPicUrl();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){ }

                full_name.setText(userName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                hideProgressDialog();
            }
        });

        if(!keyLaundry && laundry){
            dbRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.getChildrenCount()>0){
                        model = snapshot.getValue(HomeLocationModel.class);
                        hideProgressDialog();
                        flag = true;
                        for(DataSnapshot snapshot1 : snapshot.getChildren()){ }
                        notifyUser();
                    }
                    hideProgressDialog();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    hideProgressDialog();
                }
            });
        }else {
            hideProgressDialog();
        }
    }

    private void notifyUserEnergySave() {
        Intent intent = new Intent(getApplicationContext(), MessageReceiver.class);
        intent.setAction("com.example.farmersapp.SEND_MESSAGE");
        intent.putExtra("message", "Don't forget to turn off unused electrical devices");
        intent.putExtra("title", "Energy Saver ");
        sendBroadcast(intent);

        editor.putBoolean("keyEnergy",true);
        editor.apply();

        int energyScore = sharedPrefScore.getInt("energyScore",0);
        energyScore = energyScore+1;
        editor4.remove("energyScore");
        editor4.putInt("energyScore",energyScore);
        editor4.apply();
    }

    private void notifyUser() {
        new weatherTask().execute();
    }

    class weatherTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {
            String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?lat=" + model.getLatitude() + "&lon=" + model.getLongitude() + "&units=metric&appid=" + API);
            //String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?q=" + CITY + "&units=metric&appid=" + API);
            return response;
        }

        @Override
        protected void onPostExecute(String result) {


            try {
                JSONObject jsonObj = new JSONObject(result);
                JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);

                String weatherDescription = weather.getString("description");
                String details = weatherDescription.toLowerCase();

                checkWeather(details);

//                if(details.contains("thunderstorm") || details.contains("rain") || details.contains("drizzle") || details.contains("high winds") || details.contains("thunderstorms")
//                ||details.contains("blizzards") || details.contains("shower") || details.contains("snow") || details.contains("drizzle rain") || details.contains("shower rain and drizzle")
//                        || details.contains("broken clouds")){
//                    Intent intent = new Intent(getApplicationContext(), MessageReceiver.class);
//                    intent.setAction("com.example.farmersapp.SEND_MESSAGE");
//                    intent.putExtra("message", "Whoops... Looks like rain ted, best wait for better weather");
//                    intent.putExtra("title", "Weather Warning - " + details);
//                    sendBroadcast(intent);
//
//                    editor.putBoolean("keyLaundry",true);
//                    editor.apply();
//
//                    int laundryScore = sharedPrefScore.getInt("laundryScore",0);
//                    laundryScore = laundryScore+1;
//                    editor4.remove("laundryScore");
//                    editor4.putInt("laundryScore",laundryScore);
//                    editor4.apply();
//                }else if(details.contains("clear sky")|| details.contains("sun") ||details.contains("sunny") ||details.contains("scattered clouds") ||details.contains("clouds") ||
//                        details.contains("cloudy") ||details.contains("few clouds"))
//                {
//                    Intent intent = new Intent(getApplicationContext(), MessageReceiver.class);
//                    intent.setAction("com.example.farmersapp.SEND_MESSAGE");
//                    intent.putExtra("message", "Weather is looking good to put on a wash, ditch the dryer!");
//                    intent.putExtra("title", "Laundry Assistant");
//                    sendBroadcast(intent);
//
//                    editor.putBoolean("keyLaundry",true);
//                    editor.apply();
//
//                    int laundryScore = sharedPrefScore.getInt("laundryScore",0);
//                    laundryScore = laundryScore+2;
//                    editor4.remove("laundryScore");
//                    editor4.putInt("laundryScore",laundryScore);
//                    editor4.apply();
//                }


            } catch (JSONException e) {
                findViewById(R.id.loader).setVisibility(View.GONE);
                findViewById(R.id.errorText).setVisibility(View.VISIBLE);
            }

        }
    }


    public void checkWeather(String d){
        String details = d;
        //rain
        if (details.contains("rain") || details.contains("drizzle") ||  details.contains("shower")
                || details.contains("shower rain and drizzle") || details.contains("broken clouds")){
            Intent intent = new Intent(getApplicationContext(), MessageReceiver.class);
            intent.setAction("com.example.farmersapp.SEND_MESSAGE");
            intent.putExtra("message", "Whoops... Looks like rain ted, best wait for better weather\nWeather Condition : " + details);
            intent.putExtra("title", "Weather Warning - " + details);
            sendBroadcast(intent);

            editor.putBoolean("keyLaundry",true);
            editor.apply();

            int laundryScore = sharedPrefScore.getInt("laundryScore",0);
            laundryScore = laundryScore+1;
            editor4.remove("laundryScore");
            editor4.putInt("laundryScore",laundryScore);
            editor4.apply();
        }
        else if (details.contains("clear sky")|| details.contains("sun") ||details.contains("sunny") ||details.contains("scattered clouds") ||details.contains("clouds") ||
                details.contains("cloudy") ||details.contains("few clouds")){

            Intent intent = new Intent(getApplicationContext(), MessageReceiver.class);
            intent.setAction("com.example.farmersapp.SEND_MESSAGE");
            intent.putExtra("message", "Weather is looking good to put on a wash, ditch the dryer!\n Weather Condition : " + details);
            intent.putExtra("title", "Laundry Assistant");
            sendBroadcast(intent);

            editor.putBoolean("keyLaundry",true);
            editor.apply();

            int laundryScore = sharedPrefScore.getInt("laundryScore",0);
            laundryScore = laundryScore+2;
            editor4.remove("laundryScore");
            editor4.putInt("laundryScore",laundryScore);
            editor4.apply();
        }
    }



    public void animate(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        scroll = findViewById(R.id.scrollText);
        dashName = findViewById(R.id.full_name);
        //dashName.setText(firebaseUser.getDisplayName());
        dash_label = findViewById(R.id.dash_label);
        scroll.setSelected(true);

        YoYo.with(Techniques.BounceIn)
                .duration(5000)
                .repeat(2)
                .playOn(dashName);

        YoYo.with(Techniques.Flash)
                .duration(5000)
                .repeat(2)
                .playOn(dash_label);
    }

    public void animateCard(MaterialCardView mcv){
        YoYo.with(Techniques.Pulse)
                .duration(1000)
                .repeat(1)
                .playOn(mcv);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        editor.clear();
        editor.apply();
    }
}