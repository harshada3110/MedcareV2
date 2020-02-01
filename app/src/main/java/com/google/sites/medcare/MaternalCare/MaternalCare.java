package com.google.sites.medcare.MaternalCare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.sites.medcare.R;

public class MaternalCare extends AppCompatActivity {

    ImageView bt1;
    public int RequestCode;
    private final int RequestCode1=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maternal_care);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        bt1 = findViewById(R.id.sosBtn);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("sds", "sdsd");
                if (ActivityCompat.checkSelfPermission(MaternalCare.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MaternalCare.this, new String[]{Manifest.permission.SEND_SMS}, 1);
                }
                sendSMS();
            }
        });
    }

    private void sendSMS() {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        SharedPreferences userDetails = this.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        String nEmer = userDetails.getString("EmerContact", null);

        if (nEmer == null){
            Toast.makeText(MaternalCare.this, "No emergency number assigned", Toast.LENGTH_SHORT).show();
        }
        else {
            PendingIntent sentPI = PendingIntent.getBroadcast(MaternalCare.this, 0, new Intent(SENT), 0);

            PendingIntent deliveredPI = PendingIntent.getBroadcast(MaternalCare.this, 0, new Intent(DELIVERED), 0);

            //---when the SMS has been sent---
            registerReceiver(new BroadcastReceiver(){
                @Override
                public void onReceive(Context arg0, Intent arg1) {
                    switch (getResultCode())
                    {
                        case Activity.RESULT_OK:
                            Toast.makeText(getBaseContext(), "SMS sent",
                                    Toast.LENGTH_SHORT).show();
                            break;
                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                            Toast.makeText(getBaseContext(), "Generic failure",
                                    Toast.LENGTH_SHORT).show();
                            break;
                        case SmsManager.RESULT_ERROR_NO_SERVICE:
                            Toast.makeText(getBaseContext(), "No service",
                                    Toast.LENGTH_SHORT).show();
                            break;
                        case SmsManager.RESULT_ERROR_NULL_PDU:
                            Toast.makeText(getBaseContext(), "Null PDU",
                                    Toast.LENGTH_SHORT).show();
                            break;
                        case SmsManager.RESULT_ERROR_RADIO_OFF:
                            Toast.makeText(getBaseContext(), "Radio off",
                                    Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }, new IntentFilter(SENT));

            //---when the SMS has been delivered---
            registerReceiver(new BroadcastReceiver(){
                @Override
                public void onReceive(Context arg0, Intent arg1) {
                    switch (getResultCode())
                    {
                        case Activity.RESULT_OK:
                            Toast.makeText(getBaseContext(), "SMS delivered",
                                    Toast.LENGTH_SHORT).show();
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(getBaseContext(), "SMS not delivered",
                                    Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }, new IntentFilter(DELIVERED));

            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(nEmer, null, "Medical Emergency", sentPI, deliveredPI);
        }
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
