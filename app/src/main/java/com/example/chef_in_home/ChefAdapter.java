package com.example.chef_in_home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChefAdapter extends RecyclerView.Adapter<ChefAdapter.ChefViewHolder> {

    private List<Chef> chefList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Chef chef);
    }

    public ChefAdapter(List<Chef> chefList, OnItemClickListener listener) {
        this.chefList = chefList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ChefViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chef, parent, false);
        return new ChefViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChefViewHolder holder, int position) {
        Chef chef = chefList.get(position);
        holder.bind(chef, listener);
    }

    @Override
    public int getItemCount() {
        return chefList.size();
    }

    public static class ChefViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewChefName;
        private TextView textViewChefSpecialty;
        private TextView textViewChefAvailability;

        public ChefViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewChefName = itemView.findViewById(R.id.textViewChefName);
            textViewChefSpecialty = itemView.findViewById(R.id.textViewChefSpecialty);
            textViewChefAvailability = itemView.findViewById(R.id.textViewChefAvailability);
        }

        public void bind(final Chef chef, final OnItemClickListener listener) {
            textViewChefName.setText(chef.getName());
            textViewChefSpecialty.setText(chef.getSpecialty());
            textViewChefAvailability.setText(chef.isAvailable() ? "Available" : "Not Available");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(chef);
                }
            });
        }
    }
}
