package com.example.nutriwise.ui.home;

import static android.content.ContentValues.TAG;

import android.app.DatePickerDialog;
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
    private Button searchButton;
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
    Button pickDateBtn;
    Button logBtn;
    TextView selectedDateTV;
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


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(EdamamApiService.class);
        foodNameEditText = root.findViewById(R.id.foodNameEditText);
        searchButton = root.findViewById(R.id.searchButton);
        nutritionTextView = root.findViewById(R.id.nutritionTextView);
        unitSpinner = root.findViewById(R.id.unitSpinner);
        unitQtyEditTextNumber = root.findViewById(R.id.unitQtyEditTextNumber);

        searchButton.setOnClickListener(new View.OnClickListener() {
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


        pickDateBtn = root.findViewById(R.id.idBtnPickDate);
        selectedDateTV = root.findViewById(R.id.idTVSelectedDate);
        pickDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        root.getContext(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                selectedDateTV.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, year, month, day);
                // display our date picker dialog
                datePickerDialog.show();

                if (logEntry == null) {
                    logEntry = new LogEntry(year, month + 1, day);
                } else {
                    logEntry.setYear(year);
                    logEntry.setMonth(month + 1);
                    logEntry.setDay(day);
                }
            }
        }); // end of onClickListener for select date


        logBtn = root.findViewById(R.id.idBtnLog);
        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (dt.isEmpty()) {
                if (logEntry == null || logEntry.getIngredient() == null) {
                    nutritionTextView.setText("Please search for food");
                } else if (year <= 0) {
                    nutritionTextView.setText("Please select date");
                } else {
                    if (database != null) {
                        database.insertFoodEntry(logEntry);
                        nutritionTextView.setText("Data successfully added to your record!");
                        logEntry = null;
                        year = 0;
                        foodNameEditText.setText("");
                        unitQtyEditTextNumber.setText("");
                    } else {
                        nutritionTextView.setText("Internal error, please restart app");
                    }
                }
            }
        }); // end of onClickListener for logging

        return root;
    }

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

                    if (logEntry == null) {
                        logEntry = new LogEntry(searchFood, Integer.parseInt(searchQty), searchUnit, calories, carbs, protein, fat);
                    } else {
                        logEntry.setIngredient(searchFood);
                        logEntry.setQuantity(Integer.parseInt(searchQty));
                        logEntry.setUnit(searchUnit);
                        logEntry.setCalories(calories);
                        logEntry.setCarbs(carbs);
                        logEntry.setProteins(protein);
                        logEntry.setFats(fat);
                    }

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}