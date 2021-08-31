package com.app.footprintcalculator.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextPaint;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.footprintcalculator.R;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

//Startup screen that shown at very first for few seconds
public class SplashActivity extends AppCompatActivity {
//    View line1, line2, line3, line4, line5, line6;
    LinearLayout linearLayout;
    TextView money, earth, energy, logotxt;
    ImageView imageView, hero;
    Animation top, btm;
    public static final int SPLASH_TIME_OUT = 7000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        init();

        //Run Backroud thread for 27 secnds to hold on first screen for 27 screen.
        Thread obj = new Thread() {
            public void run() {
                try {
                    sleep(SPLASH_TIME_OUT);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        //After 28 seconds start loin screen using Intent
                        Intent intent = new Intent(getApplicationContext(), UserLoginActivity.class);
                        startActivity(intent);
                        finish();

                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        };obj.start();

    }

    private void init() {
        money = findViewById(R.id.money);
        earth = findViewById(R.id.earth);
        energy = findViewById(R.id.energy);
        logotxt = findViewById(R.id.logotxt);
        imageView = findViewById(R.id.logo_pic);
        hero = findViewById(R.id.hero);

        top = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        btm = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        colours(money);
        colours(earth);
        colours(energy);
        colours(logotxt);

        money.setAnimation(top);
        energy.setAnimation(top);
        earth.setAnimation(top);

        imageView.setAnimation(btm);
        logotxt.setAnimation(btm);
        hero.setAnimation(btm);

        YoYo.with(Techniques.BounceIn)
                .duration(4000)
                .repeat(2)
                .playOn(imageView);

        //Animate first screen with animations..
//        top = AnimationUtils.loadAnimation(this, R.anim.top_anim);
//        middle = AnimationUtils.loadAnimation(this, R.anim.middle_anim);
//        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);

//        line1 = findViewById(R.id.line1);
//        line2 = findViewById(R.id.line2);
//        line3 = findViewById(R.id.line3);
//        line4 = findViewById(R.id.line4);
//        line5 = findViewById(R.id.line5);
//        line6 = findViewById(R.id.line6);
//        imageView = findViewById(R.id.logo);
//        linearLayout = findViewById(R.id.linear1);

//        line1.setAnimation(top);
//        line2.setAnimation(top);
//        line3.setAnimation(top);
//        line4.setAnimation(top);
//        line5.setAnimation(top);
//        line6.setAnimation(top);
//        imageView.setAnimation(middle);
//        linearLayout.setAnimation(fadeIn);


    }

    private void colours(TextView t){
        TextPaint paint = t.getPaint();
        float width = paint.measureText(t.getText().toString());

        Shader textShader = new LinearGradient(0, 0, width, t.getTextSize(),
                new int[]{
                        Color.parseColor("#32a852"),
                        Color.parseColor("#32a852"),
                        Color.parseColor("#32a8a6"),
                        Color.parseColor("#32a8a6"),
                        Color.parseColor("#327fa8"),
                        Color.parseColor("#327fa8"),
                }, null, Shader.TileMode.CLAMP);
        t.getPaint().setShader(textShader);

    }

}