package com.google.sites.medcare;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.sites.medcare.Home.Home;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    CardView cardViewLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        cardViewLang = findViewById(R.id.languageCard);
        cardViewLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                languageselectdialog();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void languageselectdialog() {
        final String[] listitems={"English","हिंदी", "ಕನ್ನಡ"};
        AlertDialog.Builder mBuilder=new AlertDialog.Builder(SettingsActivity.this);
        mBuilder.setTitle("Choose Language");
        mBuilder.setSingleChoiceItems(listitems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if(i==0){
                    setLocale("en");
                    recreate();
                    Intent openHome = new Intent(SettingsActivity.this, Home.class);
                    startActivity(openHome);
                }

                else if(i==1){
                    setLocale("hi");
                    recreate();
                    Intent openHome = new Intent(SettingsActivity.this, Home.class);
                    startActivity(openHome);
                }

                else if(i==2){
                    setLocale("kn");
                    recreate();
                    Intent openHome = new Intent(SettingsActivity.this, Home.class);
                    startActivity(openHome);
                    Log.d("cc","zz");
                }

                dialog.dismiss();
            }
        });

        AlertDialog mDialog=mBuilder.create();
        mDialog.show();
    }

    private void setLocale(String lang) {

        Locale locale=new Locale(lang);
        Locale.setDefault(locale);
        Configuration config=new Configuration();
        config.locale=locale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor = getSharedPreferences("Settings",MODE_PRIVATE).edit();
        editor.putString("MyLang",lang);
        editor.apply();
    }

    public void loadLocale(){
        SharedPreferences prefs=getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language=prefs.getString("MyLang","");
        setLocale(language);
    }
}
