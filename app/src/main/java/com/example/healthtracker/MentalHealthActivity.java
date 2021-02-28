package com.example.healthtracker;

import android.content.Intent;
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

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MentalHealthActivity extends AppCompatActivity {

    EditText etAge;
    Button btnRegister;
    String gender,locationType,fieldOfStudy,insomnia,screenTime;//headache = fatigue

   // TextView resMentalHealth;
    String age,stressLevel,socialCircle,headache,energyLevel,physicalActivity,anxietyAttacks,growthRate,alcoholIntake,mentalHealth;
    String suicidalThoughts,mentalIllness;
    //RadioButton genderMale,genderFemale,genderOther,hdevcity,mdevcity,ldevcity,stress1,stress2,stress3,stress4,stress5;
    Users userdata= Prevalent.currentOnlineUser;
    //Users userdata=new Users("Anusha Mittal","9643825069","anusha");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mental_health);
        btnRegister=findViewById(R.id.btnRegister);
        etAge=findViewById(R.id.age);
     //   resMentalHealth=findViewById(R.id.resMentalHealth);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                age=etAge.getText().toString();
                Intent intent=new Intent(MentalHealthActivity.this,MentalHealthResultActivity.class);
                startActivity(intent);
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

            case R.id.hdevcity:
                if (checked)
                    locationType ="hdevcity";
                break;
            case R.id.mdevcity:
                if (checked)
                    locationType ="mdevcity";
                break;
            case R.id.ldevcity:
                if (checked)
                    locationType = "ldevcity";
                break;

            case R.id.stress1:
                if (checked)
                    stressLevel = "1";
                break;
            case R.id.stress2:
                if (checked)
                    stressLevel = "2";
                break;
            case R.id.stress3:
                if (checked)
                    stressLevel = "3";
                break;
            case R.id.stress4:
                if (checked)
                    stressLevel = "4";
                break;
            case R.id.stress5:
                if (checked)
                    stressLevel = "5";
                break;

            case R.id.medical:
                if (checked)
                    fieldOfStudy = "medical";
                break;
            case R.id.tech:
                if (checked)
                    fieldOfStudy = "technical";
                break;
            case R.id.commarts:
                if (checked)
                    fieldOfStudy = "commarts";
                break;

            case R.id.insomniayes:
                if (checked)
                    insomnia="Yes";
                break;
            case R.id.insomniano:
                if (checked)
                    insomnia="No";
                break;
            case R.id.insomniamaybe:
                if (checked)
                    insomnia="Maybe";
                break;

            case R.id.social1:
                if (checked)
                    socialCircle="1";
                break;

            case R.id.social2:
                if (checked)
                    socialCircle="2";
                break;
            case R.id.social3:
                if (checked)
                    socialCircle="3";
                break;
            case R.id.social4:
                if (checked)
                    socialCircle="4";
                break;
            case R.id.social5:
                if (checked)
                    socialCircle="5";
                break;

            case R.id.fatigue1:
                if (checked)
                    headache= "1";
                break;
            case R.id.fatigue2:
                if (checked)
                    headache="2";
                break;
            case R.id.fatigue3:
                if (checked)
                    headache="3";
                break;
            case R.id.fatigue4:
                if (checked)
                    headache="4";
                break;
            case R.id.fatigue5:
                if (checked)
                    headache="5";
                break;

            case R.id.suicideyes:
                if (checked)
                    suicidalThoughts = "Yes";
                break;
            case R.id.suicideno:
                if (checked)
                    suicidalThoughts = "No";
                break;

            case R.id.conc1:
                if (checked)
                    energyLevel = "1";
                break;

            case R.id.conc2 :
                if (checked)
                    energyLevel= "2";
                break;
            case R.id.conc3:
                if (checked)
                    energyLevel= "3";
                break;
            case R.id.conc4:
                if (checked)
                    energyLevel="4";
                break;
            case R.id.conc5:
                if (checked)
                    energyLevel="5";
                break;

            case R.id.exe1:
                if (checked)
                    physicalActivity =  "1";
                break;

            case R.id.exe2:
                if (checked)
                    physicalActivity = "2";
                break;
            case R.id.exe3:
                if (checked)
                    physicalActivity="3";
                break;
            case R.id.exe4:
                if (checked)
                    physicalActivity="4";
                break;
            case R.id.exe5:
                if (checked)
                    physicalActivity = "5";
                break;

            case R.id.alcohol1:
                if (checked)
                    alcoholIntake="1";
                break;

            case R.id.alcohol2:
                if (checked)
                    alcoholIntake = "2";
                break;
            case R.id.alcohol3:
                if (checked)
                    alcoholIntake = "3";
                break;
            case R.id.alcohol4:
                if (checked)
                    alcoholIntake = "4";
                break;
            case R.id.alcohol5:
                if (checked)
                    alcoholIntake="5";
                break;

            case R.id.anx1:
                if (checked)
                    anxietyAttacks ="1";
                break;

            case R.id.anx2:
                if (checked)
                    anxietyAttacks ="2";
                break;
            case R.id.anx3:
                if (checked)
                    anxietyAttacks ="3";
                break;
            case R.id.anx4:
                if (checked)
                    anxietyAttacks ="4";
                break;
            case R.id.anx5:
                if (checked)
                    anxietyAttacks = "5";
                break;

            case R.id.growth1:
                if (checked)
                    growthRate ="1";
                break;
            case R.id.growth2:
                if (checked)
                    growthRate="2";
                break;
            case R.id.growth3:
                if (checked)
                    growthRate="3";
                break;
            case R.id.growth4:
                if (checked)
                    growthRate = "4";
                break;
            case R.id.growth5:
                if (checked)
                    growthRate = "5";
                break;


            case R.id.lessthan2hrs:
                if (checked)
                    screenTime = "2 hours or less"; //
                break;
            case R.id.twotofive:
                if (checked)
                    screenTime = "2-5";
                break;
            case R.id.fivetoseven:
                if (checked)
                    screenTime = "5-7";
                break;
            case R.id.morethan7:
                if (checked)
                    screenTime="7 or above";
                break;

            case R.id.traumayes:
                if (checked)
                    mentalIllness = "Yes";
                break;
            case R.id.traumano:
                if (checked)
                    mentalIllness="No";
                break;

            case R.id.mental1:
                if (checked)
                    mentalHealth = "1";
                break;
            case R.id.mental2:
                if (checked)
                    mentalHealth ="2";
                break;
            case R.id.mental3:
                if (checked)
                    mentalHealth = "3";
                break;
            case R.id.mental4:
                if (checked)
                    mentalHealth ="4";
                break;
            case R.id.mental5:
                if (checked)
                    mentalHealth = "5";
                break;
        }


    }

    private void addInDatabase(){
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(userdata!=null && userdata.getNumber()!=null) {
                    String number = userdata.getNumber();
                    if (!dataSnapshot.child("users").child(number).child("Mental Health").exists()) {
                        HashMap<String, Object> map = new HashMap<>();
//                    map.put("name", userdata.getName());
//                    map.put("number", userdata.getNumber());
//                    map.put("password", userdata.getPass());
                        map.put("age", age);
                        map.put("gender",gender);
                        map.put("locationType", locationType);
                        map.put("stressLevel", stressLevel);
                        map.put("fieldOfStudy", fieldOfStudy);
                        map.put("insomnia", insomnia);
                        map.put("socialCircle", socialCircle);
                        map.put("headache", headache);
                        map.put("screenTime", screenTime);
                        map.put("suicidalThoughts", suicidalThoughts);
                        map.put("energyLevel", energyLevel);
                        map.put("physicalActivity", physicalActivity);
                        map.put("anxietyAttacks", anxietyAttacks);
                        map.put("growthRate", growthRate);
                        map.put("alcoholIntake", alcoholIntake);
                        map.put("mentalIllness", mentalIllness);
                        map.put("mentalHealth", mentalHealth);

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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void httpReq(View view)
    {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody formbody=new FormBody.Builder()
                .add("age",age)
                .add("stress",String.valueOf(stressLevel))
                .add("insom",insomnia)
                .add("social",String.valueOf(socialCircle))
                .add("head",String.valueOf(headache))
                .add("suicidal", suicidalThoughts)
                .add("conc",String.valueOf(energyLevel))
                .add("phy",String.valueOf(physicalActivity))
                .add("anx",String.valueOf(anxietyAttacks))
                .add("grow",String.valueOf(growthRate))
                .add("ill",mentalIllness)
                        .build();
        Request request = new Request.Builder().url("https://mental-health-ratingprediction.herokuapp.com/").post(formbody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Toast.makeText(MentalHealthActivity.this,"URL not found",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
               // TextView textView = (TextView)findViewById(R.id.resMentalHealth);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Intent i = new Intent(MentalHealthActivity.this, MentalHealthResultActivity.class);
                            String result=response.body().string();
                            i.putExtra("3", result);
                            startActivity(i);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

}