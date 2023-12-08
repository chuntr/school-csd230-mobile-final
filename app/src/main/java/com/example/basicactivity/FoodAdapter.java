package com.example.basicactivity;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private final LayoutInflater inflator;
    public ArrayList<String> foodItemList;

    public int selected = RecyclerView.NO_POSITION;

    class FoodViewHolder extends RecyclerView.ViewHolder {
        public TextView foodTextView;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            foodTextView = itemView.findViewById(R.id.foodListItem);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifyItemChanged(selected);
                    selected = getAdapterPosition();
                    notifyItemChanged(selected);
                }
            });
        }
    }

    public FoodAdapter(Context context, ArrayList<String> foodItemList) {
        inflator = LayoutInflater.from(context);
        this.foodItemList = foodItemList;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View foodItemView = inflator.inflate(R.layout.results_item, parent, false);
        return new FoodViewHolder(foodItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        holder.foodTextView.setText(foodItemList.get(position));
        holder.itemView.setSelected(selected == position);
        holder.itemView.setBackgroundColor(selected == position ? Color.CYAN : Color.WHITE);
    }

    @Override
    public int getItemCount() {
        return foodItemList.size();
    }
}
