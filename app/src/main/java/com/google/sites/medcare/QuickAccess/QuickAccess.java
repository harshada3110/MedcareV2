package com.google.sites.medcare.QuickAccess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.sites.medcare.R;

public class QuickAccess extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_access);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
