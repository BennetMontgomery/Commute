package com.example.bennet.commute;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
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
    private String name;
    private FloatingActionButton mFAB;
    private FusedLocationProviderClient mFusedLocationClient;
    private final int REQUEST_LOCATION = 4;
    private JSONObject mapData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Intent intent = getIntent();
        name = intent.getStringExtra("email");
        mFAB = findViewById(R.id.floatingActionButton);
        mFAB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("End my suffering");
                URL url;
                StringBuffer response = new StringBuffer();
                try {
                    url = new URL("https://maps.googleapis.com/maps/api/directions/json?origin=Toronto&destination=Montreal&alternatives=true&key=AIzaSyCTXdNtnh6_yKnLLwHo_efKxOvRLWzxg0k");
                    System.out.println("URL: " + url.toString());
                    try {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        String jsonString = Jsoup.connect(url.toString()).ignoreContentType(true).execute().body();
                        System.out.println("JSON: " + jsonString);
                        JsonParser jp = new JsonParser();
                        JsonElement json = jp.parse(jsonString);
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                } catch (MalformedURLException e) {
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
                    System.out.println("responseJSON: " + responseJSON);
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
            if(grantResults.length == 1
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
                                    }
                                    System.out.println("getLastLocation: " + location.toString());;
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
                            }
                            System.out.println("getLastLocation: " + location.toString());;
                        }
                    });
        }


        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
