package com.google.sites.medcare.Schemes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.sites.medcare.R;

import java.util.ArrayList;
import java.util.List;

public class FilterPage extends AppCompatActivity {
    private Spinner spinner_state, spinner_ration, spinner_category;
    String itemstate,itemration,itemcategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scheme_filter_page);
        spinner_state = findViewById(R.id.spinnerState);
        spinner_ration = findViewById(R.id.spinnerRationColor);
        spinner_category = findViewById(R.id.spinnerCategory);

        //Populate list for states
        List<String> states = new ArrayList<>();
        states.add(0, "No Location");
        states.add("Maharashtra");
        states.add("Punjab");
        states.add("Haryana");
        states.add("Kerala");
        states.add("Rajasthan");
        states.add("Gujarat");

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
        category.add("Government");
        category.add("Farmers");
        category.add("Private");

        //populate the spinner1
        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, states);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //populate the spinner2
        ArrayAdapter<String> dataAdapter2;
        dataAdapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, ration);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //populate the spinner3
        ArrayAdapter<String> dataAdapter3;
        dataAdapter3 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, category);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        //connecting adapter and spinner
        spinner_state.setAdapter(dataAdapter);
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
                   // Intent intent = new Intent(FilterPage.this, MainActivity.class);

                    // Sending value to another activity using intent.
                   // intent.putExtra("SelectedRation", itemration);


                    // startActivity(intent);
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("None")) {
                    //do nothing
                } else {
                    //on item selected

                    itemcategory = parent.getItemAtPosition(position).toString();
                     Intent intent = new Intent(FilterPage.this, SchemesList.class);

                    // Sending value to another activity using intent.
                    intent.putExtra("SelectedState", itemstate);
                    intent.putExtra("SelectedRation", itemration);
                    intent.putExtra("SelectedCategory", itemcategory);



                     startActivity(intent);
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}