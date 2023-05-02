package com.example.remindo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RemindoAdapter extends RecyclerView.Adapter<RemindoAdapter.RemindoViewHolder> {
    @NonNull
    @Override
    public RemindoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.to_do_card,parent,false);
        return new RemindoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RemindoViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class RemindoViewHolder extends RecyclerView.ViewHolder {
        public RemindoViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
