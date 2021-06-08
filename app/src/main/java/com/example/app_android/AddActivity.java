package com.example.app_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.HeaderViewListAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddActivity extends AppCompatActivity {
    EditText tvHeader, tvDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getSupportActionBar().hide();

        tvHeader = findViewById(R.id.tvHeaderActadd);
        tvDescription = findViewById(R.id.tvDescriptionActadd);

    }
    private void addToDatabase(String data){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Users");
        reference.child(FirebaseAuth.getInstance().getUid()).child("tasks").setValue(data);
    }
    public void onClickAddTask(View view) {
        String header, description;
        header = tvHeader.getText().toString();
        description = tvDescription.getText().toString();
        if (header.isEmpty()){
            tvHeader.setError("Header is empty");
            return;
        }
        if (description.isEmpty()){
            tvDescription.setError("Header is empty");
            return;
        }
        String data = MainActivity.currentUser.getTasks();
        addToDatabase(header + "#" + description + "#" + data);
        finish();
    }
}