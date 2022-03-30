package com.example.cw;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public FloatingActionButton floatingActionButton;
    TextView important, tv_carimportant;
    Button button;

    RecyclerView rvPrograms;
    expensesAdapter expensesAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<classCarModel> contactsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        important = findViewById(R.id.textView3);
        tv_carimportant = findViewById(R.id.tv_carimportant);







        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }


        //database contact and get info
        DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
        List<classCarModel> allCars = dataBaseHelper.selectAll();

        rvPrograms = findViewById(R.id.rvPrograms);
        rvPrograms.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        rvPrograms.setLayoutManager(layoutManager);
        expensesAdapter = new expensesAdapter(MainActivity.this, allCars, rvPrograms);
        rvPrograms.setAdapter(expensesAdapter);


        //SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String[] date = currentDate.split("-");
        //Toast.makeText(this, ""+date[0], Toast.LENGTH_SHORT).show();
        int difference;
        int intDays = Integer.parseInt(date[0]);
        int intMonth = Integer.parseInt(date[1]);
        int intYears = Integer.parseInt(date[2]);

        int currentDays = intDays + intMonth*30 + intYears*365;
        //Toast.makeText(this, ""+day + "." + month + "." + "20"+year, Toast.LENGTH_SHORT).show();

        for(int i=0; i<allCars.size(); i++){
            classCarModel currentCar = allCars.get(i);

            String mot = currentCar.getMOT();
            String[] motDays = mot.split("-");

            int MOTDays = Integer.parseInt(motDays[0]);
            int MOTMonth = Integer.parseInt(motDays[1]);
            int MOTYear = Integer.parseInt(motDays[2]);
            int currentDaysMOT = MOTDays + MOTMonth*30 + MOTYear*365;

            String insurance = currentCar.getMOT();
            String ins = currentCar.getInsurance();
            String[] insDays = ins.split("-");

            int INSDays = Integer.parseInt(insDays[0]);
            int INSMonth = Integer.parseInt(insDays[1]);
            int INSYear = Integer.parseInt(insDays[2]);
            int currentDaysInsurance = INSDays + INSMonth*30 + INSYear*365;
            if(currentDaysMOT - currentDays < 14){
                important.setVisibility(View.VISIBLE);
                tv_carimportant.setVisibility(View.VISIBLE);
                tv_carimportant.setText(currentCar.toString());
            }else if(currentDaysInsurance - currentDays < 14){
                important.setVisibility(View.VISIBLE);
                tv_carimportant.setVisibility(View.VISIBLE);
                tv_carimportant.setText(currentCar.toString());
            }else{

            }
            }
       //Toast.makeText(MainActivity.this, allCars.toString(), Toast.LENGTH_SHORT).show();
//
//        ArrayAdapter carArrayAdapter = new ArrayAdapter<classCarModel>(MainActivity.this, android.R.layout.simple_list_item_1, allCars);
//        ListView list = (ListView) findViewById(R.id.carListView);
//        list.setAdapter(carArrayAdapter);

        //AdapterView.OnItemClickListener onItemClickListener = ;




        floatingActionButton = findViewById(R.id.fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, addNewCar.class);
                startActivity(intent);
            }
        });
        rvPrograms.addOnItemTouchListener(
                new RecyclerItemClickListener(MainActivity.this, rvPrograms ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        Integer id = allCars.get(position).getId();
                        Toast.makeText(MainActivity.this, ""+id, Toast.LENGTH_SHORT).show();
                        Intent openedCar = new Intent(MainActivity.this, openedCar.class);
                        position++;
                        //Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
                        openedCar.putExtra("passId", id);
                        startActivity(openedCar);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );


//        rvPrograms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//             classCarModel clickedCar = (classCarModel) parent.getItemAtPosition(position);
//             int carIDD = clickedCar.getId();
//             String passInfo = Integer.toString(carIDD);
//             Intent openedCar = new Intent(MainActivity.this, openedCar.class);
//             //Toast.makeText(MainActivity.this, "" + passInfo, Toast.LENGTH_SHORT).show();
//             openedCar.putExtra("passId", passInfo);
//             startActivity(openedCar);
//            }
//        });
    }





}