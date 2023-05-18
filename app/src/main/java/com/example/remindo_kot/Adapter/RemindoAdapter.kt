package com.example.remindo_kot.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.remindo_kot.Database.RemindoFireBase
import com.example.remindo_kot.Models.RemindoModel
import com.example.remindo_kot.R
import com.google.android.material.card.MaterialCardView

class RemindoAdapter() : RecyclerView.Adapter<RemindoAdapter.RemindoViewHolder>() {

    lateinit var remindoFireBase: RemindoFireBase
    lateinit var context: Context

    constructor(context: Context) : this() {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RemindoViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.to_do_card, parent, false)
        return RemindoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RemindoViewHolder, position: Int) {
        val documentSnapshot = remindoFireBase.getDocumentData(position)
        val remindoModel = documentSnapshot.toObject(RemindoModel::class.java)
        holder.toDo_cardView.setOnLongClickListener {
            if (remindoModel != null) {
                if (!remindoModel.done!!) {
                    remindoModel!!.setStroke(holder.toDo_cardView, context)
                    remindoModel.done = true
                    remindoFireBase.updateFireBaseData(documentSnapshot.id, remindoModel)
                } else {
                    remindoModel!!.setStroke(holder.toDo_cardView, context)
                    remindoModel.done = false
                    remindoFireBase.updateFireBaseData(documentSnapshot.id, remindoModel)
                }
            }
            false
        }
        if (remindoModel != null) {
            holder.toDoTitleTextView.setText(remindoModel.taskName)
        }
        if (remindoModel != null) {
            holder.toDoDescriptionTextView.setText(remindoModel.taskDescription)
        }
        holder.toDoDateTextView.text = remindoModel!!.taskDate()
        holder.toDoTimeTextView.text = remindoModel.taskTime()
        holder.toDoRemainingTimeTextView.text = remindoModel.remainingDuration()
        remindoModel.setStroke(holder.toDo_cardView, context)
    }

    override fun getItemCount(): Int {
        return remindoFireBase.getSize()
    }
    class RemindoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var toDo_cardView: MaterialCardView
        var toDoTitleTextView: TextView
        var toDoDateTextView: TextView
        var toDoDescriptionTextView: TextView
        var toDoRemainingTimeTextView: TextView
        var toDoTimeTextView: TextView

        init {
            toDo_cardView = itemView.findViewById(R.id.toDo_cardView)
            toDoTitleTextView = itemView.findViewById(R.id.ToDoTitle_textview)
            toDoDateTextView = itemView.findViewById(R.id.ToDODate_textview)
            toDoDescriptionTextView = itemView.findViewById(R.id.ToDODescription_textview)
            toDoRemainingTimeTextView = itemView.findViewById(R.id.ToDORemainingTime_textview)
            toDoTimeTextView = itemView.findViewById(R.id.ToDOTime_textview)
        }
    }
}