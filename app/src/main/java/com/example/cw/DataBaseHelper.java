package com.example.cw;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    //CAR DATABASE COLUMNS

    public static final String CAR_TABLE = "CAR_TABLE";
    public static final String COLUMN_CAR_MAKE = "CAR_MAKE";
    public static final String COLUMN_CAR_MODEL = "CAR_MODEL";
    public static final String COLUMN_CAR_YEAR = "CAR_YEAR";
    public static final String COLUMN_CAR_PRICE = "CAR_PRICE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_LONGITUDE = "LONGITUDE";
    public static final String COLUMN_LATITUDE = "LATITUDE";
    public static final String COLUMN_CAR_MOT = "MOT";
    public static final String COLUMN_CAR_INSURANCE = "INSURANCE";
    public static final String COLUMN_CAR_IMAGE = "IMAGE";


    //EXPENSE DATABASE COLUMNS
    public static final String EXPENSE_TABLE = "EXPENSE_TABLE";
    public static final String EXPENSE_ID = "ID";
    public static final String CAR_ID = "CAR_ID";
    public static final String EXPENSE_TEXT = "EXPENSE";
    public static final String NOTE = "NOTE";



    public DataBaseHelper(@Nullable Context context) {
        super(context, "car.db", null, 1);
    }


    //this is called the first time a database is accessed. There should be code that generates a new DB.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + CAR_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CAR_MAKE + " TEXT, " + COLUMN_CAR_MODEL + " TEXT, " + COLUMN_CAR_YEAR + " INT, " +  COLUMN_CAR_PRICE + " INT," + COLUMN_LATITUDE + " TEXT, " + COLUMN_LONGITUDE + " TEXT, " + COLUMN_CAR_MOT + " TEXT, " + COLUMN_CAR_INSURANCE + " TEXT, " + COLUMN_CAR_IMAGE + " BLOB)";
        String createExpenseTable = "CREATE TABLE " + EXPENSE_TABLE + " (" + EXPENSE_ID +  " INTEGER PRIMARY KEY AUTOINCREMENT, " + EXPENSE_TEXT + " TEXT, " + NOTE + " TEXT, " + CAR_ID + " TEXT)";
        db.execSQL(createTableStatement);
        db.execSQL(createExpenseTable);
    }

    //This is called if the database version number changes. It prevents previous users apps from breaking when you change the database design.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }



    public boolean deleteOne(int ID){
        //find carModel in the database. if its found delete it and return true, if not -> return false
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + CAR_TABLE + " WHERE " + COLUMN_ID + " = " + ID;
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            return true;
        }else{
            return false;
        }
    }

    public boolean addExpense(expenseModel expense, String id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();


        cv.put(EXPENSE_TEXT, expense.getExpense());
        cv.put(NOTE, expense.getNote());
        cv.put(CAR_ID, expense.getCar_ID());
        long insert = db.insert(EXPENSE_TABLE, null, cv);

        if(insert == -1){
            return false;
        }else{
            return true;
        }
    }

    public List<expenseModel> selectAllExpenses(){
        List<expenseModel> returnList = new ArrayList<>();

        //get data from the database
        String queryString = "SELECT * FROM " + EXPENSE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            //loop through the cursor (result set) and create new expense results. Put them in the return list
            do{
                int CarID = cursor.getInt(0);
                int ExpenseID = cursor.getInt(1);
                String Expense = cursor.getString(2);
                String Note = cursor.getString(3);
                 expenseModel newExpense = new expenseModel(CarID, ExpenseID, Expense, Note);
                returnList.add(newExpense);
            }while(cursor.moveToNext());
        }else{
            //failure. do not add anything to the list.
        }
        //close both the cursor and the db when done.

        db.close();


        return returnList;
    }


    public classCarModel searchOne(int ID){
        //find car in the database by its ID
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT * FROM " + CAR_TABLE + " WHERE " + COLUMN_ID + " = " + ID ;
        Cursor cursor1 = db.rawQuery(queryString, null);
        if(cursor1.moveToFirst()) {
            int carID = cursor1.getInt(0);
            String carMake = cursor1.getString(1);
            String carModel = cursor1.getString(2);
            int carPrice = cursor1.getInt(3);
            int carYear = cursor1.getInt(4);
            String longitude = cursor1.getString(5);
            String latitude = cursor1.getString(6);
            String mot = cursor1.getString(7);
            String insurance = cursor1.getString(8);
            String path = cursor1.getString(9);
            classCarModel newCar = new classCarModel(carID, carMake, carModel, carPrice, carYear, longitude, latitude, mot, insurance, path);
            return newCar;
        }else{
            return null;
        }

    }

    public boolean addMOTINS(classCarModel carModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        String id = Integer.toString(carModel.getId());
        cv.put(COLUMN_CAR_MAKE, carModel.getMake());
        cv.put(COLUMN_CAR_MODEL, carModel.getModel());
        cv.put(COLUMN_CAR_PRICE, carModel.getPrice());
        cv.put(COLUMN_CAR_YEAR, carModel.getYear());
        cv.put(COLUMN_LONGITUDE, carModel.getLongitude());
        cv.put(COLUMN_LATITUDE, carModel.getLatitude());
        cv.put(COLUMN_CAR_MOT, carModel.getMOT());
        cv.put(COLUMN_CAR_INSURANCE, carModel.getInsurance());
        cv.put(COLUMN_CAR_IMAGE, carModel.getImagepath());

        long insert = db.update(CAR_TABLE, cv, "ID = ?", new String[] { id });

        if(insert == -1){
            return false;
        }else{
            return true;
        }
    }



    public boolean addGPS(classCarModel carModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        String id = Integer.toString(carModel.getId());

        cv.put(COLUMN_CAR_MAKE, carModel.getMake());
        cv.put(COLUMN_CAR_MODEL, carModel.getModel());
        cv.put(COLUMN_CAR_PRICE, carModel.getPrice());
        cv.put(COLUMN_CAR_YEAR, carModel.getYear());
        cv.put(COLUMN_LONGITUDE, carModel.getLongitude());
        cv.put(COLUMN_LATITUDE, carModel.getLatitude());
        cv.put(COLUMN_CAR_MOT, carModel.getMOT());
        cv.put(COLUMN_CAR_INSURANCE, carModel.getInsurance());
        cv.put(COLUMN_CAR_IMAGE, carModel.getImagepath());

        long insert = db.update(CAR_TABLE, cv, "ID = ?", new String[] { id });
        if(insert == -1){
            return false;
        }else{
            return true;
        }





    }

    public boolean addOne(classCarModel carModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CAR_MAKE, carModel.getMake());
        cv.put(COLUMN_CAR_MODEL, carModel.getModel());
        cv.put(COLUMN_CAR_PRICE, carModel.getPrice());
        cv.put(COLUMN_CAR_YEAR, carModel.getYear());
        cv.put(COLUMN_CAR_MOT, carModel.getMOT());
        cv.put(COLUMN_CAR_INSURANCE, carModel.getInsurance());
        cv.put(COLUMN_CAR_IMAGE, carModel.getImagepath());

        long insert = db.insert(CAR_TABLE, null, cv);
        if(insert == -1){
            return false;
        }else{
            return true;
        }
    }
    public List<classCarModel> selectAll(){
        List<classCarModel> returnList = new ArrayList<>();

        //get data from the database
        String queryString = "SELECT * FROM " + CAR_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            //loop through the cursor (result set) and create new car results. Put them in the return list
            do{
                int carID = cursor.getInt(0);
                String carMake = cursor.getString(1);
                String carModel = cursor.getString(2);
                int carPrice = cursor.getInt(3);
                int carYear = cursor.getInt(4);
                String mot = cursor.getString(7);
                String insurance = cursor.getString(8);
                String path = cursor.getString(9);
                classCarModel newCar = new classCarModel(carID, carMake, carModel, carPrice, carYear, "", "", mot, insurance, path);
                returnList.add(newCar);



            }while(cursor.moveToNext());
       }else{
            //failure. do not add anything to the list.
        }
        //close both the cursor and the db when done.

        db.close();


        return returnList;
    }

}
