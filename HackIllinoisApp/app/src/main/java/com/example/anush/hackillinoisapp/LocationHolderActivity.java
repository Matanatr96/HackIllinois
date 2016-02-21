package com.example.anush.hackillinoisapp;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class LocationHolderActivity extends AppCompatActivity {

    public static ArrayList<Location> locationHolder;
    private ListView lv;
    private TextView tv;

    public LocationHolderActivity() {
        locationHolder = new ArrayList<>();
    }

    public void add(Location location) {
        locationHolder.add(location);
    }

    public static ArrayList getInstance() {
        if(locationHolder == null) locationHolder = new ArrayList<>();
        return locationHolder;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_holder);

        if (locationHolder.size() == 0 || locationHolder == null) {
            tv = (TextView) findViewById(R.id.textView1);
            tv.setText("There are no locations to display");
        } else {
            lv = (ListView) findViewById(R.id.listView);
            ArrayAdapter<Location> arrayAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    locationHolder);

            lv.setAdapter(arrayAdapter);
        }
    }

    public void descriptiveView(View v) {
        startActivity(new Intent(this, DescriptiveViewActivity.class));
    }




}
