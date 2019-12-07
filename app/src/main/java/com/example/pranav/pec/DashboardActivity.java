package com.example.pranav.pec;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        firebaseAuth = FirebaseAuth.getInstance();
        Fragment fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment = new FragmentDashboard();
        fragmentTransaction.add(R.id.framelayout, fragment);
        fragmentTransaction.commit();


        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.navigation_dashboard:
                        fragment = new FragmentDashboard();
                        fragmentTransaction.replace(R.id.framelayout, fragment);
                        fragmentTransaction.commit();
                        return true;
//                    case R.id.navigation_attendance:
//
//                        return true;
                    case R.id.navigation_profile:
                        fragment = new FragmentProfile();
                        fragmentTransaction.replace(R.id.framelayout, fragment);
                        fragmentTransaction.commit();
                        return true;
                }
                return false;
            }
        });


    }

}
