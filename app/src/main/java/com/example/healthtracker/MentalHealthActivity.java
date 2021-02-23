package com.example.healthtracker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthtracker.Models.Prevalent;
import com.example.healthtracker.Models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MentalHealthActivity extends AppCompatActivity {

    EditText etAge;
    Button btnRegister;
    String gender,locationType,fieldOfStudy,insomnia,screenTime;
    int age,stressLevel,socialCircle,headache,energyLevel,physicalActivity,anxietyAttacks,growthRate,alcoholIntake,mentalHealth;
    Boolean suicidalThoughts,mentalIllness;
    RadioButton genderMale,genderFemale,genderOther,hdevcity,mdevcity,ldevcity,stress1,stress2,stress3,stress4,stress5;
    Users userdata=Prevalent.currentOnlineUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mental_health);
        btnRegister=findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addInDatabase();
            }
        });
    }

    //onradio
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.genderFemale:
                if (checked)
                    gender="Female";
                break;
            case R.id.genderMale:
                if (checked)
                    gender="Male";
                break;
            case R.id.genderOther:
                if (checked)
                    gender="Other";
                break;
        }


    }

    private void addInDatabase(){
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String number=userdata.getNumber();
                if (!dataSnapshot.child("users").child(number).child("Mental Health").exists()) {
                    HashMap<String, Object> map = new HashMap<>();
//                    map.put("name", userdata.getName());
//                    map.put("number", userdata.getNumber());
//                    map.put("password", userdata.getPass());
                    map.put("gender", gender);

                    databaseReference.child("users").child(number).child("Mental Health").updateChildren(map)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MentalHealthActivity.this, "Congratulations, your data is added!!", Toast.LENGTH_SHORT).show();
                           //             progressDialog.dismiss();
//                                        Intent intent = new Intent(MentalHealthActivity.this, HomeActivity.class);
//                                        startActivity(intent);
                                    } else {
                                        //  progressDialog.dismiss();
                                        Toast.makeText(MentalHealthActivity.this, "there seems to be some error, try again!", Toast.LENGTH_SHORT).show();
//                                        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
//                                        startActivity(intent);
                                    }
                                }
                            });

                } else {
                    Toast.makeText(MentalHealthActivity.this, "this data already exists", Toast.LENGTH_SHORT).show();
                    //       progressDialog.dismiss();
//                    Toast.makeText(RegisterActivity.this, "Please try again with some another number", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
//                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}