package com.google.sites.medcare.Ambulance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.sites.medcare.R;

import java.util.ArrayList;
import java.util.List;

public class Ambulance extends AppCompatActivity {

    private RecyclerView AmbulanceList;
    private DatabaseReference mydB;
    private AmbulanceAdapter adapter;
    private Spinner spinner;

    private List<com.google.sites.medcare.Ambulance.AmbulanceList> ambList;
    private ProgressBar progressBar;
    private String pathologylocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mydB = FirebaseDatabase.getInstance().getReference("Ambulance");
        mydB.keepSynced(true);

        SharedPreferences userDetails = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editDetails = userDetails.edit();
        String loc = userDetails.getString("Location", "Mumbai");

        progressBar = findViewById(R.id.progress_bar);
        spinner=findViewById(R.id.spinner_amb);

        List<String> locations=new ArrayList<>();
        locations.add(0,"No Location");
        locations.add("Mumbai");
        locations.add("Udupi");
        locations.add("Manipal");
        locations.add("Pune");

        //populate the spinner
        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter(this, R.layout.spinner_style, locations);
        dataAdapter.setDropDownViewResource(R.layout.spinner_style);

        //attaching dataAdapter to spinner
        spinner.setAdapter(dataAdapter);

        //set the default according to value
        int spinnerPosition = dataAdapter.getPosition(loc);
        spinner.setSelection(spinnerPosition);

        //ArrayAdapter <String> loc_adapter = new ArrayAdapter<String> (this, R.layout.spinner_style, locations);
        //loc_adapter.setDropDownViewResource(R.layout.spinner_style);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("No Location")){
                    mydB.addListenerForSingleValueEvent(valueEventListener);
                }
                else{
                    //on item selected

                    pathologylocation=parent.getItemAtPosition(position).toString();
                    Query query=FirebaseDatabase.getInstance().getReference("Ambulance").orderByChild("Location").equalTo(pathologylocation);
                    query.addListenerForSingleValueEvent(valueEventListener);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        AmbulanceList = findViewById(R.id.myrecycleview);
        AmbulanceList.setLayoutManager(new LinearLayoutManager(this));

        ambList=new ArrayList<>();
        adapter=new AmbulanceAdapter(Ambulance.this, ambList);
        AmbulanceList.setAdapter(adapter);
        mydB= FirebaseDatabase.getInstance().getReference("Ambulance");
        mydB.addListenerForSingleValueEvent(valueEventListener);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    ValueEventListener valueEventListener=new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            ambList.clear();
            if (progressBar != null) {
                progressBar.setVisibility(View.GONE);
            }
            if(dataSnapshot.exists()){

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    com.google.sites.medcare.Ambulance.AmbulanceList ambulanceList =snapshot.getValue(com.google.sites.medcare.Ambulance.AmbulanceList.class);
                    ambList.add(ambulanceList);
                }
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
