package com.google.sites.medcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Appointments extends AppCompatActivity {

    private ListView listViewappo;
    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayList <String> appointmentsListslist;
    ArrayAdapter <AppointmentsList> appointmentsListArrayAdapter;
    AppointmentsList appointmentsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);

        listViewappo = findViewById(R.id.listViewAppo);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("Appointments");
        appointmentsListslist = new ArrayList<>();
        //appointmentsListArrayAdapter = new ArrayAdapter<>(this, R.layout.listview_appo_item, R.id.textViewHospName, appointmentsListslist);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    //String questionKey = dataSnapshot.getKey();
                    String hospName = dataSnapshot.child("Hospital Name").getValue(String.class);
                    String spec = dataSnapshot.child("Specialist").getValue(String.class);
                    String date = dataSnapshot.child("Weekday").getValue(String.class);
                    appointmentsList = dataSnapshot1.getValue(AppointmentsList.class);
                    //appointmentsListslist.add(appointmentsList);
                }
                listViewappo.setAdapter(appointmentsListArrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
