package com.google.sites.medcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PathologyLab extends AppCompatActivity {

    private RecyclerView PathologyList;
    private DatabaseReference mdB;
    // private DatabaseReference mdb2;
    private PathologyAdapter adapter1;
    private ProgressBar progressBar;

    private List<com.google.sites.medcare.PathologyList> pathList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pathology_lab);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        progressBar = findViewById(R.id.progress_bar);

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
                    com.google.sites.medcare.PathologyList pathologyList = snapshot.getValue(com.google.sites.medcare.PathologyList.class);
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
