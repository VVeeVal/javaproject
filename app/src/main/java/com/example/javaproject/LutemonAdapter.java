package com.example.javaproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
//Recyclerview adapter
public class LutemonAdapter extends RecyclerView.Adapter<LutemonAdapter.LutemonViewHolder> {
    private ArrayList<Lutemon> lutemons;
    private OnItemClickListener listener;


    public LutemonAdapter(ArrayList<Lutemon> lutemons) {
        this.lutemons = lutemons;
    }

    @NonNull
    @Override
    public LutemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lutemon_item, parent, false);
        return new LutemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LutemonViewHolder holder, int position) {
        Lutemon lutemon = lutemons.get(position);
        holder.nameText.setText(lutemon.getName());
        holder.statsText.setText(String.format("Attack: %d, Defense: %d, Health: %d/%d, XP: %d",
            lutemon.getAttack(), lutemon.getDefense(), 
            lutemon.getHealth(), lutemon.getMaxHealth(),
            lutemon.getExperience()));
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(lutemon);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lutemons.size();
    }
    public interface OnItemClickListener {
        void onItemClick(Lutemon lutemon);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public void updateData(ArrayList<Lutemon> newLutemons) {
        this.lutemons = newLutemons;
        notifyDataSetChanged();
    }

    static class LutemonViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        TextView statsText;

        LutemonViewHolder(View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.nameText);
            statsText = itemView.findViewById(R.id.statsText);
        }
    }
}
