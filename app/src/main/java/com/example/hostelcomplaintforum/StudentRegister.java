package com.example.hostelcomplaintforum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class StudentRegister extends AppCompatActivity {

    TextInputLayout mUsername, mEmail, mPassword, mNumber;
    LinearLayout loginText;
    Button continueBtn;
    ProgressBar progressBar;
    String userName, email, password, number;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);
        initializations();

        loginText.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), StudentLogin.class));
            finish();
        });

        continueBtn.setOnClickListener(view -> {
            userName = Objects.requireNonNull(mUsername.getEditText()).getText().toString().trim();
            email = Objects.requireNonNull(mEmail.getEditText()).getText().toString().trim();
            password = Objects.requireNonNull(mPassword.getEditText()).getText().toString().trim();
            number = Objects.requireNonNull(mNumber.getEditText()).getText().toString().trim();

            if (!validateUserName(userName) | !validateEmail(email) | !validatePassword(password) | !validateNumber(number)) {

                if (!validateUserName(userName)) {
                    mUsername.setError(null);
                    mUsername.setErrorEnabled(true);
                    mUsername.setCounterEnabled(true);
                    progressBar.setVisibility(View.GONE);
                    mUsername.setError("Username too short");
                } else {
                    mUsername.setError(null);
                    mUsername.setCounterEnabled(false);
                    mUsername.setErrorEnabled(false);
                }
                if (!validateEmail(email)) {
                    mEmail.setError(null);
                    mEmail.setErrorEnabled(true);
                    progressBar.setVisibility(View.GONE);
                    mEmail.setError("Please enter a valid email address");
                } else {
                    mEmail.setError(null);
                    mEmail.setErrorEnabled(false);
                }
                if (!validatePassword(password)) {
                    mPassword.setError(null);
                    mPassword.setErrorEnabled(true);
                    mPassword.setCounterEnabled(true);
                    progressBar.setVisibility(View.GONE);
                    mPassword.setError("Password should be at least 6 characters long");
                } else {
                    mPassword.setError(null);
                    mPassword.setErrorEnabled(false);
                    mPassword.setCounterEnabled(false);
                }
                if (!validateNumber(password)) {
                    mNumber.setError(null);
                    mNumber.setErrorEnabled(true);
                    mNumber.setCounterEnabled(true);
                    progressBar.setVisibility(View.GONE);
                    mNumber.setError("Invalid phone number");
                    Toast.makeText(StudentRegister.this, number, Toast.LENGTH_LONG).show();
                } else {
                    mNumber.setError(null);
                    mNumber.setErrorEnabled(false);
                    mNumber.setCounterEnabled(false);
                }
            } else {
                mUsername.setError(null);
                mUsername.setCounterEnabled(false);
                mUsername.setErrorEnabled(false);
                mEmail.setError(null);
                mEmail.setErrorEnabled(false);
                mPassword.setError(null);
                mPassword.setErrorEnabled(false);
                mPassword.setCounterEnabled(false);
                progressBar.setVisibility(View.VISIBLE);
                continueBtn.setVisibility(View.INVISIBLE);
                if (firebaseAuth.getCurrentUser() != null && !firebaseAuth.getCurrentUser().isEmailVerified()) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(StudentRegister.this, "Please verify the email with the link sent to you.", Toast.LENGTH_SHORT).show();
                }
                if (firebaseAuth.getCurrentUser() == null) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            firebaseAuth.getCurrentUser().sendEmailVerification().addOnSuccessListener(unused -> {
                                Toast.makeText(StudentRegister.this, "Email verification link sent to " + email, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), StudentLogin.class);
                                Map<String, Object> user = new HashMap<>();
                                user.put("uName", userName);
                                user.put("uEmail", email);
                                user.put("uTimestamp",System.currentTimeMillis());
                                user.put("uNumber", number);
                                user.put("uAuthority", "student");
                                FirebaseFirestore.getInstance()
                                        .collection("users")
                                        .document(email)
                                        .set(user)
                                        .addOnSuccessListener(unused1 -> Toast.makeText(this, "Data added to cloud", Toast.LENGTH_SHORT).show())
                                        .addOnFailureListener(e -> Toast.makeText(this, "Data not added. "+ e.getMessage(), Toast.LENGTH_SHORT).show());
                                //startActivity(intent);
                                progressBar.setVisibility(View.GONE);
                                continueBtn.setVisibility(View.VISIBLE);
                                finish();
                            }).addOnFailureListener(e -> {
                                continueBtn.setVisibility(View.VISIBLE);
                                Toast.makeText(StudentRegister.this, "Email verification link not sent. " + e.getMessage(), Toast.LENGTH_LONG).show();
                            });

                        } else {
                            Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            continueBtn.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }

    private void initializations() {
        mUsername = findViewById(R.id.userName);
        mNumber = findViewById(R.id.number);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        continueBtn = findViewById(R.id.continueBtn);
        progressBar = findViewById(R.id.progressBar);
        loginText = findViewById(R.id.loginText);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public boolean validateUserName(String s) {
        return s.length() >= 3;
    }

    public boolean validateEmail(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(s).matches();
    }

    public boolean validatePassword(String s) {
        return s.length() >= 6;
    }
    public boolean validateNumber(String s) {
        return s.length() == 10;
    }
}