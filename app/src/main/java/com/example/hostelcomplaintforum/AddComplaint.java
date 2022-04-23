package com.example.hostelcomplaintforum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class AddComplaint extends AppCompatActivity {

    CheckBox anonymous;
    Toolbar toolbar;
    EditText subText, descText;
    Button sendBtn;
    FirebaseUser user;
    FirebaseFirestore fireStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_complaint);
        initializations();

    }

    private void initializations() {
        anonymous = findViewById(R.id.anonymous);
        toolbar = findViewById(R.id.postToolbar);
        sendBtn = findViewById(R.id.send);
        subText = findViewById(R.id.titleEditText);
        descText = findViewById(R.id.descriptionEditText);
        user = FirebaseAuth.getInstance().getCurrentUser();
        fireStore = FirebaseFirestore.getInstance();
    }
}