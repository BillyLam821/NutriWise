package com.example.nutriwise.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutriwise.MainActivity;
import com.example.nutriwise.R;
import com.example.nutriwise.databinding.FragmentDashboardBinding;
import com.example.nutriwise.ui.home.HomeFragment;

import java.util.List;
import java.util.TreeMap;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private MainActivity mainActivity;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    TreeMap<String, List<String[]>> logData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        MainActivity mainActivity = (MainActivity) getActivity();
//        logData = mainActivity.getLogData();
        logData = HomeFragment.getLogData();
//        TextView testView = root.findViewById(R.id.testView);
//        testView.setText(mainActivity.getLogData().get(0));

        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        RecyclerAdapter adapter = new RecyclerAdapter(logData);
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}