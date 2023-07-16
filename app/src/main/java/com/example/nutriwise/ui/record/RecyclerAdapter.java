package com.example.nutriwise.ui.record;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutriwise.R;
import com.example.nutriwise.model.LogEntry;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<LogEntry> logData;

    public RecyclerAdapter(List<LogEntry> logData) {
            this.logData = logData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.log_card, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LogEntry logEntry = logData.get(position);

        // Bind data to the views in the log_card layout
        String displayDate = Integer.toString(logEntry.getYear()) + "-" + Integer.toString(logEntry.getMonth()) + "-" + Integer.toString(logEntry.getDay());
        holder.logDateTextView.setText(displayDate);
        holder.logNameTextView.setText(logEntry.getIngredient());
        String displayQty = Integer.toString(logEntry.getQuantity()) + logEntry.getUnit();
        holder.logQtyTextView.setText(displayQty);
        String displayDetails = String.format(
                "%-13s%.1f kcal%n%-14s%.1f g%n%-13s%.1f g%n%-16s%.1f g",
                "Calories:", logEntry.getCalories(),
                "Carbs:", logEntry.getCarbs(),
                "Proteins:", logEntry.getProteins(),
                "Fats:", logEntry.getFats());
        holder.logDetailTextView.setText(displayDetails);
    }

    @Override
    public int getItemCount() {
        return logData.size();
    }

    // Create a ViewHolder class to hold the views for each card item
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView logDateTextView;
        TextView logNameTextView;
        TextView logQtyTextView;
        TextView logDetailTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            logDateTextView = itemView.findViewById(R.id.logDate);
            logNameTextView = itemView.findViewById(R.id.logName);
            logQtyTextView = itemView.findViewById(R.id.logQty);
            logDetailTextView = itemView.findViewById(R.id.logDetail);
        }
    }
}
