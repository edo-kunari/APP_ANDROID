package com.example.app_android;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    EditText etEmail, etPassword, etPassword2;
    Button btnRegistartion;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBarActreg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        etEmail = findViewById(R.id.tvEmailActreg);
        etPassword = findViewById(R.id.tvPasswordActreg);
        etPassword2 = findViewById(R.id.tvPasswordActreg2);
        btnRegistartion = findViewById(R.id.btnRegistartionActreg);
        progressBarActreg = findViewById(R.id.progressBarActreg);

        firebaseAuth = FirebaseAuth.getInstance();



    }

    public void onClickRegistartion(View view) {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String password2 = etPassword2.getText().toString().trim();
        if (email.isEmpty()){
            etEmail.setError("Email is empty");
            return;
        }
        if (password.isEmpty()){
            etPassword.setError("Password is empty");
            return;
        }
        if (password2.isEmpty()){
            etPassword2.setError("Password is empty");
            return;
        }
        if (!password.equals(password2)) {
            etPassword2.setError("Password mismatch");
            etPassword.setText("");
            etPassword2.setText("");
            return;
        }
        progressBarActreg.setVisibility(View.VISIBLE);
        register(email, password);
    }
    private void register(String email, String password){
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                HashMap<Object, String> hashMap = new HashMap<>();
                hashMap.put("email", email);
                hashMap.put("adminStatus", "false");
                hashMap.put("tasks", "");
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference("Users");
                reference.child(firebaseAuth.getUid()).setValue(hashMap);
                Toast.makeText(RegisterActivity.this, "Registation successfully", Toast.LENGTH_LONG).show();
                finish();

            }

            else {
                Toast.makeText(RegisterActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        progressBarActreg.setVisibility(View.INVISIBLE);
    }

}