package com.darthorg.bozo;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.darthorg.bozo.view.Inicio;

public class Splash extends AppCompatActivity {

    private final int SPLASH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



    new Handler().postDelayed(new Runnable() {
        public void run() {
            startActivity(new Intent(Splash.this,Inicio.class));
            finish();
        }
    }, SPLASH);

    }
}
