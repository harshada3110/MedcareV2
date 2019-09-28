package com.google.sites.medcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Ambulance extends AppCompatActivity {

    private RecyclerView AmbulanceList;
    private DatabaseReference mydB;
    private AmbulanceAdapter adapter;

    private List<com.google.sites.medcare.AmbulanceList> ambList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        AmbulanceList = findViewById(R.id.myrecycleview);
        AmbulanceList.setLayoutManager(new LinearLayoutManager(this));

        ambList=new ArrayList<>();
        adapter=new AmbulanceAdapter(Ambulance.this,ambList);
        AmbulanceList.setAdapter(adapter);
        mydB= FirebaseDatabase.getInstance().getReference("Ambulance");
        //mydb2=mydB.child("Mumbai");
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
            if(dataSnapshot.exists()){

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    com.google.sites.medcare.AmbulanceList ambulanceList =snapshot.getValue(com.google.sites.medcare.AmbulanceList.class);
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
