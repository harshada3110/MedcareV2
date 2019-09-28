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

public class Pharmacy extends AppCompatActivity {

    private RecyclerView PharmacyList;
    private DatabaseReference mdB2;
    private PharmacyAdapter adapter2;

    private List<com.google.sites.medcare.PharmacyList> pharmList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        PharmacyList = findViewById(R.id.mypharmrecycleview);
        PharmacyList.setLayoutManager(new LinearLayoutManager(this));

        pharmList = new ArrayList<>();
        adapter2 = new PharmacyAdapter(Pharmacy.this,pharmList);
        PharmacyList.setAdapter(adapter2);
        mdB2= FirebaseDatabase.getInstance().getReference("Pharmacy");
        mdB2.addListenerForSingleValueEvent(valueEventListener);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            pharmList.clear();
            if(dataSnapshot.exists()){

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    com.google.sites.medcare.PharmacyList pharmacyList = snapshot.getValue(com.google.sites.medcare.PharmacyList.class);
                    //Log.i("data",""+pharmacy);
                    pharmList.add(pharmacyList);
                }
                adapter2.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
