package com.example.healthtracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthtracker.Models.Prevalent;
import com.example.healthtracker.Models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText etName,etNumber,etPass;
    Button btnRegister;
    TextView tvLogin;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName=findViewById(R.id.etName);
        etNumber=findViewById(R.id.etNumber);
        etPass=findViewById(R.id.etPass);
        btnRegister=findViewById(R.id.btnRegister);
        tvLogin=findViewById(R.id.tvLogin);

        FirebaseApp.initializeApp(this);
        firebaseAuth= FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void createAccount() {
        String name=etName.getText().toString();
        String number=etNumber.getText().toString();
        String pass=etPass.getText().toString();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Enter name..", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(number)){
            Toast.makeText(this, "Enter number..", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(pass)){
            Toast.makeText(this, "Enter password..", Toast.LENGTH_SHORT).show();
        }else {
            progressDialog.setTitle("Create Account");
            progressDialog.setMessage("Registering user, please wait..");
            progressDialog.show();

            validateUser(name,number,pass);
        }
    }

    private void validateUser(final String name, final String number, final String pass) {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.child("users").child(number).exists()) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("name", name);
                    map.put("number", number);
                    map.put("password", pass);

                    Users userdata=dataSnapshot.child("users").child(number).getValue(Users.class);
                    Prevalent.currentOnlineUser=userdata;

                    databaseReference.child("users").child(number).updateChildren(map)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "Congratulations, you are registered!!", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                    } else {
                                        //  progressDialog.dismiss();
                                        Toast.makeText(RegisterActivity.this, "there seems to be some error, try again!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            });

                } else {
                    Toast.makeText(RegisterActivity.this, "this number" + number + " already exists", Toast.LENGTH_SHORT).show();
                    //       progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Please try again with some another number", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}