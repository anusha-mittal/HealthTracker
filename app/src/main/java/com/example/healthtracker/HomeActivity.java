package com.example.healthtracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    LinearLayout llMental,llPhysical;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        llMental=findViewById(R.id.llMental);
        llPhysical=findViewById(R.id.llPhysical);

        llMental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,MentalHealthActivity.class);
                startActivity(intent);
            }
        });

    }
}