package com.example.cw;

import static android.location.LocationManager.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.Nullable;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.text.InputType;
import android.view.animation.Animation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;
//import com.squareup.picasso.Picasso;

import android.view.View;

import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class openedCar extends AppCompatActivity {
    Animation from_bottom_anim, to_bottom_anim, rotate_close_anim, rotate_open_anim;
    public FloatingActionButton floatingAddExpense, floatingAddGPS, floatingAddInsurance, floatingActionButton, floatingAddMOT, floatingActionButton2;
    Button btn_delCar, btn_getGPS;
    ImageView imgCar;
    TextView tv_location, tv_fabmot, tv_fabins, tv_expense;
    boolean isOpen;
    boolean clicked;
    int ids;
    double lon, lat;
    String searchLon, searchLat;



    public static final int PICK_IMAGE = 1;
    private String m_Text = "";


    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }





    public static String bundle2string(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        String string = "Bundle{";
        for (String key : bundle.keySet()) {
            string += " " + key + " => " + bundle.get(key) + ";";
        }
        string += " }Bundle";
        return string;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opened_car);




        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if(ContextCompat.checkSelfPermission(openedCar.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(openedCar.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(openedCar.this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }




        locationManager.requestLocationUpdates(GPS_PROVIDER, 10, 1, new LocationListener(){
            @Override
            public void onLocationChanged(Location location){
//                Toast.makeText(openedCar.this, "lon " + location.getLongitude() + " lat " + location.getLatitude(), Toast.LENGTH_SHORT).show();
                lon = location.getLongitude();
                lat = location.getLatitude();
            }

        });

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

        DataBaseHelper dataBaseHelper = new DataBaseHelper(openedCar.this);

        classCarModel currentCar = dataBaseHelper.searchOne(Search);
        Toast.makeText(this, ""+currentCar, Toast.LENGTH_SHORT).show();


        TextView tv_carMake = findViewById(R.id.tv_carMake);
        TextView tv_carModel = findViewById(R.id.tv_carModel);
        TextView tv_carPrice = findViewById(R.id.tv_carPrice);
        TextView tv_carYear = findViewById(R.id.tv_carYear);
        TextView tv_mot = findViewById(R.id.tv_mot);
        TextView tv_insurance = findViewById(R.id.tv_insurance);
        TextView tv_fabins = findViewById(R.id.tv_fabins);
        TextView tv_fabmot = findViewById(R.id.tv_fabmot);
        TextView tv_location = findViewById(R.id.tv_location);
        TextView tv_addexpense = findViewById(R.id.tv_expense);
        ImageView imgCar =  findViewById(R.id.iv_car);
        ListView lv_expense = findViewById(R.id.lv_expenses);

        //ShowExpensesOnListView(dataBaseHelper, currentCar, lv_expense);

        tv_carMake.setText(currentCar.Make);
        tv_carModel.setText(currentCar.Model);
        tv_carYear.setText(Integer.toString(currentCar.Year));
        tv_carPrice.setText(Integer.toString(currentCar.Price));
        tv_mot.setText(currentCar.getMOT());
        tv_insurance.setText(currentCar.getInsurance());
        searchLat = currentCar.getLatitude();
        searchLon = currentCar.getLongitude();
        String imgPath = currentCar.getImagepath();
//        Uri imgUri = Uri.parse(imgPath);
//        String Result = getRealPathFromUri(openedCar.this, imgUri);
//
//        Toast.makeText(this, "CREATED! "+Result, Toast.LENGTH_SHORT).show();
//        String imgPathPlus = imgPath + ".jpg";
//        imgCar.setVisibility(View.VISIBLE);
      //  Picasso.get().load(imgPath).into(imgCar);
        imgCar.setImageURI(Uri.parse(imgPath));





//        File imgFile = new  File(imgPathPlus);
//            Bitmap myBitmap = BitmapFactory.decodeFile("/storage/emulated/0/Pictures/IMG_20220327_210652.jpg");
//            imgCar.setImageBitmap(myBitmap);





        List <expenseModel> allExpenses = dataBaseHelper.selectAllExpenses(Search);
        ArrayAdapter expenseModelArrayAdapter = new ArrayAdapter<expenseModel>(openedCar.this, android.R.layout.simple_list_item_1, allExpenses);
        lv_expense.setAdapter(expenseModelArrayAdapter);

      //  Toast.makeText(openedCar.this, ""+allExpenses.toString(), Toast.LENGTH_SHORT).show();




        btn_delCar = findViewById(R.id.btn_delCar);
        btn_getGPS = findViewById(R.id.btn_getGPS);
        floatingAddExpense = findViewById(R.id.floatingAddExpense);
        floatingAddGPS = findViewById(R.id.floatingAddGPS);
        floatingAddInsurance = findViewById(R.id.floatingAddInsurance);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingAddMOT = findViewById(R.id.floatingAddMOT);
        floatingActionButton2 = findViewById(R.id.floatingActionButton2);



        from_bottom_anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.from_bottom_anim);
        to_bottom_anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.to_bottom_anim);
        rotate_close_anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_close_anim);
        rotate_open_anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_close_anim);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOpen) {
                    floatingAddExpense.startAnimation(to_bottom_anim);
                    floatingAddGPS.startAnimation(to_bottom_anim);
                    floatingAddInsurance.startAnimation(to_bottom_anim);
                    floatingAddMOT.startAnimation(to_bottom_anim);
                    floatingActionButton2.startAnimation(to_bottom_anim);
                    floatingActionButton.startAnimation(rotate_close_anim);
                    tv_fabins.startAnimation(to_bottom_anim);
                    tv_fabmot.startAnimation(to_bottom_anim);
                    tv_location.startAnimation(to_bottom_anim);
                    tv_addexpense.startAnimation(to_bottom_anim);


                    floatingAddExpense.setClickable(false);
                    floatingAddGPS.setClickable(false);
                    floatingAddInsurance.setClickable(false);
                    floatingAddMOT.setClickable(false);
                    floatingActionButton2.setClickable(false);

                    isOpen = false;

                } else {
                    floatingAddExpense.startAnimation(from_bottom_anim);
                    floatingAddGPS.startAnimation(from_bottom_anim);
                    floatingAddInsurance.startAnimation(from_bottom_anim);
                    floatingActionButton2.startAnimation(from_bottom_anim);
                    floatingAddMOT.startAnimation(from_bottom_anim);
                    tv_fabins.startAnimation(from_bottom_anim);
                    tv_fabmot.startAnimation(from_bottom_anim);
                    tv_location.startAnimation(from_bottom_anim);
                    tv_addexpense.startAnimation(from_bottom_anim);

                    floatingActionButton.startAnimation(rotate_open_anim);


                    floatingAddExpense.setClickable(true);
                    floatingActionButton2.setClickable(true);
                    floatingAddGPS.setClickable(true);
                    floatingAddInsurance.setClickable(true);
                    floatingAddMOT.setClickable(true);


                    floatingAddExpense.setVisibility(View.VISIBLE);
                    floatingAddGPS.setVisibility(View.VISIBLE);
                    floatingActionButton2.setVisibility(View.VISIBLE);
                    floatingAddInsurance.setVisibility(View.VISIBLE);
                    floatingAddMOT.setVisibility(View.VISIBLE);
                    tv_fabins.setVisibility(View.VISIBLE);
                    tv_fabmot.setVisibility(View.VISIBLE);
                    tv_location.setVisibility(View.VISIBLE);
                    tv_addexpense.setVisibility(View.VISIBLE);


                    isOpen = true;
                }

            }
        });
        floatingAddMOT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(openedCar.this, "You clicked on addmot", Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(openedCar.this);
                builder.setTitle("Add MOT");

                // Set up the input
                final EditText input = new EditText(openedCar.this);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        tv_mot.setText(m_Text);
                        currentCar.setMOT(m_Text);
                        dataBaseHelper.addMOTINS(currentCar);

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

            }
        });

        floatingAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(openedCar.this, "You clicked on Expense", Toast.LENGTH_LONG).show();
                Intent addExpense = new Intent(openedCar.this, addExpense.class);
                //Toast.makeText(MainActivity.this, "" + passInfo, Toast.LENGTH_SHORT).show();
                addExpense.putExtra("passId", currentCar.getId());
                startActivity(addExpense);
            }
        });
        String finalOutput = output;
        floatingAddGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DataBaseHelper dataBaseHelper2 = new DataBaseHelper(openedCar.this);
                String lonStr = String.valueOf(lon);
                String latStr = String.valueOf(lat);
                classCarModel carModel = new classCarModel(currentCar.getId(), currentCar.getMake(), currentCar.getModel(), currentCar.getPrice(), currentCar.getYear(), lonStr, latStr, currentCar.getMOT(), currentCar.getInsurance(), currentCar.getImagepath());
                dataBaseHelper2.addGPS(carModel);
                Toast.makeText(openedCar.this, "GPS added successfully! lon: " + lon + " lat: " + lat, Toast.LENGTH_SHORT).show();
            }
        });
        floatingAddInsurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(openedCar.this, "You clicked on Insurance", Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(openedCar.this);
                builder.setTitle("Add insurance");

                // Set up the input
                final EditText input = new EditText(openedCar.this);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        tv_insurance.setText(m_Text);
                        currentCar.setInsurance(m_Text);
                        dataBaseHelper.addMOTINS(currentCar);

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();





            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                DataBaseHelper dbHelp = new DataBaseHelper(openedCar.this);
                                dbHelp.deleteOne(currentCar.getId());
                                Intent intent = new Intent(openedCar.this, MainActivity.class);
                                startActivity(intent);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });
        btn_delCar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(openedCar.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btn_getGPS.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String stringLon = Double.toString(lon);
                String stringLat = Double.toString(lat);
                // Create a Uri from an intent string. Use the result to create an Intent.
                //Toast.makeText(openedCar.this, "GPS located! lon: " + lon + " lat: " + lat, Toast.LENGTH_SHORT).show();
                if(stringLon == null || stringLon == null || Double.parseDouble(stringLon) == 0.0 || Double.parseDouble(stringLat) == 0 ){
                    searchLon = currentCar.getLongitude();
                    searchLat = currentCar.getLatitude();
                    Toast.makeText(openedCar.this, "1!!! GPS located! lon: " + searchLon + " lat: " + searchLat, Toast.LENGTH_SHORT).show();
                    String geoUri = "http://maps.google.com/maps?q=loc:" + searchLon + "," + searchLat;
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri ));
                    startActivity(intent);
                }else {
                    Toast.makeText(openedCar.this, "2!!! GPS located! lon: " + stringLat + " lat: " + stringLon, Toast.LENGTH_SHORT).show();
                    String geoUri = "http://maps.google.com/maps?q=loc:" + stringLat + ", " + stringLon;
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                    startActivity(intent);
                }
            }
        });
        lv_expense.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                expenseModel expenseModel2 = (expenseModel) parent.getItemAtPosition(position);
                int carIDD = expenseModel2.getExpense_id();
                boolean done = dataBaseHelper.deleteExpense(carIDD);
                ShowExpensesOnListView(dataBaseHelper, currentCar, lv_expense);


            }
        });



    }

    private void ShowExpensesOnListView(DataBaseHelper dataBaseHelper, classCarModel currentCar, ListView lv_expense) {
        ArrayAdapter<expenseModel> expenseModelArrayAdapter = new ArrayAdapter<expenseModel>(openedCar.this, android.R.layout.simple_list_item_1, dataBaseHelper.selectAllExpenses(currentCar.getId()));
        lv_expense.setAdapter(expenseModelArrayAdapter);
    }



}