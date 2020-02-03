package com.google.sites.medcare.Schemes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.sites.medcare.R;

import java.util.ArrayList;
import java.util.List;

public class FilterPage extends AppCompatActivity {
    private Spinner spinner_state, spinner_ration, spinner_category;
    String itemstate,itemration,itemcategory;

    Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scheme_filter_page);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        SharedPreferences userDetails = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editDetails = userDetails.edit();
        String loc = userDetails.getString("State", "No Location");

        spinner_state = findViewById(R.id.spinnerState);
        spinner_ration = findViewById(R.id.spinnerRationColor);
        spinner_category = findViewById(R.id.spinnerCategory);
        search = findViewById(R.id.search);

        //Populate list for states
        List<String> states = new ArrayList<>();
        states.add(0, "No Location");
        states.add("National");
        states.add("Gujarat");
        states.add("Haryana");
        states.add("Kerala");
        states.add("Karnataka");
        states.add("MadhyaPradesh");
        states.add("Maharashtra");
        states.add("Punjab");
        states.add("Rajasthan");
        states.add("UttarPradesh");
        states.add("WestBengal");

        //Populate list for rationcolor
        List<String> ration = new ArrayList<>();
        ration.add(0, "No Ration Card");
        ration.add("Yellow");
        ration.add("White");
        ration.add("Orange");

        //Populate list for category
        List<String> category = new ArrayList<>();
        category.add(0, "None");
        category.add("Common");
        category.add("Children");
        category.add("Employee");
        category.add("Women");

        //populate the spinner1
        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter(this, R.layout.spinner_style, states);
        dataAdapter.setDropDownViewResource(R.layout.spinner_style);

        //populate the spinner2
        ArrayAdapter<String> dataAdapter2;
        dataAdapter2 = new ArrayAdapter(this, R.layout.spinner_style, ration);
        dataAdapter2.setDropDownViewResource(R.layout.spinner_style);

        //populate the spinner3
        ArrayAdapter<String> dataAdapter3;
        dataAdapter3 = new ArrayAdapter(this, R.layout.spinner_style, category);
        dataAdapter3.setDropDownViewResource(R.layout.spinner_style);


        //connecting adapter and spinner
        spinner_state.setAdapter(dataAdapter);
        int spinnerPosition = dataAdapter.getPosition(loc);
        spinner_state.setSelection(spinnerPosition);
        spinner_ration.setAdapter(dataAdapter2);
        spinner_category.setAdapter(dataAdapter3);


        spinner_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("No Location")) {
                    //do nothing
                } else {
                    //on item selected

                    itemstate = parent.getItemAtPosition(position).toString();
               //     Intent intent = new Intent(FilterPage.this, MainActivity.class);

                    // Sending value to another activity using intent.
                //    intent.putExtra("SelectedState", itemstate);


                   // startActivity(intent);
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinner_ration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("No Ration Card")) {
                    //do nothing
                } else {
                    //on item selected
                    itemration = parent.getItemAtPosition(position).toString();
                    //Intent intent = new Intent(FilterPage.this, MainActivity.class);
                    //Sending value to another activity using intent.
                    //intent.putExtra("SelectedRation", itemration);
                    //startActivity(intent);
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Intent intent = new Intent(FilterPage.this, SchemesList.class);

        spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("None")) {
                    //do nothing
                } else {
                    //on item selected
                    itemcategory = parent.getItemAtPosition(position).toString();

                    // Sending value to another activity using intent.
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spinner_state.getSelectedItemPosition() == 0){
                    Toast.makeText(FilterPage.this, "Select a state", Toast.LENGTH_SHORT).show();
                }
                else if (spinner_ration.getSelectedItemPosition() == 0){
                    Toast.makeText(FilterPage.this, "Select a ration card color", Toast.LENGTH_SHORT).show();
                }
                else if (spinner_category.getSelectedItemPosition() == 0){
                    Toast.makeText(FilterPage.this, "Select a category", Toast.LENGTH_SHORT).show();
                }
                else {
                    intent.putExtra("SelectedState", itemstate);
                    intent.putExtra("SelectedRation", itemration);
                    intent.putExtra("SelectedCategory", itemcategory);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}