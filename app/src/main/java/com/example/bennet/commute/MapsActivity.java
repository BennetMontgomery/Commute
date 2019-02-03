package com.example.bennet.commute;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.StrictMode;
import android.provider.SyncStateContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FloatingActionButton mFAB;
    private FusedLocationProviderClient mFusedLocationClient;
    private final int REQUEST_LOCATION = 4;
    private JSONObject mapData;
    private Button goButton;
    private ProgressDialog progress;

    EditText origin;
    EditText finaldest;
    Button sendInfo;

    private class JSONThread implements Runnable {
        JsonStore json;
        URL url;

        public JSONThread(URL url) {
            this.url = url;
        }


        public void run() {
            this.json = null;
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                System.out.println("Thread test");
                json = objectMapper.readValue(url, JsonStore.class);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        public JsonStore getJsonValue() {
            return this.json;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        mFAB = findViewById(R.id.floatingActionButton);
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Time test");
                URL url;
                StringBuffer response = new StringBuffer();
                try {
                    System.out.println("Time test 2");
                    url = new URL("https://maps.googleapis.com/maps/api/directions/json?origin=Toronto&destination=Montreal&alternatives=true&key=AIzaSyCTXdNtnh6_yKnLLwHo_efKxOvRLWzxg0k");
                    JsonStore js = null;
                    try {
                        JSONThread thread = new JSONThread(url);
                        new Thread(thread).start();

                        while(js == null) {
                            System.out.println("Test while");
                            js = thread.getJsonValue();
                        }
                        System.out.println("Time test 3");
                        System.out.println("Time in traffic: " + js.getRoutes()[0].getLegs()[0].getDuration_in_traffic().getValue());
                    } catch(Exception e) {
                        System.out.println("Fuck");
                        e.printStackTrace();
                    }
                } catch (MalformedURLException e) {
                    System.out.println("Fuck");
                    throw new IllegalArgumentException("invalid url");
                }

                HttpURLConnection conn = null;
                try {
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoOutput(false);
                    conn.setDoInput(true);
                    conn.setUseCaches(false);
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

                    // handle the response
                    int status = conn.getResponseCode();
                    if (status != 200) {
                        throw new IOException("Post failed with error code " + status);
                    } else {
                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String inputLine;
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }

                    //Here is your json in string format
                    String responseJSON = response.toString();
                }

            }
        });
        System.out.println("email (after intent): " + name);
        setTitle("Hello " + name + "!");
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        origin = findViewById(R.id.editText);
        finaldest = findViewById(R.id.editText2);
        sendInfo = findViewById(R.id.go_button);

        sendInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouteParser rp = new RouteParser();
                if (!rp.parse(origin.getText().toString(), true)) {
                    Toast.makeText(MapsActivity.this, "The origin address is invalid", Toast.LENGTH_LONG).show();
                }


                if (!rp.parse(finaldest.getText().toString(), false)) {
                    Toast.makeText(MapsActivity.this, "The final destination is invalid", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // We can now safely use the API we requested access to
                try {
                    mFusedLocationClient.getLastLocation()
                            .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    mMap.setMyLocationEnabled(true);
                                    // Got last known location. In some rare situations this can be null.
                                    if (location != null) {
                                        // Logic to handle location object
                                        System.out.println("getLastLocation: " + location.toString());
                                    }

                                }
                            });
                } catch (SecurityException e) {
                    System.out.println(e.toString());
                }

            } else {
                // Permission was denied or request was cancelled
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
        } else {
            // permission has been granted, continue as usual
            mMap.setMyLocationEnabled(true);
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                double latitude = location.getLatitude();

                                // Getting longitude of the current location
                                double longitude = location.getLongitude();

                                // Creating a LatLng object for the current location
                                LatLng latLng = new LatLng(latitude, longitude);

                                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
                            }
                            System.out.println("getLastLocation: " + location.toString());
                        }
                    });
        }
    }

    /*@Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();

        // Getting longitude of the current location
        double longitude = location.getLongitude();

        // Creating a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);

    }*/
}
