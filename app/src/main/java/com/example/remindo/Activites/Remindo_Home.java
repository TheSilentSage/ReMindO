package com.example.remindo.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.remindo.R;
import com.example.remindo.Adapter.RemindoAdapter;
import com.example.remindo.database.RemindoFireBase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Remindo_Home extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remindo_home);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


//        ArrayList<RemindoViewModel> arrayList = new ArrayList<>();
//        arrayList.add(new RemindoViewModel(1,"Buy Apple","Walk 10 km and buy apple","Wed 05 May,2023 11:22 AM",false));
//        arrayList.add(new RemindoViewModel(2,"Buy Milk","Walk 10 km and buy Milk","Wed 05 May,2023 11:22 AM",false));
//        arrayList.add(new RemindoViewModel(2,"Buy Tea","Walk 10 km and buy Tea","Wed 05 May,2023 11:22 AM",false));
//        arrayList.add(new RemindoViewModel(3,"Buy Chocolate","Walk 10 km and buy Choco","Wed 05 May,2023 11:22 AM",false));
//        arrayList.add(new RemindoViewModel(1,"Run","Run 10 km","Wed 05 May,2023 11:22 AM",false));
//        arrayList.add(new RemindoViewModel(1,"Complete Project","Complete Project before sleep","Wed 05 May,2023 11:22 AM",false));
//        arrayList.add(new RemindoViewModel(2,"Listen to Music","Listen to music after playing","Wed 05 May,2023 11:22 AM",false));
//        arrayList.add(new RemindoViewModel(1,"Study","Study 3 chapters tonight","Wed 05 May,2023 11:22 AM",false));
//        arrayList.add(new RemindoViewModel(2,"Play","Play to stay fit","Wed 05 May,2023 11:22 AM",false));
//        arrayList.add(new RemindoViewModel(3,"Change tap","Fix leaking tap","Wed 05 May,2023 11:22 AM",false));

        RemindoAdapter adapter = new RemindoAdapter(this);

        RemindoFireBase remindoFireBase = new RemindoFireBase(adapter);
        remindoFireBase.readFromFireBase();
        RecyclerView recyclerView = findViewById(R.id.ToDo_recyclerview);
        adapter.setRemindoFireBase(remindoFireBase);
        LinearLayoutManager llm = new LinearLayoutManager(Remindo_Home.this,RecyclerView.HORIZONTAL,false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(llm);




        FloatingActionButton fab = findViewById(R.id.add_todo_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentNewToDo = new Intent(Remindo_Home.this,Add_New_Task.class);
                intentNewToDo.putExtra("db",remindoFireBase);
                startActivity(intentNewToDo);
            }
        });



        getSupportActionBar().hide();
    }
}