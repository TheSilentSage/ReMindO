package com.example.remindo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class Remindo_Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_task_activity);

//        RecyclerView recyclerView = findViewById(R.id.ToDo_recyclerview);
//        RemindoAdapter adapter = new RemindoAdapter();
//        LinearLayoutManager llm = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(llm);

        getSupportActionBar().hide();
    }
}