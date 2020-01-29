package com.google.sites.medcare.Appointments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.sites.medcare.R;

import java.util.ArrayList;
import java.util.List;

public class RequestedAppointments extends AppCompatActivity {

    private RecyclerView RequestedAppointmentsList;
    private DatabaseReference mydB;
    private RequestedAppointmentsAdapter adapter;
    private List<RequestedAppointmentsList> appList;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested_appointments);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        RequestedAppointmentsList= findViewById(R.id.appRecyclerView);
        RequestedAppointmentsList.setLayoutManager(new LinearLayoutManager(this));

        progressBar = findViewById(R.id.progress_bar);

        appList = new ArrayList<>();
        adapter=new RequestedAppointmentsAdapter(this,appList);
        RequestedAppointmentsList.setAdapter(adapter);
        mydB = FirebaseDatabase.getInstance().getReference("Appointments");
        mydB.keepSynced(true);

        Query query = mydB.orderByChild("avisited").equalTo(0);
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

            SharedPreferences userDetails = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
            SharedPreferences.Editor editDetails = userDetails.edit();
            String email = userDetails.getString("Email", null);

            if(dataSnapshot.exists()){

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    RequestedAppointmentsList requestedAppointmentsList = snapshot.getValue(RequestedAppointmentsList.class);

                    if (requestedAppointmentsList.getEmail().equals(email)){
                        appList.add(requestedAppointmentsList);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
