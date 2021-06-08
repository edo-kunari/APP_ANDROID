package com.example.app_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.FHolder>{

    List<Task> model;
    Context context;

    public Adapter (List<Task> model, Context context){
        this.model = model;
        this.context = context;
    }
    @NonNull
    @Override
    public FHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.tasks_card, parent, false);
        return new FHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.FHolder holder, int position) {
        holder.tvHedliner.setText(model.get(position).getHeadliner());
        holder.tvDesc.setText(model.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class FHolder extends RecyclerView.ViewHolder{

        TextView tvHedliner, tvDesc;
        ImageButton imgButton;

        public FHolder(@NonNull View itemView){
            super(itemView);
            tvHedliner = itemView.findViewById(R.id.tvHeadlinerActmain);
            tvDesc = itemView.findViewById(R.id.tvDescActmain);
            imgButton = itemView.findViewById(R.id.imgButtonDelete);

            imgButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String currentData;
                    currentData = MainActivity.currentUser.getTasks();
                    String header = tvHedliner.getText().toString();
                    String description = tvDesc.getText().toString();

                    String[] tasks = currentData.split("#");
                    for(int i = 0; i < tasks.length-1; i++){
                        if (tasks[i].equals(header)){
                            if (tasks[i+1].equals(description)){
                                tasks = removeFromArr(tasks,i);
                                tasks = removeFromArr(tasks,i);
                                break;
                            }
                        }
                    }
                    currentData = "";
                    for (int i = 0; i < tasks.length; i++){
                        currentData += tasks[i];
                        if (!(i == tasks.length-1)){
                            currentData+="#";
                        }
                    }
                    addToDatabase(currentData);
                }
            });
        }
    }
    public void setModel(List<Task> model) {
        this.model = model;
    }
    private void addToDatabase(String data){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Users");
        reference.child(FirebaseAuth.getInstance().getUid()).child("tasks").setValue(data);
    }
    private String[] removeFromArr(String[] strings, int i){
        ArrayList<String> a = new ArrayList<>(Arrays.asList(strings));
        a.remove(i);
        strings = new String[a.size()];
        a.toArray(strings);
        return strings;
    }
}
