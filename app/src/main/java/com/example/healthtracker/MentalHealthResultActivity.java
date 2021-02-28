package com.example.healthtracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MentalHealthResultActivity extends AppCompatActivity {

    TextView resMentalHealth;
    TextView mentalStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mental_health_result);

        resMentalHealth=findViewById(R.id.resMentalHealth);
        Intent intentThatStartedThis = getIntent();
        String result= intentThatStartedThis.getStringExtra("result");
        resMentalHealth.setText(result);
//        mentalStatus = findViewById(R.id.mentalstatus);
//        Button consultpsy = (Button)findViewById(R.id.consultpsych);
//        if(result.equals("1"))
//        {
//            mentalStatus.setText("We advise you to consult a psychiatrist to improve your mental health");
//            consultpsy.setVisibility(View.VISIBLE);
//        }
//        else if(result.equals("2"))
//        {
//            mentalStatus.setText("Your mental health is average");
//        }
//        else if(result.equals("3"))
//        {
//            mentalStatus.setText("You are doing good!");
//        }
    }
}