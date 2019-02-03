package com.example.bennet.commute;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Looper;
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
import android.widget.ProgressBar;
import android.widget.Toast;
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
import com.google.maps.android.PolyUtil;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FloatingActionButton mFAB;
    private FusedLocationProviderClient mFusedLocationClient;
    private final int REQUEST_LOCATION = 4;
    private JSONObject mapData;
    private Button goButton;
    private ProgressDialog progress;
    private String[] rawPaths;
    private int[] travelTimes;
    private String start;
    private String end;
    public JSONObject BIGDADDY;

    EditText origin;
    EditText finaldest;
    Button sendInfo;

//    private class JSONThread implements Runnable {
//        JsonStore json;
//        URL url;
//
//        public JSONThread(URL url) {
//            this.url = url;
//        }
//
//
//        public void run() {
//            this.json = null;
//            ObjectMapper objectMapper = new ObjectMapper();
//            try {
//                System.out.println("Thread test");
//                json = objectMapper.readValue(url, JsonStore.class);
//            } catch(Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        public JsonStore getJsonValue() {
//            return this.json;
//        }
//    }
//
//    private class DownloadFilesTask extends AsyncTask<URL, Integer, Long> {
//        protected Long doInBackground(URL... urls) {
//            int count = urls.length;
//            long totalSize = 0;
//            for (int i = 0; i < count; i++) {
//                totalSize += Downloader.downloadFile(urls[i]);
//                publishProgress((int) ((i / (float) count) * 100));
//                // Escape early if cancel() is called
//                if (isCancelled()) break;
//            }
//            return totalSize;
//        }
//
//        protected void onProgressUpdate(Integer... progress) {
//            setProgressPercent(progress[0]);
//        }
//
//        protected void onPostExecute(Long result) {
//            showDialog("Downloaded " + result + " bytes");
//        }
//    }

    class MyAsyncTask extends AsyncTask<String, Integer, JSONObject> {
        private JSONObject jsonObject;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        public void onPostExecute(JSONObject result) {
            super.onPostExecute(result);
            //System.out.println("SLUT FOR JSON: " + jsonObject); //use jsonObject here

        }

        @Override
        protected JSONObject doInBackground(final String... args) {
            try {
                Looper.prepare();
                System.out.println("Time test");
                String myUrl = args[0];

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(myUrl);
                httpPost.setEntity(new UrlEncodedFormEntity(new ArrayList<NameValuePair>()));
                HttpEntity httpEntity = httpClient.execute(httpPost).getEntity();

                InputStream stream = httpEntity.getContent();
                BufferedReader bReader = new BufferedReader(new InputStreamReader(stream, "utf-8"), 8);
                StringBuilder sBuilder = new StringBuilder();

                String line = null;
                while ((line = bReader.readLine()) != null) {
                    sBuilder.append(line + "\n");
                }

                stream.close();
                jsonObject = new JSONObject(sBuilder.toString());
                BIGDADDY = new JSONObject(sBuilder.toString());
                System.out.println("In try of ASYNC! JSON OBJ: " + jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Returning: " + jsonObject);
            return jsonObject;
        }
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        mFAB = findViewById(R.id.floatingActionButton);
        origin = findViewById(R.id.editText);
        finaldest = findViewById(R.id.editText2);
        sendInfo = findViewById(R.id.go_button);

        mFAB.setOnClickListener(new View.OnClickListener() {
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
        sendInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouteParser rp = new RouteParser();
                if (rp.parse(origin.getText().toString(), true) && rp.parse(finaldest.getText().toString(), false)) {
                    rp.parse(origin.getText().toString(), true);
                    rp.parse(finaldest.getText().toString(), false);
                    start = rp.getOrigin();
                    end = rp.getDestination();
                }
                else {
                    start = "Kingston+ON";
                    end = "Montreal";
                }
                System.out.println("Time test");
                String myUrl = "https://maps.googleapis.com/maps/api/directions/json?origin=" + start + "&destination=" + end + "&departure_time=now&alternatives=true&key=AIzaSyCTXdNtnh6_yKnLLwHo_efKxOvRLWzxg0k";
                MyAsyncTask myAsyncTask = new MyAsyncTask();
                try {
                    BIGDADDY = myAsyncTask.execute(myUrl).get();
                } catch (ExecutionException e) {
                    System.out.println(e);
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
                System.out.println("WHO YO DADDY: " + BIGDADDY);
                JSONArray routes;
                try {
                    routes = BIGDADDY.getJSONArray("routes");
                    int bestValue = 0;
                    int bestRoute = 0;
                    for (int i = 0; i < routes.length(); i++) {
                        JSONObject singleRoute = (JSONObject) routes.get(i);
                        JSONArray legs = (JSONArray) singleRoute.get("legs");
                        JSONObject singleLeg = (JSONObject) legs.get(0);
                        JSONObject DIT = (JSONObject) singleLeg.get("duration_in_traffic");
                        System.out.println("DIT: " + DIT);
                        if (Integer.parseInt(DIT.getString("value")) >= bestValue) {
                            bestRoute = i;
                        }
                    }
                    routes = BIGDADDY.getJSONArray("routes");
                    for (int i = 0; i < routes.length(); i++) {
                        int best = Color.rgb(0.133f, 0.757f, 0.02f);
                        JSONObject singleRoute = (JSONObject) routes.get(i);
                        JSONObject overview_polyline = (JSONObject) singleRoute.get("overview_polyline");
                        if (overview_polyline != null) {
                            List<LatLng> polyLine = PolyUtil.decode(overview_polyline.getString("points"));
                            if (i == bestRoute) {
                                mMap.addPolyline(new PolylineOptions().addAll(polyLine).color(best));
                            }
                            else {
                                mMap.addPolyline(new PolylineOptions().addAll(polyLine));
                            }

                        }


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
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

    public static int count(final String string, final String substring)
    {
        int count = 0;
        int idx = 0;

        while ((idx = string.indexOf(substring, idx)) != -1)
        {
            idx++;
            count++;
        }

        return count;
    }

    /*public static int[] scores(int[] tt) {
        int max =
    }*/

}
