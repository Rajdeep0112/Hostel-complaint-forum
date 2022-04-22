package com.example.hostelcomplaintforum;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    CardView student, faculty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializations();
        btnFuncs();
    }

    private void btnFuncs() {
        student.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), StudentLogin.class));
        });
    }

    private void initializations() {
        student = findViewById(R.id.student_btn);
        faculty = findViewById(R.id.faculty_btn);
    }


}