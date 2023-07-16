package com.example.nutriwise.ui.record;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutriwise.MainActivity;
import com.example.nutriwise.R;
import com.example.nutriwise.databinding.FragmentDashboardBinding;
import com.example.nutriwise.model.Database;
import com.example.nutriwise.model.LogEntry;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private MainActivity mainActivity;
    private RecyclerView recyclerView;
    private Button deleteAllBtn;
    RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapter adapter;
    List<LogEntry> logEntries = new ArrayList<>();
    Database database;
    TextView noRecordTxt;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mainActivity = (MainActivity) getActivity();
        deleteAllBtn = root.findViewById(R.id.deleteAllBtn);
        database = mainActivity.getDatabase();
        logEntries = database.getAllLog();
        if (logEntries.isEmpty()) {
            noRecordTxt = root.findViewById(R.id.noRecordTxt);
            noRecordTxt.setText("No Record Found");
            deleteAllBtn.setVisibility(View.INVISIBLE);
        }

        // Set up RecyclerView
        recyclerView = root.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(requireContext());
        adapter = new RecyclerAdapter(logEntries);
        recyclerView.setAdapter(adapter);

        deleteAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.deleteAllLogEntries();
                deleteAllBtn.setText("ALL DATA ARE DELETED");
                recyclerView.setAdapter(null);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}