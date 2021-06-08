package com.example.app_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static User currentUser;
    public RecyclerView recyclerView;
    public List<Task> alTasks;
    public Adapter adapter;
    public TextView adminMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        currentUser = new User("","","");
        adminMessage = findViewById(R.id.tvAdminActmain);
        alTasks = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new Adapter(alTasks, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                adminMessage.setText(snapshot.child("AdminMessage").getValue(String.class));
                currentUser = snapshot.child("Users").child(firebaseAuth.getUid()).getValue(User.class);
                parseTasks();
                adapter.setModel(alTasks);
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "You are "+ currentUser.getEmail(), Toast.LENGTH_LONG).show();
            }
            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    public void onClickExit(View view) {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        FirebaseAuth.getInstance().signOut();
        finish();
    }

    public void onClickAdd(View view) {
        startActivity(new Intent(MainActivity.this,AddActivity.class));
    }

    private void parseTasks(){
        List<Task> res = new ArrayList<>();
        String data = currentUser.getTasks();

        if (!data.isEmpty()){

            String[] strTascs = data.split("#");
            ArrayList<String> headliners = new ArrayList<>();
            ArrayList<String> descriptions = new ArrayList<>();
            boolean isHeadliner = true;
            for(String s: strTascs){
                if (isHeadliner){
                    headliners.add(s);
                    isHeadliner = false;
                }
                else{
                    descriptions.add(s);
                    isHeadliner = true;
                }
            }
            for(int i = 0; i < headliners.size(); i++){
                Task task = new Task(headliners.get(i),descriptions.get(i));
                res.add(task);
            }
            alTasks = res;
        }
        else{
            alTasks = new ArrayList<>();
            currentUser.setTasks("");
            adapter.notifyDataSetChanged();
        }
    }

    public void onClickAdmin(View view) {
        if (currentUser.getAdminStatus().equals("true")){
            startActivity(new Intent(MainActivity.this, EditAdminMessageActivity.class));

        }
    }
}
