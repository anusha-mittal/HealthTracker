package com.example.healthtracker;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    Button btnBmi;
    Users userdata= Prevalent.currentOnlineUser;
    private ProgressDialog progressDialog;

    double bmi;
    int cal_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical_health);

        etHeight=findViewById(R.id.etHeight);
        etWeight=findViewById(R.id.etWeight);
        etAge=findViewById(R.id.etAge);
        btnBmi=findViewById(R.id.btnBmi);
        progressDialog=new ProgressDialog(this);

        btnBmi.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                String height=etHeight.getText().toString();
                String weight=etWeight.getText().toString();
                String age=etAge.getText().toString();

                int ht = Integer.parseInt(height);
                int wt = Integer.parseInt(weight);
                int a = Integer.parseInt(age);

                bmi = wt/(ht*ht);

                if(a>=14 && a<=18 && bmi <=24)
                    cal_count = 1400;



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
                    addInDatabase(height, weight, age); //add cal_count
                }
            }
        });
    }

    //db function starts here
    private void addInDatabase(String height,String weight,String age){
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String number=userdata.getNumber();
                if (!dataSnapshot.child("users").child(number).child("Physical Health").exists()) {
                    HashMap<String, Object> map = new HashMap<>();
//                    map.put("name", userdata.getName());
//                    map.put("number", userdata.getNumber());
//                    map.put("password", userdata.getPass());
                    map.put("height", height);
                    map.put("weight", weight);
                    map.put("age", age);

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


}