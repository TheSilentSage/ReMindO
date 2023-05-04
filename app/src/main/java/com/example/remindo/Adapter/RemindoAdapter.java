package com.example.remindo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.remindo.R;
import com.example.remindo.Models.RemindoModel;
import com.example.remindo.database.RemindoFireBase;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.firestore.DocumentSnapshot;

public class RemindoAdapter extends RecyclerView.Adapter<RemindoAdapter.RemindoViewHolder> {
    RemindoFireBase remindoFireBase;
    Context context;

    public RemindoAdapter(Context context) {
        this.context = context;
    }

    public void setRemindoFireBase(RemindoFireBase remindoFireBase) {
        this.remindoFireBase = remindoFireBase;
    }

    @NonNull
    @Override
    public RemindoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.to_do_card,parent,false);
        return new RemindoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RemindoViewHolder holder, int position) {

        DocumentSnapshot documentSnapshot = remindoFireBase.getDocumentData(position);
        RemindoModel remindoModel = documentSnapshot.toObject(RemindoModel.class);


        holder.toDo_cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(!remindoModel.getDone()){
                    remindoModel.setStroke(holder.toDo_cardView, context);
                    remindoModel.setDone(true);
                    remindoFireBase.updateFireBaseData(documentSnapshot.getId(), remindoModel);
                }
                else {
                    remindoModel.setStroke(holder.toDo_cardView, context);
                    remindoModel.setDone(false);
                    remindoFireBase.updateFireBaseData(documentSnapshot.getId(), remindoModel);
                }
                return false;
            }
        });

        holder.toDoTitleTextView.setText(remindoModel.getTaskName());
        holder.toDoDescriptionTextView.setText(remindoModel.getTaskDescription());
        holder.toDoDateTextView.setText(remindoModel.taskDate());
        holder.toDoTimeTextView.setText(remindoModel.taskTime());
        holder.toDoRemainingTimeTextView.setText(remindoModel.remainingDuration());
        remindoModel.setStroke(holder.toDo_cardView,context);

    }

    @Override
    public int getItemCount() {
        return remindoFireBase.getSize();
    }

    class RemindoViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView toDo_cardView;
        TextView toDoTitleTextView;
        TextView toDoDateTextView;
        TextView toDoDescriptionTextView;
        TextView toDoRemainingTimeTextView;
        TextView toDoTimeTextView;
        public RemindoViewHolder(@NonNull View itemView) {
            super(itemView);
            toDo_cardView = itemView.findViewById(R.id.toDo_cardView);
            toDoTitleTextView = itemView.findViewById(R.id.ToDoTitle_textview);
            toDoDateTextView = itemView.findViewById(R.id.ToDODate_textview);
            toDoDescriptionTextView = itemView.findViewById(R.id.ToDODescription_textview);
            toDoRemainingTimeTextView = itemView.findViewById(R.id.ToDORemainingTime_textview);
            toDoTimeTextView = itemView.findViewById(R.id.ToDOTime_textview);


        }
    }
}
