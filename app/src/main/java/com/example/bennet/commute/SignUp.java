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

    EditText editName, editPassword, editPasswordConfirm, editMail;

    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");
        editName = findViewById(R.id.prompt_name);
        editMail = findViewById(R.id.edit_email_signup);
        editPassword = findViewById(R.id.edit_password_signup);
        editPasswordConfirm = findViewById(R.id.edit_password_confirm_signup);
        btnSignUp = findViewById(R.id.submit_button);



        firebaseAuth = FirebaseAuth.getInstance();
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = editName.getText().toString();
                final String email = editMail.getText().toString();
                final String password = editPassword.getText().toString();
                if (editPasswordConfirm.getText().toString().equals(editPassword.getText().toString())) {
                    if (email.isEmpty()||password.isEmpty()){
                        Toast.makeText(SignUp.this, "There is an empty field", Toast.LENGTH_LONG).show();
                    }
                    else{
                        CreateUserAccount(email, password);

                        User user = new User(name, email, 0);

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("Users");

                        myRef.setValue(user);

                    }
                }
                else{
                    Toast.makeText(SignUp.this, "The Passwords are not the same", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void CreateUserAccount(final String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(SignUp.this, "Account created", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                    i.putExtra("email", email);
                    startActivity(i);
                }
                else{
                    Toast.makeText(SignUp.this, "ERROR, I REPEAT ERROR " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

