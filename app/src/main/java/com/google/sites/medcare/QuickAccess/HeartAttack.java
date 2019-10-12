package com.google.sites.medcare.QuickAccess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.sites.medcare.R;

public class HeartAttack extends AppCompatActivity {

    Button bt, bt1;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_attack);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        bt=findViewById(R.id.audioHeart);
        bt1=findViewById(R.id.videoHeart);

        bt.setOnClickListener(new View.OnClickListener() {
            int count=1;
            @Override
            public void onClick(View v) {

                if(count==1) {
                    mediaPlayer= MediaPlayer.create(HeartAttack.this,R.raw.snakebite);
                    mediaPlayer.start();

                    Log.d("if","if");
                    count=2;
                }
                else{
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    count=1;
                }

            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=WDh0LdqCeYg"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.google.android.youtube");
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
