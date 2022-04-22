package com.example.hostelcomplaintforum;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class PublicFeed extends AppCompatActivity {


    public DrawerLayout drawerLayout;
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_feed);

        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        drawerLayout = findViewById(R.id.my_drawer_layout);
        toolbar = findViewById(R.id.public_toolBar);
        configureToolbar();
        configureNavigationDrawer();
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.public_lan) {
                Toast.makeText(PublicFeed.this, "Lan", Toast.LENGTH_SHORT).show();
            } else if (item.getItemId() == R.id.d) {
                Toast.makeText(PublicFeed.this, "Hostel D", Toast.LENGTH_SHORT).show();
            }
            return false;
        });
    }

    private void configureToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.public_toolBar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);
        actionbar.setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.public_feed_menu, menu);
        return true;
    }
    private void configureNavigationDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.my_drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.navigation_view);
        navView.setNavigationItemSelectedListener(menuItem -> {
            if(menuItem.getItemId()==R.id.resetPassword) {
                Toast.makeText(PublicFeed.this, "Reset Password", Toast.LENGTH_SHORT).show();
                return false;
            }
            return false;
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return true;
    }
}