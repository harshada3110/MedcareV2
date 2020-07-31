package com.google.sites.medcare.Hospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.sites.medcare.R;

import java.util.ArrayList;
import java.util.List;

public class FilterHospital extends AppCompatActivity {

    private Spinner spinner_city, spinner_category;
    String itemstate, itemcategory;

    Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_hospital);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        SharedPreferences userDetails = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editDetails = userDetails.edit();
        String loc = userDetails.getString("Location", "Mumbai");

        spinner_city = findViewById(R.id.spinnerCity);
        spinner_category = findViewById(R.id.spinnerCategory);
        search = findViewById(R.id.search);

        List<String> locations=new ArrayList<>();
        //locations.add(0,"No Location");
        locations.add("Chennai");
        locations.add("Hyderabad");
        locations.add(0,"Mumbai");
        locations.add("Navi Mumbai");
        locations.add("Pune");
        locations.add("Thiruvananthapuram");
        locations.add("Udupi");

        List<String> category=new ArrayList<>();
        category.add(0,"Select Category");
        category.add("Cardiologist");
        category.add("COVID");
        category.add("Dentist");
        category.add("ENT");
        category.add("Gastroenterologists");
        category.add("Gynaecologist");
        category.add("Neurologist");
        category.add("Oncologist");

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter(this, R.layout.spinner_style, locations);
        dataAdapter.setDropDownViewResource(R.layout.spinner_style);

        ArrayAdapter<String> dataAdapter2;
        dataAdapter2 = new ArrayAdapter(this, R.layout.spinner_style, category);
        dataAdapter2.setDropDownViewResource(R.layout.spinner_style);

        spinner_city.setAdapter(dataAdapter);
        spinner_category.setAdapter(dataAdapter2);

        int spinnerPosition = dataAdapter.getPosition(loc);
        spinner_city.setSelection(spinnerPosition);


        spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals(loc)) {
                    itemstate = loc;
                } else {
                    //on item selected
                    itemstate = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Select Category")) {
                    //do nothing
                } else {
                    //on item selected
                    itemcategory = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Intent intent = new Intent(FilterHospital.this, Hospital.class);

        search.setOnClickListener(view -> {
            if (spinner_category.getSelectedItemPosition() == 0){
                Toast.makeText(FilterHospital.this, "Select a category", Toast.LENGTH_SHORT).show();
            }
            else {
                intent.putExtra("SelectedCity", itemstate);
                intent.putExtra("SelectedCategory", itemcategory);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}