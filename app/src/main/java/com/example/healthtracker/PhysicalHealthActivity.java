package com.example.healthtracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class PhysicalHealthActivity extends AppCompatActivity {

    EditText etHeight,etWeight,etAge;
    Button btnBmi,btnCalorie;

    Users userdata= Prevalent.currentOnlineUser;
    private ProgressDialog progressDialog;

    public static double bmi;
    public static int cal_count;
    public static String obesity,gender,activityStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical_health);

        etHeight=findViewById(R.id.etHeight);
        etWeight=findViewById(R.id.etWeight);
        etAge=findViewById(R.id.etAge);
        btnBmi=findViewById(R.id.btnBmi);
        //btnCalorie=findViewById(R.id.btnCalorie);
        //reqdCalorie=findViewById(R.id.reqdCalorie);
        //calculatedBMI=findViewById(R.id.calculatedBMI);
        //category=findViewById(R.id.category);
        progressDialog=new ProgressDialog(this);

        btnBmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String height=etHeight.getText().toString();
                String weight=etWeight.getText().toString();
                String age=etAge.getText().toString();

                if(TextUtils.isEmpty(height)){
                    Toast.makeText(PhysicalHealthActivity.this, "Enter height..", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(weight)){
                    Toast.makeText(PhysicalHealthActivity.this, "Enter weight..", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(age)){
                    Toast.makeText(PhysicalHealthActivity.this, "Enter age..", Toast.LENGTH_SHORT).show();
                }else {
                    progressDialog.setTitle("Physical Health Data");
                    progressDialog.setMessage("adding data, please wait..");
                    progressDialog.show();
                    addInDatabase(height, weight, age,gender,activityStatus); //add cal_count
                }

                int ht = Integer.parseInt(height);
                int wt = Integer.parseInt(weight);
                int a = Integer.parseInt(age);

                bmi = wt/(ht*ht);
                calorieAllocation(a);
                categoryAllocation(bmi);
//                reqdCalorie.setText(String.valueOf(cal_count));
//                category.setText(obesity);

                Intent i = new Intent(PhysicalHealthActivity.this, AddItemActivity.class);
                i.putExtra("bmi", bmi);
                i.putExtra("cal_count", cal_count);
                i.putExtra("obesity", obesity);
                startActivity(i);


            }
        });

        btnCalorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigate to next activity
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.genderFemale:
                if (checked)
                    gender = "Female";
                break;
            case R.id.genderMale:
                if (checked)
                    gender = "Male";
                break;

            case R.id.sedentary:
                if (checked)
                    activityStatus = "sedentary";
                break;
            case R.id.moderatelyActive:
                if (checked)
                    activityStatus = "modelatelyActive";
                break;
            case R.id.Active:
                if (checked)
                    activityStatus = "Active";
                break;
        }
    }

    //db function starts here
    private void addInDatabase(String height,String weight,String age,String gender,String activityStatus){
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String number="";
                if(userdata.getNumber()!=null){
                   number=userdata.getNumber();}
                if (!dataSnapshot.child("users").child(number).child("Physical Health").exists()) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("height", height);
                    map.put("weight", weight);
                    map.put("age", age);
                    map.put("gender",gender);
                    map.put("activityStatus",activityStatus);

                    databaseReference.child("users").child(number).child("Physical Health").updateChildren(map)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(PhysicalHealthActivity.this, "Congratulations, your data is added!!", Toast.LENGTH_SHORT).show();
                                        //             progressDialog.dismiss();
//                                        Intent intent = new Intent(MentalHealthActivity.this, HomeActivity.class);
//                                        startActivity(intent);
                                    } else {
                                        //  progressDialog.dismiss();
                                        Toast.makeText(PhysicalHealthActivity.this, "there seems to be some error, try again!", Toast.LENGTH_SHORT).show();
//                                        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
//                                        startActivity(intent);
                                    }
                                }
                            });

                } else {
                    Toast.makeText(PhysicalHealthActivity.this, "this data already exists", Toast.LENGTH_SHORT).show();
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
    }//End of database func

    public void calorieAllocation(int a){
        if(a>=2 && a<=3){
            if(activityStatus=="sedentary"){
                cal_count=1000;
            }else if(activityStatus=="moderatelyActive"){
                cal_count=1200;
            }else{
                cal_count=1200;
            }
        }else if(a>=4 && a<=8){
            if (gender == "Female") {
                if(activityStatus=="sedentary"){
                    cal_count=1200;
                }else if(activityStatus=="moderatelyActive"){
                    cal_count=1500;
                }else{
                    cal_count=1600;
                }
            }else{
                if(activityStatus=="sedentary"){
                    cal_count=1400;
                }else if(activityStatus=="moderatelyActive"){
                    cal_count=1500;
                }else{
                    cal_count=1800;
                }
            }
        }else if(a>=9 && a<=13){
            if (gender == "Female") {
                if(activityStatus=="sedentary"){
                    cal_count=1600;
                }else if(activityStatus=="moderatelyActive"){
                    cal_count=1800;
                }else{
                    cal_count=2000;
                }
            }else{
                if(activityStatus=="sedentary"){
                    cal_count=1800;
                }else if(activityStatus=="moderatelyActive"){
                    cal_count=2000;
                }else{
                    cal_count=2300;
                }
            }
        }else if(a>=14 && a<=18){
            if (gender == "Female") {
                if(activityStatus=="sedentary"){
                    cal_count=1800;
                }else if(activityStatus=="moderatelyActive"){
                    cal_count=2000;
                }else{
                    cal_count=2400;
                }
            }else{
                if(activityStatus=="sedentary"){
                    cal_count=2200;
                }else if(activityStatus=="moderatelyActive"){
                    cal_count=2600;
                }else{
                    cal_count=3000;
                }
            }
        }else if(a>=19 && a<=30){
            if (gender == "Female") {
                if(activityStatus=="sedentary"){
                    cal_count=2000;
                }else if(activityStatus=="moderatelyActive"){
                    cal_count=2100;
                }else{
                    cal_count=2400;
                }
            }else{
                if(activityStatus=="sedentary"){
                    cal_count=2400;
                }else if(activityStatus=="moderatelyActive"){
                    cal_count=2700;
                }else{
                    cal_count=3000;
                }
            }
        }else if(a>=31 && a<=50){
            if (gender == "Female") {
                if(activityStatus=="sedentary"){
                    cal_count=1800;
                }else if(activityStatus=="moderatelyActive"){
                    cal_count=2000;
                }else{
                    cal_count=2200;
                }
            }else{
                if(activityStatus=="sedentary"){
                    cal_count=2200;
                }else if(activityStatus=="moderatelyActive"){
                    cal_count=2500;
                }else{
                    cal_count=2900;
                }
            }
        }else if(a>=51){
            if (gender == "Female") {
                if(activityStatus=="sedentary"){
                    cal_count=1600;
                }else if(activityStatus=="moderatelyActive"){
                    cal_count=1800;
                }else{
                    cal_count=2100;
                }
            }else{
                if(activityStatus=="sedentary"){
                    cal_count=2000;
                }else if(activityStatus=="moderatelyActive"){
                    cal_count=2300;
                }else{
                    cal_count=2600;
                }
            }
        }
    }

    public void categoryAllocation(double bmi){
        if(bmi<18.5){
            obesity="Underweight";
        }else if(bmi>=18.5 && bmi<=24.5){
            obesity="Healthy";
        }else if(bmi>=25 && bmi<=29.9){
            obesity="Overweight";
        }else if(bmi>=30){
            obesity="Obese";
        }
    }

}