package com.google.sites.medcare.Hospital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

public class Hospital extends AppCompatActivity {

    private Spinner spinner;
    private RecyclerView HospitalList;
    private DatabaseReference mydB;
    private DatabaseReference mydb2;
    private HospitalAdapter adapter;
    private List<com.google.sites.medcare.Hospital.HospitalList> hospList;
    private String hospitallocation, hospitalCity, hospitalCategory;
    private ProgressBar progressBar;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /*String DomainHolder = getIntent().getStringExtra("ListViewClickedValue");
        Log.v("Spec", DomainHolder);*/

        mydB = FirebaseDatabase.getInstance().getReference("Hospital").child("Mumbai");
        mydB.keepSynced(true);

        SharedPreferences userDetails = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editDetails = userDetails.edit();
        String loc = userDetails.getString("Location", "Mumbai");

        hospitalCity=getIntent().getStringExtra("SelectedCity");
        hospitalCategory=getIntent().getStringExtra("SelectedCategory");

        spinner=findViewById(R.id.spinner2);
        progressBar = findViewById(R.id.progress_bar);

        List<String> locations=new ArrayList<>();
        //locations.add(0,"No Location");
        locations.add("Chennai");
        locations.add("Hyderabad");
        locations.add(0,"Mumbai");
        locations.add("Navi Mumbai");
        locations.add("Pune");
        locations.add("Thiruvananthapuram");
        locations.add("Udupi");

        SharedPreferences mySharedPreferences = this.getSharedPreferences("MYPREFERENCENAME", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter(this, R.layout.spinner_style, locations);
        dataAdapter.setDropDownViewResource(R.layout.spinner_style);

        spinner.setAdapter(dataAdapter);

        //set the default according to value
        int spinnerPosition = dataAdapter.getPosition(loc);
        spinner.setSelection(spinnerPosition);

        /*spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editor.putString("Location", spinner.getSelectedItem().toString());
                editor.apply();
                Log.v("Loca", spinner.getSelectedItem().toString());
                if(parent.getItemAtPosition(position).equals("No Location")){
                    Query query=FirebaseDatabase.getInstance().getReference("Hospital").child("Mumbai").orderByChild(DomainHolder).equalTo(1);
                    query.addListenerForSingleValueEvent(valueEventListener);
                }
                else{
                    hospitallocation = parent.getItemAtPosition(position).toString();
                    Query query=FirebaseDatabase.getInstance().getReference("Hospital").child(hospitallocation).orderByChild(DomainHolder).equalTo(1);
                    query.addListenerForSingleValueEvent(valueEventListener);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        /*editor.putString("Speciality",DomainHolder);
        editor.apply();*/

        HospitalList= findViewById(R.id.myHospRecyclerview);
        HospitalList.setLayoutManager(new LinearLayoutManager(this));

        hospList=new ArrayList<>();
        adapter=new HospitalAdapter(this,hospList);
        HospitalList.setAdapter(adapter);
        /*Query query=FirebaseDatabase.getInstance().getReference("Hospital");
        query.addListenerForSingleValueEvent(valueEventListener);*/
        mydB = FirebaseDatabase.getInstance().getReference("Hospital").child(hospitalCity);
        Query query= mydB.orderByChild(hospitalCategory).equalTo(1);
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (progressBar != null) {
                progressBar.setVisibility(View.GONE);
            }
            hospList.clear();
            if(dataSnapshot.exists()){
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    com.google.sites.medcare.Hospital.HospitalList hospitalList = snapshot.getValue(com.google.sites.medcare.Hospital.HospitalList.class);
                    hospList.add(hospitalList);
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
