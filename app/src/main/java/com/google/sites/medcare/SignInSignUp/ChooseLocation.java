package com.google.sites.medcare.SignInSignUp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.sites.medcare.Home.Home;
import com.google.sites.medcare.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ChooseLocation extends AppCompatActivity {

    ListView listView;
    ArrayList<String> list;
    ArrayAdapter<String > adapter;

    static double a;
    String finalno;
    double lat, longitu;

    Geocoder geocoder;
    List<Address> addresses;

    private static  final int REQUEST_LOCATION=1;

    Button getlocationBtn;

    LocationManager locationManager;
    String latitude,longitude,finalAddress;

    SharedPreferences userDetails;

    private FusedLocationProviderClient mFusedLocationClient;
    private int locationRequestCode = 1000;
    private double wayLatitude = 0.0, wayLongitude = 0.0;
    private LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_location);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        userDetails = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);

        getlocationBtn=findViewById(R.id.getLocation);


        getlocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
                //Check gps is enable or not
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    //Write Function To enable gps
                    OnGPS();
                }
                else {
                    //GPS is already On then
                    getLocation();
                }
            }
        });

        listView = findViewById(R.id.locList);

        list = new ArrayList<>();
        list.add("Mumbai");
        list.add("Manipal");
        list.add("Pune");
        list.add("Thiruvananthapuram");
        /*list.add("Lychee");
        list.add("Gavava");
        list.add("Peech");
        list.add("Melon");
        list.add("Watermelon");
        list.add("Papaya");*/

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view,int position, long arg3) {
                        SharedPreferences.Editor editDetails = userDetails.edit();
                        String loc = listView.getItemAtPosition(position).toString();
                        //Toast.makeText(ChooseLocation.this, loc, Toast.LENGTH_SHORT).show();
                        editDetails.putString("Location", loc);
                        editDetails.commit();
                        Intent openHome = new Intent(ChooseLocation.this, Home.class);
                        startActivity(openHome);
                    }
                }
        );
    }

    private void getLocation() {
        //Check Permissions again
        if (ActivityCompat.checkSelfPermission(ChooseLocation.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ChooseLocation.this, Manifest.permission.ACCESS_COARSE_LOCATION) !=PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        else {
            Location LocationGps= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location LocationNetwork=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location LocationPassive=locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(20 * 1000);

            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                if (location != null) {
                    wayLatitude = location.getLatitude();
                    wayLongitude = location.getLongitude();
                    lat = wayLatitude;
                    longitu = wayLongitude;
                    latitude=String.valueOf(wayLatitude);
                    longitude=String.valueOf(wayLongitude);

                    setLoc();
                }
                else {
                    Toast.makeText(this, "Can't Get Your Location", Toast.LENGTH_SHORT).show();
                }
            });

            /*if (LocationGps !=null) {

                mFusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                    if (location != null) {
                        wayLatitude = location.getLatitude();
                        wayLongitude = location.getLongitude();
                    }
                });

                lat=LocationGps.getLatitude();
                longitu=LocationGps.getLongitude();

                Log.d("cam",String.valueOf(lat));
                Log.d("cum",String.valueOf(longitu));

                latitude=String.valueOf(wayLatitude);
                longitude=String.valueOf(wayLongitude);

            }
            else if (LocationNetwork !=null) {
                lat=LocationNetwork.getLatitude();
                longitu=LocationNetwork.getLongitude();
                Log.d("cam",String.valueOf(lat));
                Log.d("cum",String.valueOf(longitu));

                latitude=String.valueOf(wayLatitude);
                longitude=String.valueOf(wayLongitude);
                setLoc();
            }
            else if (LocationPassive !=null) {
                lat=LocationPassive.getLatitude();
                longitu=LocationPassive.getLongitude();

                Log.d("cam",String.valueOf(lat));
                Log.d("cum",String.valueOf(longitu));

                latitude=String.valueOf(wayLatitude);
                longitude=String.valueOf(wayLongitude);
            }
            else {
                Toast.makeText(this, "Can't Get Your Location", Toast.LENGTH_SHORT).show();
            }*/
            //Thats All Run Your App
        }
        Log.d("xxx",String.valueOf(lat));
        Log.d("yyyy",String.valueOf(longitu));
        //geo-location function
    }

    private void OnGPS() {

        final AlertDialog.Builder builder= new AlertDialog.Builder(this);

        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        final AlertDialog alertDialog=builder.create();
        alertDialog.show();

    }

    private void setLoc() {

        geocoder=new Geocoder(this, Locale.getDefault());
        SharedPreferences.Editor editDetails = userDetails.edit();

        try {
            double dem=lat;
            double dem1=longitu;
            Log.d("ccc",String.valueOf(dem));
            Log.d("ddd",String.valueOf(dem1));
            addresses=geocoder.getFromLocation(dem,dem1,1);

            String address=addresses.get(0).getAddressLine(0);
            String area=addresses.get(0).getLocality();
            editDetails.putString("Location", area);
            Toast.makeText(ChooseLocation.this, area, Toast.LENGTH_SHORT).show();
            String state=addresses.get(0).getAdminArea();
            editDetails.putString("State", state);
            //String country=addresses.get(0).getCountryName();
            //String postalcode=addresses.get(0).getPostalCode();
            editDetails.commit();
            Intent openHome = new Intent(ChooseLocation.this, Home.class);
            startActivity(openHome);

            //finalAddress=address;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.loc_menu, menu);
        MenuItem searchViewItem = menu.findItem(R.id.app_bar_search);
        final SearchView searchView = (SearchView) searchViewItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
             /*   if(list.contains(query)){
                    adapter.getFilter().filter(query);
                }else{
                    Toast.makeText(MainActivity.this, "No Match found",Toast.LENGTH_LONG).show();
                }*/
                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
