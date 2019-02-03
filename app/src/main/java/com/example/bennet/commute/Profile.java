package com.example.bennet.commute;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends AppCompatActivity {

    TextView scenePts;
    int scene;

    private Button btn20, btn40, btn60, btn80, btn100, btn120, btn140;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        scenePts = findViewById(R.id.scene_points);
        scene = Integer.parseInt(scenePts.toString());

        btn20 = findViewById(R.id.button20);
        btn40 = findViewById(R.id.button20);
        btn60 = findViewById(R.id.button20);
        btn80 = findViewById(R.id.button20);
        btn100 = findViewById(R.id.button20);
        btn120 = findViewById(R.id.button20);
        btn140 = findViewById(R.id.button20);

    }

    public void onClickBtn(View v)
    {
        while(scene>=0) {
            if (v == btn20) {
                scenePts.setText(scene - 20);
            } else if (v == btn40) {
                scenePts.setText(scene - 40);
            } else if (v == btn60) {
                scenePts.setText(scene - 60);
            } else if (v == btn80) {
                scenePts.setText(scene - 80);
            } else if (v == btn100) {
                scenePts.setText(scene - 100);
            } else if (v == btn120) {
                scenePts.setText(scene - 120);
            } else if (v == btn140) {
                scenePts.setText(scene - 140);
            }
        }
        Toast.makeText(Profile.this, "You don't have enough", Toast.LENGTH_LONG).show();
    }
}
