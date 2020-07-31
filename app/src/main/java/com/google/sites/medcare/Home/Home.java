package com.google.sites.medcare.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.sites.medcare.AboutUs;
import com.google.sites.medcare.Accident.ShakeDetector;
import com.google.sites.medcare.Accident.ShakeService;
import com.google.sites.medcare.Ambulance.Ambulance;
import com.google.sites.medcare.Appointments.RequestedAppointments;
import com.google.sites.medcare.Camps.CampsFragment;
import com.google.sites.medcare.News.NewsFragment;
import com.google.sites.medcare.PatientHistory.Appointments;
import com.google.sites.medcare.QuickAccess.QuickAccess;
import com.google.sites.medcare.R;
import com.google.sites.medcare.Reminder.Injection;
import com.google.sites.medcare.Reminder.medicine.MedicinePresenter;
import com.google.sites.medcare.Reminder.medicine.MedicineFragment;
import com.google.sites.medcare.SettingsActivity;
import com.google.sites.medcare.SignInSignUp.SignIn;
import com.google.sites.medcare.UserDetails.UserFragment;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.kommunicate.KmConversationBuilder;
import io.kommunicate.Kommunicate;
import io.kommunicate.callbacks.KmCallback;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    private HomeFragment homeFragment;
    private NewsFragment newsFragment;
    private CampsFragment campsFragment;
    private MedicineFragment medicineFragment;
    private UserFragment userFragment;
    private BlankFragment blankFragment;
    private AppBarConfiguration mAppBarConfiguration;
    SharedPreferences sharedPreferences;
    SharedPreferences userDetails;

    public Toolbar toolbar;
    public DrawerLayout drawerLayout;
    public NavController navController;
    public NavigationView navigationView;
    private MedicinePresenter mPresenter;
    private Context mCtx;

    private TextView nameTextView;
    private TextView BMITextView;
    private ImageView DPimage;

    //Accident Detection
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    static double a;
    String finalno;
    double lat,longitu;

    Geocoder geocoder;
    List<Address> addresses;

    private static  final int REQUEST_LOCATION=1;

    Button getlocationBtn;
    TextView showLocationTxt;

    LocationManager locationManager;
    String latitude,longitude,finalAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_home);

        Kommunicate.init(this,"3fe3ee8ea7630dc3d4f3248a1d0e8eead");

        startService(new Intent(getApplicationContext(), ShakeService.class));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
        }

        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {
            int shakecount = 0;
            @Override
            public void onShake(int count) {
                /*
                 * The following method, "handleShakeEvent(count):" is a stub //
                 * method you would use to setup whatever you want done once the
                 * device has been shook.
                 */
                shakecount = shakecount+1;
                if (shakecount == 3) {
                    Toast.makeText(Home.this, "Emergency alert has been sent", Toast.LENGTH_SHORT).show();
                    shakecount = 0;
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
            }
        });

        //Accident Detection
        int n=0;

        double[] latitude = new double[]{18.9951011, 19.012042, 18.9898701, 18.989443, 18.991556};
        double[] longitude = new double[]{73.1230633, 73.104424, 73.1221746, 73.117960, 73.115363};
        String[] hospital = new String[]{"DR DIPALEE MANE Hospital", "MGM Hospital", "Lifeline-muilti hospital", "Parhar hospital", "Lifecyle hospital"};
        String[] number = new String[]{"9920628520", "8108206885", "7021259334", "9594757772", "8976809733"};

        double[] result = new double[5];double[] f = new double[5];
        double[] lats = new double[5];
        double[] longs = new double[5];

        for (int i = 0; i < latitude.length; i++)
        {
            double  lat2=latitude[i];
            double lon2=longitude[i];

            double distance = haversine(lat, longitu , lat2, lon2);
            System.out.println(distance);
            result[i]=distance;
            f[i]=distance;
            lats[i] = latitude[i];
            longs[i] = longitude[i];
            n=n+1;
        }

        //accessing the elements of the specified array
        for (int i = 0; i < result.length; i++) {
            System.out.println("Element of original  result " + i + " : "+ result[i]);
        }

        //accessing the elements of the specified array
        for (int k = 0; k < f.length; k++) {
            System.out.println("Copy array of results element " + k + " : "+ f[k]);
        }

        //for getting the minimum distamce from array of results
        double temp;

        for (int i = 0; i < result.length; i++) {
            for (int j = i + 1; j < result.length; j++) {
                if (result[i] > result[j]) {
                    temp = result[i];
                    result[i] = result[j];
                    result[j] = temp;
                }
            }
        }

        System.out.print("Ascending Order:");
        for (int i = 0; i < result.length - 1; i++) {
            System.out.print(f[i] + ",");
            if (i==0){
                a= result[i];
                Log.d("Shortest value ",Double.toString(a));

            }
        }

        //accessing the elements of the specified array
        for (int k = 0; k < result.length; k++) {

            System.out.println("copy of results element " + k + " : "+ f[k]);
        }


        //System.out.print(result[result.length - 1]);

        for(int g=0;g<f.length;g++) {
            if(a==f[g]){
                System.out.println("result positiion in f array"+g);
                System.out.println("result value"+f[g]);

                System.out.println("latitude"+latitude[g]);
                System.out.println("longitude"+longitude[g]);
                System.out.println("Hospital name "+hospital[g]);
                System.out.println("Hospital number "+number[g]);
                finalno = number[g];
            }
        }


        sharedPreferences = getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);

        mMainFrame = findViewById(R.id.main_frame);
        mMainNav = findViewById(R.id.main_nav);

        homeFragment = new HomeFragment();
        newsFragment = new NewsFragment();
        campsFragment = new CampsFragment();
        medicineFragment = new MedicineFragment();
        userFragment = new UserFragment();
        blankFragment = new BlankFragment();

        mMainNav.setSelectedItemId(R.id.home_menu);
        setFragment(homeFragment);

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.news_menu:
                        setFragment(newsFragment);
                        return true;

                    case R.id.rem_menu:
                        setFragment(medicineFragment);
                        return true;

                    case R.id.home_menu:
                        setFragment(homeFragment);
                        return true;

                    case R.id.notif_menu:
                        setFragment(campsFragment);
                        return true;

                    case R.id.user_menu:
                        setFragment(userFragment);
                        return true;

                    default:
                        return false;
                }
            }
        });

        setupNavigation();
        navigationView.setCheckedItem(R.id.nav_home);
        mPresenter = new MedicinePresenter(Injection.provideMedicineRepository(Home.this), medicineFragment);
    }

    private void setupNavigation() {

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);


        userDetails = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);

        nameTextView = headerView.findViewById(R.id.nameTextView);
        BMITextView = headerView.findViewById(R.id.BMItextView);
        DPimage = headerView.findViewById(R.id.DPimageView);

        String name = userDetails.getString("Name", "MedCare");
        String email = userDetails.getString("Email", "codeblooded.io@gmail.com");
        String DP = userDetails.getString("DP", null);

        Log.v("NamePerson", name);

        nameTextView.setText(name);
        BMITextView.setText(email);
        Picasso.get().load(DP).transform(new CircleTransform()).fit().into(DPimage);

        //Glide.with(getApplicationContext()).load(DP).into(DPimage);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.nav_host_fragment), drawerLayout);
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    public void openSettings(MenuItem item) {
        Intent openSet = new Intent(Home.this, SettingsActivity.class);
        startActivity(openSet);
    }

    private void setLocale(String lang) {
        Locale locale=new Locale(lang);
        Locale.setDefault(locale);
        Configuration config=new Configuration();
        config.locale=locale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor=getSharedPreferences("Settings",MODE_PRIVATE).edit();
        editor.putString("MyLang",lang);
        editor.apply();
    }
    public void loadLocale(){
        SharedPreferences prefs=getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language=prefs.getString("MyLang","");
        setLocale(language);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finishAffinity();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        navigationView.setCheckedItem(R.id.nav_home);
        drawerLayout.closeDrawers();

        int id = menuItem.getItemId();

        switch (id) {

            case R.id.nav_home:
                navController.navigate(R.id.blankFragment);
                break;

            case R.id.nav_pathis:
                Intent openPathis = new Intent(Home.this, Appointments.class);
                startActivity(openPathis);
                break;

            case R.id.nav_ambulance:
                Intent openAmbu = new Intent(Home.this, Ambulance.class);
                startActivity(openAmbu);
                break;

            case R.id.nav_pendapps:
                Intent openPendapps = new Intent(Home.this, RequestedAppointments.class);
                startActivity(openPendapps);
                break;

            case R.id.nav_settings:
                Intent openSettings = new Intent(Home.this, SettingsActivity.class);
                startActivity(openSettings);
                break;

            case R.id.nav_quick:
                Intent openQuick = new Intent(Home.this, QuickAccess.class);
                startActivity(openQuick);
                break;

            case R.id.nav_aboutus:
                Intent openAbout = new Intent(Home.this, AboutUs.class);
                startActivity(openAbout);
                break;

            case R.id.nav_bot:
                List<String> agentList = new ArrayList();
                agentList.add("agent1@yourdomain.com"); //add your agentID
                List<String> botList = new ArrayList();
                botList.add("medcaresih-qzjan"); //enter your integrated bot Ids
                new KmConversationBuilder(this).launchConversation(new KmCallback() {
                    @Override
                    public void onSuccess(Object message) {
                        Log.d("Conversation", "Success : " + message);
                    }

                    @Override
                    public void onFailure(Object error) {
                        Log.d("Conversation", "Failure : " + error);
                    }
                });
                Toast toast = Toast.makeText(this, "Loading MedBot", Toast.LENGTH_SHORT);
                toast.show();
                break;

            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("Value", true);
                editor.commit();
                Log.d("SignInStatus", "True");
                Intent openSignIn = new Intent(Home.this, SignIn.class);
                startActivity(openSignIn);
                break;
        }
        return true;
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Value", true);
        editor.commit();
        Log.d("SignInStatus", "True");
        Intent openSignIn = new Intent(Home.this, SignIn.class);
        startActivity(openSignIn);
    }

    private void getLocation() {
        //Check Permissions again
        if (ActivityCompat.checkSelfPermission(Home.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Home.this, Manifest.permission.ACCESS_COARSE_LOCATION) !=PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        else {
            Location LocationGps= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location LocationNetwork=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location LocationPassive=locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (LocationGps !=null) {
                lat=LocationGps.getLatitude();
                longitu=LocationGps.getLongitude();

                Log.d("cam",String.valueOf(lat));
                Log.d("cum",String.valueOf(longitu));

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longitu);

                //showLocationTxt.setText("Your Location1:"+"\n"+"Latitude= "+latitude+"\n"+"Longitude= "+longitude);

            }
            else if (LocationNetwork !=null) {
                lat=LocationNetwork.getLatitude();
                longitu=LocationNetwork.getLongitude();
                Log.d("cam",String.valueOf(lat));
                Log.d("cum",String.valueOf(longitu));

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longitu);

                //showLocationTxt.setText("Your Location2:"+"\n"+"Latitude= "+latitude+"\n"+"Longitude= "+longitude);

                sendSMS();
            }
            else if (LocationPassive !=null) {
                lat=LocationPassive.getLatitude();
                longitu=LocationPassive.getLongitude();

                Log.d("cam",String.valueOf(lat));
                Log.d("cum",String.valueOf(longitu));
                latitude=String.valueOf(lat);
                longitude=String.valueOf(longitu);

                //showLocationTxt.setText("Your Location3:"+"\n"+"Latitude= "+latitude+"\n"+"Longitude= "+longitude);
            }
            else {
                Toast.makeText(this, "Can't Get Your Location", Toast.LENGTH_SHORT).show();
            }

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

    static double haversine(double lat1, double lon1, double lat2, double lon2) {
        // distance between latitudes and longitudes
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        // convert to radians
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // apply formulae
        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.pow(Math.sin(dLon / 2), 2) *
                        Math.cos(lat1) *
                        Math.cos(lat2);
        double rad = 6371;
        double c = 2 * Math.asin(Math.sqrt(a));
        return rad * c;
    }

    private void sendSMS() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
        }

        geocoder=new Geocoder(this, Locale.getDefault());
        try {
            double dem=lat;
            double dem1=longitu;
            Log.d("ccc",String.valueOf(dem));
            Log.d("ddd",String.valueOf(dem1));
            addresses=geocoder.getFromLocation(dem,dem1,1);

            String address=addresses.get(0).getAddressLine(0);
            //   String area=addresses.get(0).getLocality();
            // String city=addresses.get(0).getAdminArea();
            //   String country=addresses.get(0).getCountryName();
            //   String postalcode=addresses.get(0).getPostalCode();

            // Log.d("messy",address);
            finalAddress=address;
        } catch (IOException e) {
            e.printStackTrace();
        }

        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(Home.this, 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(Home.this, 0,
                new Intent(DELIVERED), 0);

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
        String textsms="Send an ambulance at"+" "+String.valueOf(lat)+" "+String.valueOf(longitu);
        String textsms2= "Send an ambulance at \n \nGoogle Maps:-\n"+ "http://maps.google.com/?q="+String.valueOf(lat)+","+String.valueOf(longitu)+"\n \nAddress: "+finalAddress;
        Log.d("messx",textsms2);
        SmsManager sms = SmsManager.getDefault();
        ArrayList<String> parts = sms.divideMessage(textsms2);
        //sms.sendTextMessage(finalno, null,finalAddress, sentPI, deliveredPI);
        if (parts.size() == 1) {
            String msg = parts.get(0);
            sms.sendTextMessage(finalno, null, msg, sentPI, deliveredPI);
        }
        else {
            ArrayList<PendingIntent> sentPis = new ArrayList<PendingIntent>();
            ArrayList<PendingIntent> delPis = new ArrayList<PendingIntent>();

            int ct = parts.size();
            for (int i = 0; i < ct; i++) {
                sentPis.add(i, sentPI);
                delPis.add(i, deliveredPI);
            }
            sms.sendMultipartTextMessage(finalno, null, parts, sentPis, delPis);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
    }

    /*@Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }*/

    @Override
    public void onDestroy() {
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
        super.onDestroy();
    }
}
