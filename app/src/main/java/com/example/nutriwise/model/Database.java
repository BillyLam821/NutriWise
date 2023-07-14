package com.example.nutriwise.model;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "nutrition_log";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "log_entries";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_INGREDIENT = "ingredient";
    private static final String COLUMN_QUANTITY = "quantity";
    private static final String COLUMN_UNIT = "unit";
    private static final String COLUMN_CALORIES = "calories";
    private static final String COLUMN_CARBS = "carbs";
    private static final String COLUMN_PROTEINS = "proteins";
    private static final String COLUMN_FATS = "fats";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_MONTH = "month";
    private static final String COLUMN_DAY = "day";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_INGREDIENT + " TEXT, " +
                COLUMN_QUANTITY + " INTEGER, " +
                COLUMN_UNIT + " TEXT, " +
                COLUMN_CALORIES + " REAL, " +
                COLUMN_CARBS + " REAL, " +
                COLUMN_PROTEINS + " REAL, " +
                COLUMN_FATS + " REAL, " +
                COLUMN_YEAR + " INTEGER, " +
                COLUMN_MONTH + " INTEGER, " +
                COLUMN_DAY + " INTEGER" +
                ")";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database schema upgrades tbc
    }

    public long insertFoodEntry(LogEntry logEntry) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_INGREDIENT, logEntry.getIngredient());
        values.put(COLUMN_QUANTITY, logEntry.getQuantity());
        values.put(COLUMN_UNIT, logEntry.getUnit());
        values.put(COLUMN_CALORIES, logEntry.getCalories());
        values.put(COLUMN_CARBS, logEntry.getCarbs());
        values.put(COLUMN_PROTEINS, logEntry.getProteins());
        values.put(COLUMN_FATS, logEntry.getFats());
        values.put(COLUMN_YEAR, logEntry.getYear());
        values.put(COLUMN_MONTH, logEntry.getMonth());
        values.put(COLUMN_DAY, logEntry.getDay());

        long rowId = db.insert(TABLE_NAME, null, values);
        db.close();
        return rowId;
    }

    public ArrayList<LogEntry> getAllLog() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<LogEntry> logs = new ArrayList<>();

        String sqlQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(sqlQuery, null);

        // Process the query result
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Access the column values from the cursor
                @SuppressLint("Range") String ingredient = cursor.getString(cursor.getColumnIndex(COLUMN_INGREDIENT));
                @SuppressLint("Range") int quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY));
                @SuppressLint("Range") String unit = cursor.getString(cursor.getColumnIndex(COLUMN_UNIT));
                @SuppressLint("Range") double calories = cursor.getDouble(cursor.getColumnIndex(COLUMN_CALORIES));
                @SuppressLint("Range") double carbs = cursor.getDouble(cursor.getColumnIndex(COLUMN_CARBS));
                @SuppressLint("Range") double proteins = cursor.getDouble(cursor.getColumnIndex(COLUMN_PROTEINS));
                @SuppressLint("Range") double fats = cursor.getDouble(cursor.getColumnIndex(COLUMN_FATS));
                @SuppressLint("Range") int year = cursor.getInt(cursor.getColumnIndex(COLUMN_YEAR));
                @SuppressLint("Range") int month = cursor.getInt(cursor.getColumnIndex(COLUMN_MONTH));
                @SuppressLint("Range") int day = cursor.getInt(cursor.getColumnIndex(COLUMN_DAY));

                // Retrieve other column values as needed

                // Create a new instance of YourModel and populate it with the retrieved values
                LogEntry logEntry = new LogEntry(ingredient, quantity, unit, calories, carbs, proteins, fats, year, month, day);
                // Add the model to the ArrayList
                logs.add(logEntry);
            } while (cursor.moveToNext());
            cursor.close();
        }

        // Close the database connection
        db.close();

        // Return the ArrayList of results
        return logs;
    }

    // to add other methods to query, update, or delete data from the database

}

