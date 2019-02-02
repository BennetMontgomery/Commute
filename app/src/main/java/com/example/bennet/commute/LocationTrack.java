package com.example.bennet.commute;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LocationTrack extends AppCompatActivity {

    EditText origin;
    EditText finaldest;
    Button sendInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_track);

        origin = findViewById(R.id.Origin);
        finaldest = findViewById(R.id.FinalDest);
        sendInfo = findViewById(R.id.sendInfo);

        sendInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouteParser rp = new RouteParser();
                if (!rp.parse(origin.getText().toString(), true)){
                    Toast.makeText(LocationTrack.this, "The origin address is invalid", Toast.LENGTH_LONG).show();
                }


                if (!rp.parse(finaldest.getText().toString(), false)){
                    Toast.makeText(LocationTrack.this, "The final destination is invalid", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
