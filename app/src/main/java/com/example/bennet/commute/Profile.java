package com.example.bennet.commute;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends AppCompatActivity {

    TextView scenePts, namer;
    int scene;
String name;
    private Button btn20, btn40, btn60, btn80, btn100, btn120, btn140;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        scenePts = findViewById(R.id.scene_points2);
        scene = Integer.parseInt(scenePts.getText().toString());

        namer = findViewById(R.id.scene_points3);


        btn20 = findViewById(R.id.button20);
        btn40 = findViewById(R.id.button40);
        btn60 = findViewById(R.id.button60);
        btn80 = findViewById(R.id.button80);
        btn100 = findViewById(R.id.button100);
        btn120 = findViewById(R.id.button120);
        btn140 = findViewById(R.id.button140);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        namer.setText(name);

        System.out.println(name + " hello");

        btn20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scene = Integer.parseInt(scenePts.getText().toString());
                if(scene - 20 >= 0) {
                    scenePts.setText(Integer.toString(scene - 20));
                }
                else {
                    Toast.makeText(Profile.this, "You don't have enough", Toast.LENGTH_LONG).show();
                }
            }
        });
        btn40.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scene = Integer.parseInt(scenePts.getText().toString());
                if(scene - 40 >= 0) {
                    scenePts.setText(Integer.toString(scene - 40));                }
                else {
                    Toast.makeText(Profile.this, "You don't have enough", Toast.LENGTH_LONG).show();
                }
            }
        });
        btn60.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scene = Integer.parseInt(scenePts.getText().toString());
                if(scene - 60 >= 0) {
                    scenePts.setText(Integer.toString(scene - 60));                }
                else {
                    Toast.makeText(Profile.this, "You don't have enough", Toast.LENGTH_LONG).show();
                }
            }
        });
        btn80.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scene = Integer.parseInt(scenePts.getText().toString());
                if(scene - 80 >= 0) {
                    scenePts.setText(Integer.toString(scene - 80));                }
                else {
                    Toast.makeText(Profile.this, "You don't have enough", Toast.LENGTH_LONG).show();
                }
            }
        });
        btn100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scene = Integer.parseInt(scenePts.getText().toString());
                if(scene - 100 >= 0) {
                    scenePts.setText(Integer.toString(scene - 100));                }
                else {
                    Toast.makeText(Profile.this, "You don't have enough", Toast.LENGTH_LONG).show();
                }
            }
        });
        btn120.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scene = Integer.parseInt(scenePts.getText().toString());
                if(scene - 120 >= 0) {
                    scenePts.setText(Integer.toString(scene - 120));                }
                else {
                    Toast.makeText(Profile.this, "You don't have enough", Toast.LENGTH_LONG).show();
                }
            }
        });
        btn140.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scene = Integer.parseInt(scenePts.getText().toString());
                if(scene - 140 >= 0) {
                    scenePts.setText(Integer.toString(scene - 140));                }
                else {
                    Toast.makeText(Profile.this, "You don't have enough", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
