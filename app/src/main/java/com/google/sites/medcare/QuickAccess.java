package com.google.sites.medcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.google.sites.medcare.Home.Home;
import com.google.sites.medcare.PatientHistory.Appointments;

public class QuickAccess extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_access);
    }

    public void openSnake(View view) {
        Intent openPathis = new Intent(QuickAccess.this, SnakeBite.class);
        startActivity(openPathis);
    }

    public void openBurn(View view) {
        Intent openPathis = new Intent(QuickAccess.this, Burns.class);
        startActivity(openPathis);
    }

    public void openBone(View view) {
        Intent openPathis = new Intent(QuickAccess.this, BoneFracture.class);
        startActivity(openPathis);
    }

    public void openHeart(View view) {
        Intent openPathis = new Intent(QuickAccess.this, HeartAttack.class);
        startActivity(openPathis);
    }
}
