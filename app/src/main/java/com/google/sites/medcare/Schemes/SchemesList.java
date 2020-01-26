package com.google.sites.medcare.Schemes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.sites.medcare.R;

import java.util.ArrayList;
import java.util.List;

public class SchemesList extends AppCompatActivity {
    private RecyclerView SchemeList;
    private DatabaseReference mydB;
    private SchemesAdapter adapter;
    private List<Schemes> schemeList;
    String StateFinal,RationFinal,CategoryFinal;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schemes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        progressBar = findViewById(R.id.progress_bar);

        StateFinal=getIntent().getStringExtra("SelectedState");
        RationFinal=getIntent().getStringExtra("SelectedRation");
        CategoryFinal=getIntent().getStringExtra("SelectedCategory");

        Log.d("Stat",StateFinal);
        Log.d("Rati",RationFinal);
        Log.d("Catg",CategoryFinal);
        SchemeList=(RecyclerView)findViewById(R.id.myrecycleview);
        SchemeList.setLayoutManager(new LinearLayoutManager(this));
        schemeList=new ArrayList<>();
        adapter=new SchemesAdapter(this,schemeList);
        SchemeList.setAdapter(adapter);
        mydB= FirebaseDatabase.getInstance().getReference("Schemes");
        mydB.keepSynced(true);
        Query query= FirebaseDatabase.getInstance().getReference("Schemes").child(StateFinal)
                .orderByChild("Category")
                .equalTo(CategoryFinal);

        query.addListenerForSingleValueEvent(valueEventListener);
    }


    ValueEventListener valueEventListener=new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            schemeList.clear();
            if (progressBar != null) {
                progressBar.setVisibility(View.GONE);
            }
            if(dataSnapshot.exists()){

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Schemes schemes=snapshot.getValue(Schemes.class);
                    // "
                    if (schemes.getRationColor().equals(RationFinal) || schemes.getRationColor().equals("None")){
                        schemeList.add(schemes);
                    }
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
