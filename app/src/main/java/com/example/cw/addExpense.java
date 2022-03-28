package com.example.cw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class addExpense extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        EditText expenseAmount, expenseReason;

        Button btnAdd = findViewById(R.id.btn_addExpense);
        expenseReason = findViewById(R.id.editTextTextPersonName2);
        expenseAmount = findViewById(R.id.editTextTextPersonName);

        Intent intent = getIntent();
        Bundle IDs = getIntent().getExtras();
        String output = null; // = bundle2string(IDs);
        for (String key : IDs.keySet()) {
            output = IDs.get(key) + "";
        }
        //Toast.makeText(this, "" + output, Toast.LENGTH_SHORT).show();
        String ID;
        if (IDs != null) {
            ID = IDs.getString("passID");
            //idSearch = Integer.parseInt(ID);
        }
        Integer Search = Integer.parseInt(output);
        //Search++;

        DataBaseHelper dataBaseHelper = new DataBaseHelper(addExpense.this);

        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                expenseModel expenseModel1;
                try{
                    expenseModel1 = new expenseModel(-1, Search , expenseAmount.getText().toString(), expenseReason.getText().toString());

                    //Toast.makeText(addNewCar.this, carModel1.toString(), Toast.LENGTH_SHORT).show();
                }catch(Exception e){
                    Toast.makeText(addExpense.this, "Error adding a car", Toast.LENGTH_SHORT).show();
                    expenseModel1 = new expenseModel(-1, -1, "error", "error");
                }

                DataBaseHelper dataBaseHelper = new DataBaseHelper(addExpense.this);
                boolean success = dataBaseHelper.addExpense(expenseModel1, Integer.toString(Search));

                Toast.makeText(addExpense.this, "Success= " + success, Toast.LENGTH_SHORT).show();

            }
        });
    }
}