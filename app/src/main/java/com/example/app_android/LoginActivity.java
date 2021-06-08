package com.example.app_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText etEmail, etPassword;
    Button btnLogin;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBarActlog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        etEmail = findViewById(R.id.tvEmailActlog);
        etPassword = findViewById(R.id.tvPasswordActlog);
        progressBarActlog = findViewById(R.id.progressBarActlog);
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    public void openRegistration(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    public void onClickLogin(View view) {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (email.isEmpty()){
            etEmail.setError("Email is empty");
            return;
        }
        if (password.isEmpty()){
            etPassword.setError("Password is empty");
            return;
        }
        progressBarActlog.setVisibility(View.VISIBLE);

        login(email, password);
    }

    private void login (String email, String password){
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()){
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
            else {
                Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                etPassword.setText("");
                progressBarActlog.setVisibility(View.INVISIBLE);
            }
        }).addOnFailureListener(e -> Toast.makeText(LoginActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}