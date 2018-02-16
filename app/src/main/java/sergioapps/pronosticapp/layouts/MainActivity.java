package sergioapps.pronosticapp.layouts;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import sergioapps.pronosticapp.asynctask.refreshTime;
import sergioapps.pronosticapp.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int MY_PERMISSIONS = 10;
    RelativeLayout content;
    ImageButton mapbutton, refreshbutton;
    public static ImageView imToday,imstate;
    public static TextView city,textgrados,textdate,txt1,txt2,txt3;
    Double lat, lon;
    private String Key = "45744d6b388d63e1d99d34045a322e74";
    private String dataurl = "https://api.darksky.net/forecast/" + Key;
    LocationManager locationManager;
    android.app.AlertDialog alert = null;
    LocationListener locationListener;
    public static RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConfigAll();
        Permisos();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Toast.makeText(getApplicationContext(),"Waiting for GPS...",Toast.LENGTH_LONG).show();
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lon=location.getLongitude();
                lat=location.getLatitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };


        new Thread(new Runnable() {
            public void run() {

                RefreshData();

                runOnUiThread(new Runnable() {
                    public void run() {

                    }
                });
            }
        }).start();

    }

    private void ConfigAll() {
        mapbutton = (ImageButton) findViewById(R.id.mapbutton);
        mapbutton.setOnClickListener(this);
        refreshbutton = (ImageButton) findViewById(R.id.refreshbutton);
        refreshbutton.setOnClickListener(this);
        content = (RelativeLayout) findViewById(R.id.content);
        city = (TextView) findViewById(R.id.textCity);
        textgrados = (TextView) findViewById(R.id.textGrados);
        textdate = (TextView) findViewById(R.id.textdate);
        txt1 = (TextView) findViewById(R.id.txt1);
        txt2 = (TextView) findViewById(R.id.txt2);
        txt3 = (TextView) findViewById(R.id.txt3);

        recyclerView= (RecyclerView)findViewById(R.id.recicler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        imToday = (ImageView)findViewById(R.id.imToday);
        imstate = (ImageView)findViewById(R.id.imstate);
    }


    private void Permisos() {
        //PREGUNTAR PERMISOS
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) || (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED)
                    ) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS);
            }
        }
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mapbutton:
                //map
                Toast.makeText(getApplicationContext(), "MAPA", Toast.LENGTH_SHORT).show();
                //Intent intentMon = new Intent(this, monitor2.class);
                //startActivity(intentMon);
                break;
            case R.id.refreshbutton:
                RefreshData();
                Toast.makeText(getApplicationContext(), "Actualizando..", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void RefreshData() {
        String provider = "";
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
            Paso2(provider);
        } else {
            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                provider = LocationManager.NETWORK_PROVIDER;
                Paso2(provider);
            } else {
                AlertNoGps();
            }
        }

    }

    private void Paso2(final String provider) {
        while (String.valueOf(lon)=="null"||String.valueOf(lat)=="null"){

        }
        refreshTime task = new refreshTime();
        task.execute(dataurl + "/" + String.valueOf(lat) + "," + String.valueOf(lon));
    }

    public void AlertNoGps() {
        //ACTIVAR GPS
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage("El sistema GPS esta desactivado, ACTIVELO...")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                });
        alert = builder.create();
        alert.show();
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            } else {
                locationManager.removeUpdates(locationListener);
            }
        } else {
            locationManager.removeUpdates(locationListener);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            } else {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        } else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }









}
