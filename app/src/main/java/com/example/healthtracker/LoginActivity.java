package com.example.healthtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    EditText etPhone,etPass;
    Button btnLogin;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etPhone=findViewById(R.id.etPhone);
        etPass=findViewById(R.id.etPass);
        btnLogin=findViewById(R.id.btnLogin);
        progressDialog=new ProgressDialog(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number=etPhone.getText().toString();
                String pass=etPass.getText().toString();

                if(TextUtils.isEmpty(number)){
                    Toast.makeText(LoginActivity.this, "Enter number..", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(pass)){
                    Toast.makeText(LoginActivity.this, "Enter password..", Toast.LENGTH_SHORT).show();
                }else {
                    progressDialog.setTitle("Login");
                    progressDialog.setMessage("Checking credentials, please wait..");
                    progressDialog.show();

                        login(number, pass);

                }
            }
        });
    }

    private void login(final String number, final String pass) {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference userNameRef = rootRef.child("users").child(number);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    Toast.makeText(LoginActivity.this, "This number doesn't exist, try again!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    progressDialog.dismiss();
//                    Users userData=dataSnapshot.getValue(Users.class);
//                    if(userData.getPass().equals(pass))
//                      Toast.makeText(LoginActivity.this, "Congratulations! You are logged in!", Toast.LENGTH_SHORT).show();
//                    else{
//                        Toast.makeText(LoginActivity.this, "Password is wrong", Toast.LENGTH_SHORT).show();
//                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
//                        startActivity(intent);
//                    }
//                    Prevalent.currentOnlineUser=userData;
                    Toast.makeText(LoginActivity.this, "Congratulations! You are logged in!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //   Log.d(TAG, databaseError.getMessage()); //Don't ignore errors!
            }
        };
        userNameRef.addListenerForSingleValueEvent(eventListener);
    }

}