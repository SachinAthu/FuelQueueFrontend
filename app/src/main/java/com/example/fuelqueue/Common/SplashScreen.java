package com.example.fuelqueue.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.fuelqueue.R;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_TIME = 5000;
    TextView textView1, textView2, textView3;

    Animation animation1, animation2, animation3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);

        textView1 = findViewById(R.id.splashTextView1);
        textView2 = findViewById(R.id.splashTextView2);
        textView3 = findViewById(R.id.splashTextView3);

        animation1 = AnimationUtils.loadAnimation(this, R.anim.splash_screen_anim_1);
        animation2 = AnimationUtils.loadAnimation(this, R.anim.splash_screen_anim_2);
        animation3 = AnimationUtils.loadAnimation(this, R.anim.splash_screen_anim_3);

        textView1.setAnimation(animation1);
        textView2.setAnimation(animation2);
        textView3.setAnimation(animation3);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME);


    }
}