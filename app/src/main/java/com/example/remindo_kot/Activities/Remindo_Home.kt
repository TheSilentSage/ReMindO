package com.example.remindo_kot.Activities

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.remindo_kot.Adapter.RemindoAdapter
import com.example.remindo_kot.Database.RemindoFireBase
import com.example.remindo_kot.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Remindo_Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remindo_home)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    val adapter = RemindoAdapter(this)
        val remindoFireBase = RemindoFireBase(adapter)
        remindoFireBase.readFromFireBase()
        val recyclerView:RecyclerView = findViewById(R.id.ToDo_recyclerview)
        adapter.remindoFireBase = remindoFireBase
        val llm = LinearLayoutManager(this@Remindo_Home, RecyclerView.HORIZONTAL, false)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = llm


        val fab:FloatingActionButton = findViewById(R.id.add_todo_button)
        fab.setOnClickListener {
            val intentNewToDo = Intent(this@Remindo_Home, Add_New_Task::class.java)
            intentNewToDo.putExtra("db", remindoFireBase)
            startActivity(intentNewToDo)
        }

        supportActionBar?.hide()

    }
}