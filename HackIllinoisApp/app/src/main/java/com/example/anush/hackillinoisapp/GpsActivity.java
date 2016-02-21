package com.example.anush.hackillinoisapp;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GpsActivity extends Activity implements View.OnClickListener, android.content.DialogInterface.OnClickListener {
    /**
     * Called when the activity is first created.
     */

    private EditText showLocation;
    private Button btnGetLocation;
    private ProgressBar progress;

    private LocationManager locManager;
    private LocationListener locListener = new myLocationListener();

    private boolean gps_enabled = false;
    private boolean network_enabled = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        showLocation = (EditText) findViewById(R.id.txtShowLoc);
        showLocation.setEnabled(false);

        progress = (ProgressBar) findViewById(R.id.progressBar);
        progress.setVisibility(View.GONE);

        btnGetLocation = (Button) findViewById(R.id.btnGetLoc);
        btnGetLocation.setOnClickListener(this);

        locManager = (LocationManager) getSystemService(getApplicationContext().LOCATION_SERVICE);
    }

    @Override
    public void onPause() {
        super.onPause();

    }
    @Override
    public void onClick(View v) {

        progress.setVisibility(View.VISIBLE);

        try {
            gps_enabled = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);
        } catch (Exception e) {
        }
        try {
            network_enabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locListener);
        } catch (Exception e) {

        }

        if (!gps_enabled && !network_enabled) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Attention!");
            builder.setMessage("Sorry, location is not determined. Please enable location providers");
            builder.setPositiveButton("OK", this);
            builder.setNeutralButton("Cancel", this);
            builder.create().show();
            progress.setVisibility(View.GONE);
        }


    }

    private class myLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {

                try {
                    locManager.removeUpdates(locListener);
                } catch (Exception e) {

                }


                String longitude = "Longitude: " + location.getLongitude();
                String latitude = "Latitude: " + location.getLatitude();

                String cityName = null;

                Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
                //public List<Address> addresses= gcd.getFromLocation (double latitude1, double longitude1, int maxResults)


                List<Address> addresses;
                try {
                    addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    if (addresses.size() > 0)
                        System.out.println(addresses.get(0).getLocality());
                    cityName = addresses.get(0).getLocality();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                showLocation.setText("City Name: " + cityName + "\n" + longitude + "\n" + latitude);
                LocationHolderActivity loc = new LocationHolderActivity();
                loc.add(location);
                progress.setVisibility(View.GONE);
            }
        }

        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub

        }

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        if (which == DialogInterface.BUTTON_NEUTRAL) {
            showLocation.setText("location is not getting due to location provider");

        } else if (which == DialogInterface.BUTTON_POSITIVE) {
            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));

        }
    }

    public void viewLocations(View v) {
        startActivity(new Intent(this, LocationHolderActivity.class));
    }
}