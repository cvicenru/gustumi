package com.example.gustumi.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.gustumi.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity_layout);

        //Creo un hilo que se encargue de hacer la llamada al RegistrarseActivity después de 3 segundos
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, RegistrarseActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);//El splash durará 3 segundos

    }//fin onCreate

}//fin clase SplashScreenActivity