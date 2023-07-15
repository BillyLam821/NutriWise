package com.example.nutriwise.ui.dashboard;

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
import com.example.nutriwise.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private MainActivity mainActivity;
    private RecyclerView recyclerView;
    private Button deleteAllBtn;
    RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapter adapter;
    TreeMap<String, List<String[]>> logData;
    List<LogEntry> logEntries = new ArrayList<>();
    Database database;
    TextView noRecordTxt;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textDashboard;
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        MainActivity mainActivity = (MainActivity) getActivity();
//        logData = mainActivity.getLogData();
        database = mainActivity.getDatabase();
        logEntries = database.getAllLog();
        if (logEntries.isEmpty()) {
            noRecordTxt = root.findViewById(R.id.noRecordTxt);
            noRecordTxt.setText("No Record Found");
        }
//        TextView testView = root.findViewById(R.id.testView);
//        testView.setText(mainActivity.getLogData().get(0));

        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);

        // Set up RecyclerView
//        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
//        RecyclerAdapter adapter = new RecyclerAdapter(logEntries);
//        recyclerView.setAdapter(adapter);


//        recyclerView = findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(requireContext());
//        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter(logEntries);
        recyclerView.setAdapter(adapter);

        deleteAllBtn = root.findViewById(R.id.deleteAllBtn);
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