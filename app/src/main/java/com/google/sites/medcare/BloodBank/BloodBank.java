package com.google.sites.medcare.BloodBank;

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

public class BloodBank extends AppCompatActivity {

    private RecyclerView BloodBankList;
    private DatabaseReference mydB,mydB2;
    private BloodAdapter adapter;
    private List<com.google.sites.medcare.BloodBank.BloodBankList> bloodList;
    private ProgressBar progressBar;
    private Spinner spinner;
    private String BBlocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_bank);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        SharedPreferences userDetails = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editDetails = userDetails.edit();
        String loc = userDetails.getString("Location", "Mumbai");

        BloodBankList=(RecyclerView) findViewById(R.id.bloodrecycleview);
        progressBar = findViewById(R.id.progress_bar);
        BloodBankList.setHasFixedSize(true);
        BloodBankList.setLayoutManager(new LinearLayoutManager(this));
        spinner=findViewById(R.id.spinner_bb);

        List<String> locations=new ArrayList<>();
        locations.add(0,"No Location");
        locations.add("Mumbai");
        locations.add("Udupi");
        locations.add("Bangalore");
        locations.add("Alappuzha");
        locations.add("Mysore");

        bloodList=new ArrayList<>();
        adapter=new BloodAdapter(this,bloodList);
        BloodBankList.setAdapter(adapter);
        mydB= FirebaseDatabase.getInstance().getReference("BloodBank");
        mydB.keepSynced(true);

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter(this, R.layout.spinner_style, locations);
        dataAdapter.setDropDownViewResource(R.layout.spinner_style);

        spinner.setAdapter(dataAdapter);

        //set the default according to value
        int spinnerPosition = dataAdapter.getPosition(loc);
        spinner.setSelection(spinnerPosition);

        mydB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
                bloodList.clear();
                if(dataSnapshot.exists()){

                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        com.google.sites.medcare.BloodBank.BloodBankList camps = snapshot.getValue(com.google.sites.medcare.BloodBank.BloodBankList.class);
                        bloodList.add(camps);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("No Location")){
                    mydB = FirebaseDatabase.getInstance().getReference("BloodBank");
                    mydB.addListenerForSingleValueEvent(valueEventListener);
                }
                else{
                    BBlocation=parent.getItemAtPosition(position).toString();
                    Query query=FirebaseDatabase.getInstance().getReference("BloodBank").orderByChild("Place").equalTo(BBlocation);
                    query.addListenerForSingleValueEvent(valueEventListener);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (progressBar != null) {
                progressBar.setVisibility(View.GONE);
            }
            bloodList.clear();
            if(dataSnapshot.exists()){

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    com.google.sites.medcare.BloodBank.BloodBankList camps = snapshot.getValue(com.google.sites.medcare.BloodBank.BloodBankList.class);
                    bloodList.add(camps);
                }
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
