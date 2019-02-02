package com.example.bennet.commute;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.*;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;




/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    TextView email, password;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_login);
        // Set up the login form.

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn = findViewById(R.id.email_sign_in_button);

        firebaseAuth = FirebaseAuth.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String finalEmail = email.getText().toString();
                String finalPassword= password.getText().toString();
                firebaseAuth.signInWithEmailAndPassword(finalEmail, finalPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                        startActivity(i);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, "IT DID NOT WORK :( ", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }



}


