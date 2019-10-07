package com.google.sites.medcare.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.sites.medcare.AboutUs;
import com.google.sites.medcare.Appointments.RequestedAppointments;
import com.google.sites.medcare.Camps.CampsFragment;
import com.google.sites.medcare.News.NewsFragment;
import com.google.sites.medcare.PatientHistory.Appointments;
import com.google.sites.medcare.R;
import com.google.sites.medcare.Reminder.Injection;
import com.google.sites.medcare.Reminder.medicine.MedicinePresenter;
import com.google.sites.medcare.Reminder.medicine.MedicineFragment;
import com.google.sites.medcare.SettingsActivity;
import com.google.sites.medcare.SignInSignUp.SignIn;
import com.google.sites.medcare.UserDetails.UserFragment;
import com.squareup.picasso.Picasso;

import java.util.Locale;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_home);

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

            case R.id.nav_pendapps:
                Intent openPendapps = new Intent(Home.this, RequestedAppointments.class);
                startActivity(openPendapps);
                break;

            case R.id.nav_settings:
                Intent openSettings = new Intent(Home.this, SettingsActivity.class);
                startActivity(openSettings);
                break;

            case R.id.nav_aboutus:
                Intent openAbout = new Intent(Home.this, AboutUs.class);
                startActivity(openAbout);
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
}
