package com.google.sites.medcare.MaternalCare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.sites.medcare.R;

public class HowsMyBaby extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hows_my_baby);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
