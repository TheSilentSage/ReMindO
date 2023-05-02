package com.example.remindo.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.remindo.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Remindo_Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remindo_home);

//        RecyclerView recyclerView = findViewById(R.id.ToDo_recyclerview);
//        RemindoAdapter adapter = new RemindoAdapter();
//        LinearLayoutManager llm = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(llm);

        FloatingActionButton fab = findViewById(R.id.add_todo_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentNewToDo = new Intent(Remindo_Home.this,Add_New_Task.class);
                startActivity(intentNewToDo);
            }
        });

        getSupportActionBar().hide();
    }
}