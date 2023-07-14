package com.example.nutriwise;

import static android.content.ContentValues.TAG;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.nutriwise.api.EdamamApiService;
import com.example.nutriwise.model.NutritionData;
import com.example.nutriwise.model.NutritionDataResponse;
import com.example.nutriwise.ui.dashboard.DashboardFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.nutriwise.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AppBarConfiguration mAppBarConfiguration;
//    private EditText foodNameEditText;
//    private Button submitButton;
//    private TextView nutritionTextView;
//    private Spinner unitSpinner;
//    private EditText unitQtyEditTextNumber;
//    private EdamamApiService apiService;
//    private String appId = "09fc50ad";
//    private String appKey = "70e217ae29908de8eb71baf50a8b6e06";
//    private String BASE_URL = "https://api.edamam.com/api/";
//    String ingredient;
//    private int year;
//    private int month;
//    private int day;
    private String testVal = "testVal";
//    private List<String> logData = new ArrayList<>();
    Comparator<String> descendingComparator = Comparator.reverseOrder();
    private TreeMap<String, List<String[]>> logData = new TreeMap<>(descendingComparator); // to-be-implemented
//    public void addLogData(String s) {
//        getLogData().add(s);
//    }
//    public void addLogData(String date, String food, String nutrition) {
//        if (!logData.containsKey(date)) {
//            logData.put(date, new ArrayList<>());
//        }
//        logData.get(date).add(new String[] {food, nutrition});
//    }
//    public TreeMap<String, List<String[]>> getLogData() {
//        return logData;
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        apiService = retrofit.create(EdamamApiService.class);
//        foodNameEditText = findViewById(R.id.foodNameEditText);
//        submitButton = findViewById(R.id.submitButton);
//        nutritionTextView = findViewById(R.id.nutritionTextView);
//        unitSpinner = findViewById(R.id.unitSpinner);
//        unitQtyEditTextNumber = findViewById(R.id.unitQtyEditTextNumber);
//
//        submitButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String searchQty = unitQtyEditTextNumber.getText().toString();
//                String searchUnit = unitSpinner.getSelectedItem().toString();
//                String searchFood = foodNameEditText.getText().toString();
//                if (searchQty.isEmpty()) {
//                    nutritionTextView.setText("Quantity is missing");
//                } else if (searchFood.isEmpty()) {
//                    nutritionTextView.setText("Food name is missing");
//                } else {
//                    ingredient = searchQty + searchUnit + " " + searchFood;
//                    nutritionTextView.setText("Searching for data...");
//                    fetchNutritionData(ingredient);
//                }
//            }
//        }); // end of onClickListener for food search

//        Button logBtn;
//        logBtn = findViewById(R.id.idBtnLog);
//        logBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (dt.isEmpty()) {
//                    nutritionTextView.setText("Please search for food");
//                } else if (year <= 0) {
//                    nutritionTextView.setText("Please select date");
//                } else {
//                    String dateString = Integer.toString(year) + "-" + Integer.toString(month + 1) + "-" + Integer.toString(day);
//                    addLogData(dateString, ingredient, dt);
//                    dt = null;
//                    ingredient = null;
//                    year = 0;
//                    month = 0;
//                    day = 0;
//                }
//            }
//        }); // end of onClickListener for Logging

//        // on below line we are initializing our variables.
//        Button pickDateBtn;
//        TextView selectedDateTV;
//        pickDateBtn = findViewById(R.id.idBtnPickDate);
//        selectedDateTV = findViewById(R.id.idTVSelectedDate);
//
//        // on below line we are adding click listener for our pick date button
//        pickDateBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // on below line we are getting
//                // the instance of our calendar.
//                final Calendar c = Calendar.getInstance();
//
//                // on below line we are getting
//                // our day, month and year.
//                year = c.get(Calendar.YEAR);
//                month = c.get(Calendar.MONTH);
//                day = c.get(Calendar.DAY_OF_MONTH);
//
//                // on below line we are creating a variable for date picker dialog.
//                DatePickerDialog datePickerDialog = new DatePickerDialog(
//                        // on below line we are passing context.
//                        MainActivity.this,
//                        new DatePickerDialog.OnDateSetListener() {
//                            @Override
//                            public void onDateSet(DatePicker view, int year,
//                                                  int monthOfYear, int dayOfMonth) {
//                                // on below line we are setting date to our text view.
//                                selectedDateTV.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
//
//                            }
//                        },
//                        // on below line we are passing year,
//                        // month and day for selected date in our date picker.
//                        year, month, day);
//                // at last we are calling show to
//                // display our date picker dialog.
//                datePickerDialog.show();
//            }
//        });
    } // end of onCreate

//    NutritionData nutritionData;
//    String dt;
//    private void fetchNutritionData(String ingredient) {
//
//        Call<NutritionDataResponse> call = apiService.getNutritionData(appId, appKey, ingredient);
//
//        call.enqueue(new Callback<NutritionDataResponse>() {
//            @Override
//            public void onResponse(Call<NutritionDataResponse> call, Response<NutritionDataResponse> response) {
//                if (response.isSuccessful() && response.body() != null) {
//
//                    NutritionDataResponse data = response.body();
//                    // Parse the data and display on UI
//                    nutritionData = data.getNutritionData();
//                    double calories = nutritionData.getEnergy().getQuantity();
//                    double fat = nutritionData.getFat().getQuantity();
//                    double protein = nutritionData.getProtein().getQuantity();
//                    double carbs = nutritionData.getCarbs().getQuantity();
//
//                    String displayText = String.format(
//                            "%-14s%.1f%n%-14s%.1f%n%-14s%.1f%n%-14s%.1f",
//                            "Calories:", calories,
//                            "Carbs:", carbs,
//                            "Protein:", protein,
//                            "Fat:", fat);
//                    dt = displayText;
//
//                    System.out.println(displayText);
//                    nutritionTextView.setText(displayText);
//
//                } else {
//                    // Handle API error
//                    System.out.println("NO RESPONSE");
//                    System.out.println(response.code());
//                    System.out.println(response.message());
//                    Log.e(TAG, "Request failed: " + response.code());
//                    nutritionTextView.setText("Failed to fetch nutrition data. Error: " + response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<NutritionDataResponse> call, Throwable t) {
//                // Handle network or other errors
//                Log.e(TAG, "Request failed: " + t.getMessage());
//                nutritionTextView.setText("Failed to fetch nutrition data. Error: " + t.getMessage());
//            }
//        });
//    } // end of fetchNutritionData

}