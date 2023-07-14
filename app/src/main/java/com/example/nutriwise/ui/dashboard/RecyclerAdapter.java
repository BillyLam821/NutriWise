package com.example.nutriwise.ui.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutriwise.R;
import com.example.nutriwise.model.LogEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {


//    private TreeMap<String, List<String[]>> logData;
//    private List<String> dates; // List to store the dates
    private List<LogEntry> logData;

//    public RecyclerAdapter(TreeMap<String, List<String[]>> logData) {
//        this.logData = logData;
//        this.dates = new ArrayList<>(logData.keySet()); // Store the dates from the TreeMap
//    }
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
        holder.logDateTextView.setText(Integer.toString(logEntry.getYear()));
        holder.logNameTextView.setText(logEntry.getIngredient());
        holder.logDetailTextView.setText(Double.toString(logEntry.getCalories()));

//        holder.logDetailTextView.setText(logDetailBuilder.toString());
    }

    @Override
    public int getItemCount() {
        return logData.size();
    }

    // Create a ViewHolder class to hold the views for each card item
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView logDateTextView;
        TextView logNameTextView;
        TextView logDetailTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            logDateTextView = itemView.findViewById(R.id.logDate);
            logNameTextView = itemView.findViewById(R.id.logName);
            logDetailTextView = itemView.findViewById(R.id.logDetail);
        }
    }
}
