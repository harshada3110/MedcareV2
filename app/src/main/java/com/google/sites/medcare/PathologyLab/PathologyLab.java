package com.google.sites.medcare.PathologyLab;

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

public class PathologyLab extends AppCompatActivity {

    private RecyclerView PathologyList;
    private DatabaseReference mdB;
    private PathologyAdapter adapter1;
    private ProgressBar progressBar;
    private Spinner spinner;

    private List<com.google.sites.medcare.PathologyLab.PathologyList> pathList;
    private String pathologylocation;   //for filtering

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pathology_lab);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mdB = FirebaseDatabase.getInstance().getReference("PathologyLab");
        mdB.keepSynced(true);

        SharedPreferences userDetails = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editDetails = userDetails.edit();
        String loc = userDetails.getString("Location", "Mumbai");

        progressBar = findViewById(R.id.progress_bar);
        spinner=findViewById(R.id.spinner2);

        List<String> locations=new ArrayList<>();
        locations.add(0,"No Location");
        locations.add("Mumbai");
        locations.add("Udupi");
        //locations.add("Haryana");
        locations.add("Alappuzha");
        locations.add("Delhi");
        //locations.add("Hyderabad");
        locations.add("Chandigarh");
        locations.add("Ahmedabad");
        locations.add("Calcutta");

        //populate the spinner
        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter(this, R.layout.spinner_style, locations);
        dataAdapter.setDropDownViewResource(R.layout.spinner_style);

        //attaching dataAdapter to spinner
        spinner.setAdapter(dataAdapter);

        //ArrayAdapter <String> loc_adapter = new ArrayAdapter<String> (this, R.layout.spinner_style, locations);
        //loc_adapter.setDropDownViewResource(R.layout.spinner_style);

        //set the default according to value
        int spinnerPosition = dataAdapter.getPosition(loc);
        spinner.setSelection(spinnerPosition);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("No Location")){
                    mdB = FirebaseDatabase.getInstance().getReference("PathologyLab");
                    mdB.addListenerForSingleValueEvent(valueEventListener);
                }
                else{
                    //on item selected
                    pathologylocation=parent.getItemAtPosition(position).toString();
                    Query query=FirebaseDatabase.getInstance().getReference("PathologyLab").orderByChild("Location").equalTo(pathologylocation);
                    query.addListenerForSingleValueEvent(valueEventListener);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        PathologyList= findViewById(R.id.mypathrecycleview);
        PathologyList.setLayoutManager(new LinearLayoutManager(this));

        pathList = new ArrayList<>();
        adapter1 = new PathologyAdapter(PathologyLab.this,pathList);
        PathologyList.setAdapter(adapter1);
        mdB = FirebaseDatabase.getInstance().getReference("PathologyLab");
        mdB.addListenerForSingleValueEvent(valueEventListener);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (progressBar != null) {
                progressBar.setVisibility(View.GONE);
            }
            pathList.clear();
            if(dataSnapshot.exists()){

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    com.google.sites.medcare.PathologyLab.PathologyList pathologyList = snapshot.getValue(com.google.sites.medcare.PathologyLab.PathologyList.class);
                    pathList.add(pathologyList);
                }
                adapter1.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
