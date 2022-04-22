package com.example.hostelcomplaintforum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;
import java.util.regex.Pattern;

public class StudentLogin extends AppCompatActivity {

    TextInputLayout mEmail, mPassword;
    ProgressBar progressBar;
    String email, password;
    Button continueBtn;
    FirebaseAuth fAuth;
    private final int RC_SIGN_IN=2;

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null) startActivity(new Intent(this, PublicFeed.class));
        finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null) startActivity(new Intent(this, PublicFeed.class));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null) startActivity(new Intent(this, PublicFeed.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        initializations();
        (findViewById(R.id.registerText)).setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), StudentRegister.class));
            finish();
        });
        continueBtn.setOnClickListener(view -> {
            email= Objects.requireNonNull(mEmail.getEditText()).getText().toString().trim();
            password= Objects.requireNonNull(mPassword.getEditText()).getText().toString().trim();

            if(!validateEmail(email) | !validatePassword(password)){

                if(!validateEmail(email)) {
                    mEmail.setError(null);
                    mEmail.setErrorEnabled(true);
                    progressBar.setVisibility(View.GONE);
                    mEmail.setError("Please enter a valid email address");
                } else {
                    mEmail.setError(null);
                    mEmail.setErrorEnabled(false);
                }
                if(!validatePassword(password)) {
                    mPassword.setError(null);
                    mPassword.setErrorEnabled(true);
                    mPassword.setCounterEnabled(true);
                    progressBar.setVisibility(View.GONE);
                    mPassword.setError("Password should be atleast 6 characters long");
                } else{
                    mPassword.setError(null);
                    mPassword.setErrorEnabled(false);
                    mPassword.setCounterEnabled(false);
                }
            }
            else{
                mEmail.setError(null);
                mEmail.setErrorEnabled(false);
                mPassword.setError(null);
                mPassword.setErrorEnabled(false);
                mPassword.setCounterEnabled(false);
                progressBar.setVisibility(View.VISIBLE);
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "User logged in", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), PublicFeed.class));
                        progressBar.setVisibility(View.GONE);
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    public boolean validateEmail(String s){
        if(s==null || s.isEmpty()){
            return false;
        }
        String emailRegex= "^[a-zA-Z0-9_+&*-]+(?:\\."+"[a-zA-Z0-9_+&*-]+)*@"+"(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern= Pattern.compile(emailRegex);
        return pattern.matcher(s).matches();
    }
    public boolean validatePassword(String s){
        return s.length() > 5;
    }

    private void initializations() {
        mEmail= findViewById(R.id.emailLogin);
        mPassword= findViewById(R.id.passwordLogin);
        continueBtn= findViewById(R.id.continueBtnLogin);
        progressBar= findViewById(R.id.progressBarLogin);
        fAuth= FirebaseAuth.getInstance();
    }
}