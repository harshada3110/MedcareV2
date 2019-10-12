package com.google.sites.medcare.MaternalCare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.sites.medcare.R;

public class MaternalDiet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maternal_diet);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
