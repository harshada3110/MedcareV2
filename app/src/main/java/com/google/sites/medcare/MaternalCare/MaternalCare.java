package com.google.sites.medcare.MaternalCare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.sites.medcare.R;

public class MaternalCare extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maternal_care);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void openDiet(View view) {
        Intent openD = new Intent(MaternalCare.this, MaternalDiet.class);
        startActivity(openD);
    }

    public void openPOP(View view) {
        Intent openpop = new Intent(MaternalCare.this, PhasesOfPregnancy.class);
        startActivity(openpop);
    }
}
