package com.example.healthtracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MentalHealthResultActivity extends AppCompatActivity {

    TextView resMentalHealth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mental_health_result);

        resMentalHealth=findViewById(R.id.resMentalHealth);
        Intent intentThatStartedThis = getIntent();
        String result= intentThatStartedThis.getStringExtra("3");
        resMentalHealth.setText(result);
    }
}