package com.example.app_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditAdminMessageActivity extends AppCompatActivity {
    EditText etAdminMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_admin_message);
        getSupportActionBar().hide();

        etAdminMessage = findViewById(R.id.tvAdminMessage);
    }
    public void onClickAddAdmin(View view) {
        if (etAdminMessage.getText().toString().isEmpty()){
            etAdminMessage.setError("Message is empty");
            return;
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("AdminMessage");
        reference.setValue(etAdminMessage.getText().toString());
        finish();
    }
}