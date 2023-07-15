package com.example.nutriwise.ui.home;

import static android.content.ContentValues.TAG;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.nutriwise.MainActivity;
import com.example.nutriwise.R;
import com.example.nutriwise.api.EdamamApiService;
import com.example.nutriwise.databinding.FragmentHomeBinding;
import com.example.nutriwise.model.Database;
import com.example.nutriwise.model.LogEntry;
import com.example.nutriwise.model.NutritionData;
import com.example.nutriwise.model.NutritionDataResponse;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private EditText foodNameEditText;
    private Button submitButton;
    private TextView nutritionTextView;
    private Spinner unitSpinner;
    private EditText unitQtyEditTextNumber;
    private EdamamApiService apiService;
    private String appId = "09fc50ad";
    private String appKey = "70e217ae29908de8eb71baf50a8b6e06";
    private String BASE_URL = "https://api.edamam.com/api/";
    String ingredient;
    NutritionData nutritionData;
    String dt;
    private int year;
    private int month;
    private int day;
    static Comparator<String> descendingComparator = Comparator.reverseOrder();
    private static TreeMap<String, List<String[]>> logData = new TreeMap<>(descendingComparator); // to-be-implemented
    private Database database;
    LogEntry logEntry;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            database = mainActivity.getDatabase();
        }
        System.out.println("*********");
        System.out.println(database);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(EdamamApiService.class);
        foodNameEditText = root.findViewById(R.id.foodNameEditText);
        submitButton = root.findViewById(R.id.submitButton);
        nutritionTextView = root.findViewById(R.id.nutritionTextView);
        unitSpinner = root.findViewById(R.id.unitSpinner);
        unitQtyEditTextNumber = root.findViewById(R.id.unitQtyEditTextNumber);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchQty = unitQtyEditTextNumber.getText().toString();
                String searchUnit = unitSpinner.getSelectedItem().toString();
                String searchFood = foodNameEditText.getText().toString();
                if (searchQty.isEmpty()) {
                    nutritionTextView.setText("Quantity is missing");
                } else if (searchFood.isEmpty()) {
                    nutritionTextView.setText("Food name is missing");
                } else {
//                    ingredient = searchQty + searchUnit + " " + searchFood;
                    nutritionTextView.setText("Searching for data...");
                    fetchNutritionData(searchQty, searchUnit, searchFood);
                }
            }
        }); // end of onClickListener for food search

        // on below line we are initializing our variables.
        Button pickDateBtn;
        TextView selectedDateTV;
        pickDateBtn = root.findViewById(R.id.idBtnPickDate);
        selectedDateTV = root.findViewById(R.id.idTVSelectedDate);

        // on below line we are adding click listener for our pick date button
        pickDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting
                // the instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH) + 1;
                day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        root.getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                selectedDateTV.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();
            }
        });

        Button logBtn;
        logBtn = root.findViewById(R.id.idBtnLog);
        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (dt.isEmpty()) {
                if (logEntry.equals(null)) {
                    nutritionTextView.setText("Please search for food");
                } else if (year <= 0) {
                    nutritionTextView.setText("Please select date");
                } else {
//                    String dateString = Integer.toString(year) + "-" + Integer.toString(month + 1) + "-" + Integer.toString(day);
//                    addLogData(dateString, ingredient, dt);
//                    dt = null;
//                    ingredient = null;
//                    year = 0;
//                    month = 0;
//                    day = 0;
                    if (database != null) {
                        database.insertFoodEntry(logEntry);
                    }
                    else {
                        System.out.println("DB is null");
                    }
                }
            }
        }); // end of onClickListener for Logging

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

//    public void addLogData(String date, String food, String nutrition) {
//        if (!logData.containsKey(date)) {
//            logData.put(date, new ArrayList<>());
//        }
//        logData.get(date).add(new String[] {food, nutrition});
//    }
//    public static TreeMap<String, List<String[]>> getLogData() {
//        return logData;
//    }


    private void fetchNutritionData(String searchQty, String searchUnit, String searchFood) {
        ingredient = searchQty + searchUnit + " " + searchFood;
        Call<NutritionDataResponse> call = apiService.getNutritionData(appId, appKey, ingredient);

        call.enqueue(new Callback<NutritionDataResponse>() {
            @Override
            public void onResponse(Call<NutritionDataResponse> call, Response<NutritionDataResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                    NutritionDataResponse data = response.body();
                    // Parse the data and display on UI
                    nutritionData = data.getNutritionData();
                    double calories = nutritionData.getEnergy().getQuantity();
                    double fat = nutritionData.getFat().getQuantity();
                    double protein = nutritionData.getProtein().getQuantity();
                    double carbs = nutritionData.getCarbs().getQuantity();

                    // Insert data into db
                    logEntry = new LogEntry(searchFood, Integer.parseInt(searchQty), searchUnit, calories, carbs, protein, fat, year, month, day);

                    String displayText = String.format(
                            "%-13s%.1f kcal%n%-14s%.1f g%n%-13s%.1f g%n%-16s%.1f g",
                            "Calories: ", calories,
                            "Carbs:    ", carbs,
                            "Protein:  ", protein,
                            "Fat:      ", fat);
                    dt = displayText;

                    System.out.println(displayText);
                    nutritionTextView.setText(displayText);

                } else {
                    // Handle API error
                    System.out.println("NO RESPONSE");
                    System.out.println(response.code());
                    System.out.println(response.message());
                    Log.e(TAG, "Request failed: " + response.code());
                    nutritionTextView.setText("Failed to fetch nutrition data. Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<NutritionDataResponse> call, Throwable t) {
                // Handle network or other errors
                Log.e(TAG, "Request failed: " + t.getMessage());
                nutritionTextView.setText("Failed to fetch nutrition data. Error: " + t.getMessage());
            }
        });
    } // end of fetchNutritionData

//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        MainActivity mainActivity = (MainActivity) context;
//        database = mainActivity.getDatabase();
//        System.out.println("*******");
//        System.out.println("ATTACH");
//        System.out.println(database);
//    }
}