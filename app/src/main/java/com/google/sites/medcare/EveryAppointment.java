package com.google.sites.medcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class EveryAppointment extends AppCompatActivity {

    TextView HospName, Date, Time, Prescription, Comments, Specialist;
    ImageView hospImage;
    String DisplayHospName, DisplayDate, DisplayTime, DisplayPrescription, DisplayComments, DisplaySpecialist, DisplayLocation;
    private DatabaseReference mydB, mydB1, myDB2, myDB3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_every_appointment);

        HospName = findViewById(R.id.hospTextView);
        Specialist = findViewById(R.id.specTextView);
        Date = findViewById(R.id.dateTextView);
        Time = findViewById(R.id.timeTextView);
        Prescription = findViewById(R.id.textViewPresc);
        Comments = findViewById(R.id.textViewComm);
        hospImage = findViewById(R.id.imageViewHosp);

        DisplayHospName = getIntent().getStringExtra("HospName");
        DisplayDate = getIntent().getStringExtra("Date");
        DisplayTime = getIntent().getStringExtra("Time");
        DisplayPrescription = getIntent().getStringExtra("Prescription");
        DisplayComments = getIntent().getStringExtra("Comments");
        DisplaySpecialist = getIntent().getStringExtra("Specialists");
        DisplayLocation = getIntent().getStringExtra("Location");

        mydB= FirebaseDatabase.getInstance().getReference("Hospital");
        mydB1 = mydB.child(DisplayLocation);

        mydB1.child(DisplayHospName).child("Photo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String photo = dataSnapshot.getValue(String.class);
                Log.w("PhotoLink", photo);
                Glide.with(getApplicationContext())
                        .load(photo)
                        .into(hospImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        /*Query query=FirebaseDatabase.getInstance().getReference("Hospitals").child(DisplayLocation)
                .orderByChild("Name")
                .equalTo(DisplayHospName);*/



        HospName.setText(DisplayHospName);
        Date.setText(DisplayDate);
        Time.setText(DisplayTime);
        Prescription.setText(DisplayPrescription);
        Comments.setText(DisplayComments);
        Specialist.setText(DisplaySpecialist);
    }
}
