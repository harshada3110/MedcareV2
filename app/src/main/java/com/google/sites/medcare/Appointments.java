package com.google.sites.medcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Appointments extends AppCompatActivity {

    private RecyclerView AppointmentsList;
    private DatabaseReference mydB;
    private AppointmentsAdapter adapter;
    private List<AppointmentsList> appList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);

        AppointmentsList= findViewById(R.id.appRecyclerView);
        AppointmentsList.setLayoutManager(new LinearLayoutManager(this));

        appList = new ArrayList<>();
        adapter=new AppointmentsAdapter(this,appList);
        AppointmentsList.setAdapter(adapter);
        mydB= FirebaseDatabase.getInstance().getReference("Appointment");

        mydB.addListenerForSingleValueEvent(valueEventListener);

    }
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            appList.clear();
            if(dataSnapshot.exists()){

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    AppointmentsList appointmentsList = snapshot.getValue(AppointmentsList.class);
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
