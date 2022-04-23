package com.example.hostelcomplaintforum;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PublicFeed extends AppCompatActivity {


    public DrawerLayout drawerLayout;
    public Toolbar toolbar;
    CardView addBtn;
    RecyclerView recyclerView;
    ArrayList<ComplaintItem> mFiles = new ArrayList<ComplaintItem>();
    ComplaintAdapter complaintAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_feed);
        drawerLayout = findViewById(R.id.my_drawer_layout);
        toolbar = findViewById(R.id.public_toolBar);
        addBtn = findViewById(R.id.add_complaint);
        recyclerView = findViewById(R.id.public_recycler_view);
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
        if (mFiles.size() > 1 || complaintAdapter == null) getFiles();
        addBtn.setOnClickListener(view -> startActivity(new Intent(this, AddComplaint.class)));
        //getFiles();
    }

    private void getFiles() {
        FirebaseFirestore.getInstance()
                .collection("complaints")
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        QuerySnapshot querySnapshots = task.getResult();
                        for(DocumentSnapshot snapshot: querySnapshots){
                            String subject, desc, mode, name, email, room, hostel, reply, state;
                            long time;
                            subject = snapshot.getString("cSubject");
                            desc = snapshot.getString("cDesc");
                            mode = snapshot.getString("cMode");
                            name = snapshot.getString("cName");
                            email = snapshot.getString("email");
                            room = snapshot.getString("cRoom");
                            hostel = snapshot.getString("cHostel");
                            state = snapshot.getString("cState");
                            reply= snapshot.getString("cReply");
                            time =  Long.parseLong(snapshot.getString("cTime"));
                            mFiles.add(new ComplaintItem(subject, desc, mode, name, email, room, hostel, time, state, reply));
                            if (complaintAdapter != null)
                                complaintAdapter.updateData(mFiles);
                            else {
                                if (mFiles.size() >= 1) {
                                    complaintAdapter = new ComplaintAdapter(getApplicationContext(), mFiles);
                                    recyclerView.setAdapter(complaintAdapter);
                                    if (getApplicationContext() != null)
                                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()
                                                , RecyclerView.VERTICAL, false));
                                }
                            }
                        }
                        if (mFiles.size() >= 1) {
                            complaintAdapter = new ComplaintAdapter(getApplicationContext(), mFiles);
                            recyclerView.setAdapter(complaintAdapter);
                            if (getApplicationContext() != null)
                                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()
                                        , RecyclerView.VERTICAL, false));
                        }
                    }
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
            if(menuItem.getItemId()==R.id.logout) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, MainActivity.class));
                Toast.makeText(PublicFeed.this, "User Signed Out", Toast.LENGTH_SHORT).show();
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