package com.google.sites.medcare.Hospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.sites.medcare.R;

public class HospitalDetails extends AppCompatActivity {

    TextView HospName,HospAddress,HospAbout, HospFacilities;
    String DisplayName,DisplayImage, DisplayAddress,DisplayLocation, DisplayAbout, DisplayLoc, DisplayContact, DisplayEmail, DisplayFacilities, DisplayWebsite, DisplayLong, DisplayLat;
    ImageView HospImage;
    ImageView imageViewWeb;
    ImageView imageViewCall;
    ImageView imageViewLoc;
    Button BookAppo;
    Double initLat, initLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        HospName=findViewById(R.id.hospTextView);
        HospImage=findViewById((R.id.imageViewHosp));
        HospAddress=findViewById(R.id.addTextView);
        HospAbout=findViewById(R.id.textViewAbout);
        HospFacilities=findViewById(R.id.textViewFacilities);
        BookAppo=findViewById(R.id.buttonappointment);
        imageViewWeb=findViewById(R.id.imageViewWeb);
        imageViewCall=findViewById(R.id.imageViewCallP);
        imageViewLoc=findViewById(R.id.imageViewMapP);

        DisplayName=getIntent().getStringExtra("HospName");
        DisplayAddress=getIntent().getStringExtra("HospAddress");
        DisplayImage=getIntent().getStringExtra("HospImage");
        DisplayAbout=getIntent().getStringExtra("HospAbout");
        DisplayLoc=getIntent().getStringExtra("HospLoc");
        DisplayContact=getIntent().getStringExtra("HospContact");
        DisplayFacilities=getIntent().getStringExtra("HospFacilities");
        DisplayWebsite=getIntent().getStringExtra("HospWebsite");
        DisplayEmail=getIntent().getStringExtra("HospEmail");
        initLat=getIntent().getDoubleExtra("HospLatitude", 18.00000);
        initLong=getIntent().getDoubleExtra("HospLongitude", 72.00000);

        Glide.with(HospitalDetails.this).load(DisplayImage).into(HospImage);
        HospName.setText(DisplayName);
        HospAddress.setText(DisplayAddress);
        HospAbout.setText(DisplayAbout);
        HospFacilities.setText(DisplayFacilities);
        imageViewLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(DisplayLoc));
                startActivity(intent);
            }
        });
        imageViewCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:"+DisplayContact));
                startActivity(dialIntent);
            }
        });
        imageViewWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(DisplayWebsite); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        BookAppo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HospitalDetails.this, BookAppointment.class);
                intent.putExtra("HospName",DisplayName);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
