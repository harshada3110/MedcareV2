package com.google.sites.medcare.PatientHistory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.sites.medcare.Ambulance.AmbulanceList;
import com.google.sites.medcare.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class EveryAppointment extends AppCompatActivity {

    TextView HospName, Date, Time, Prescription, Comments, Specialist;
    ImageView hospImage;
    String DisplayHospName, DisplayDate, DisplayTime, DisplayPrescription, DisplayComments, DisplaySpecialist, DisplayLocation;
    private RecyclerView PrescRList;
    private DatabaseReference mydB, mydB1, myDB2, myDB3;
    private ProgressBar progressBar;
    private PrescriptionAdapter adapter;
    private List<PrescriptionList> appList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_every_appointment);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        PrescRList = findViewById(R.id.recyclerViewPresc);
        PrescRList.setLayoutManager(new LinearLayoutManager(this));
        appList = new ArrayList<>();
        adapter=new PrescriptionAdapter(this,appList);
        PrescRList.setAdapter(adapter);

        HospName = findViewById(R.id.hospTextView);
        Specialist = findViewById(R.id.specTextView);
        Date = findViewById(R.id.dateTextView);
        Time = findViewById(R.id.timeTextView);
        Prescription = findViewById(R.id.textViewPresc);
        Comments = findViewById(R.id.textViewComm);
        hospImage = findViewById(R.id.imageViewHosp);

        progressBar = findViewById(R.id.progress_bar);

        DisplayHospName = getIntent().getStringExtra("HospName");
        DisplayDate = getIntent().getStringExtra("Date");
        DisplayTime = getIntent().getStringExtra("Time");
        DisplayPrescription = getIntent().getStringExtra("Prescription");
        DisplayComments = getIntent().getStringExtra("Comments");
        DisplaySpecialist = getIntent().getStringExtra("Specialists");
        DisplayLocation = getIntent().getStringExtra("Location");

        mydB= FirebaseDatabase.getInstance().getReference("Hospital");
        mydB1 = mydB.child(DisplayLocation);
        myDB2= FirebaseDatabase.getInstance().getReference("Appointments").child(DisplayPrescription).child("prescription");

        Log.d("Fire", String.valueOf(myDB2));

        mydB1.child(DisplayHospName).child("Photo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String photo = dataSnapshot.getValue(String.class);
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
                //Log.w("PhotoLink", photo);
                Glide.with(getApplicationContext())
                        .load(photo)
                        .into(hospImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        myDB2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        com.google.sites.medcare.PatientHistory.PrescriptionList prescriptionList = snapshot.getValue(com.google.sites.medcare.PatientHistory.PrescriptionList.class);
                        appList.add(prescriptionList);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Log.d("Fire", String.valueOf(PrescRList));

        HospName.setText(DisplayHospName);
        Date.setText(DisplayDate);
        Time.setText(DisplayTime);
        Prescription.setText("DUAGRA(1+0+1) - 7 days");
        Comments.setText(DisplayComments);
        Specialist.setText(DisplaySpecialist);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
