package com.google.sites.medcare.PatientHistory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.sites.medcare.R;

import java.util.ArrayList;
import java.util.List;

public class Appointments extends AppCompatActivity {

    private RecyclerView AppointmentsList;
    private DatabaseReference mydB;
    private AppointmentsAdapter adapter;
    private List<AppointmentsList> appList;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        AppointmentsList= findViewById(R.id.appRecyclerView);
        AppointmentsList.setLayoutManager(new LinearLayoutManager(this));

        progressBar = findViewById(R.id.progress_bar);

        appList = new ArrayList<>();
        adapter=new AppointmentsAdapter(this,appList);
        AppointmentsList.setAdapter(adapter);
        mydB= FirebaseDatabase.getInstance().getReference("Appointments");
        mydB.keepSynced(true);

        Query query = mydB.orderByChild("avisited").equalTo(1);
        query.addListenerForSingleValueEvent(valueEventListener);
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
            appList.clear();
            if(dataSnapshot.exists()){

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    String parent = snapshot.getKey();
                    Log.d("Parent", parent);
                    AppointmentsList appointmentsList = snapshot.getValue(AppointmentsList.class);
                    appointmentsList.key = snapshot.getKey();
                    appList.add(appointmentsList);
                }

                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
