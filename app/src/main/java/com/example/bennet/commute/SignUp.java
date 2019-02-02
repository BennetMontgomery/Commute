package com.example.bennet.commute;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.auth.*;


public class SignUp extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference users;
    private FirebaseAuth firebaseAuth;

    EditText editUsername, editPassword, editPasswordConfirm, editMail;

    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");
        editUsername = findViewById(R.id.Name);
        editMail = findViewById(R.id.Email);
        editPassword = findViewById(R.id.PWEdit);
        editPasswordConfirm = findViewById(R.id.PConfirmEdit);
        btnSignUp = findViewById(R.id.Submit);



        firebaseAuth = FirebaseAuth.getInstance();
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = editMail.getText().toString();
                final String password = editPassword.getText().toString();
                final String name = editUsername.getText().toString();
                if (editPasswordConfirm.getText().toString().equals(editPassword.getText().toString())) {
                    if (email.isEmpty()||password.isEmpty()||name.isEmpty()){
                        Toast.makeText(SignUp.this, "There is an empty field", Toast.LENGTH_LONG).show();
                    }
                    else{
                        CreateUserAccount(email, password);
                    }
                }
                else{
                    Toast.makeText(SignUp.this, "The Passwords are not the same", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void CreateUserAccount(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(SignUp.this, "Account created", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(SignUp.this, "ERROR, I REPEAT ERROR " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

