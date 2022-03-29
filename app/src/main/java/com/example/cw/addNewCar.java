package com.example.cw;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.Date;

public class addNewCar extends AppCompatActivity {

    //references for buttons and other controls on the layout
    Button btnCancel, btnAdd, btnImage;
    EditText carMake, carModel;
    EditText carYear, carPrice;
    EditText carInsurance, carMOT;
    ImageView carPic;
    Uri selImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_car);

btnAdd = findViewById(R.id.btnAdd);
btnCancel = findViewById(R.id.btnCancel);
carMake = findViewById(R.id.carMake);
carModel = findViewById(R.id.carModel);
carYear = findViewById(R.id.carYear);
carPrice = findViewById(R.id.carPrice);
carInsurance = findViewById(R.id.tv_carInsurance);
carMOT = findViewById(R.id.tv_carMOT);
carPic = findViewById(R.id.iv_carPic);
btnImage = findViewById(R.id.btn_addImage);




//button Listeners

        btnImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);

                }

        });


        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                classCarModel carModel1;
                try{
                    String path = selImage.toString();
                    Toast.makeText(addNewCar.this, ""+path, Toast.LENGTH_SHORT).show();
                    carModel1 = new classCarModel(-1, carMake.getText().toString(), carModel.getText().toString(), Integer.parseInt(carYear.getText().toString()), Integer.parseInt(carPrice.getText().toString()), "", "", carMOT.getText().toString(), carInsurance.getText().toString(), path);

                    //Toast.makeText(addNewCar.this, carModel1.toString(), Toast.LENGTH_SHORT).show();
                }catch(Exception e){
                    Toast.makeText(addNewCar.this, "Error adding a car", Toast.LENGTH_SHORT).show();
                    carModel1 = new classCarModel(-1, "error", "error", 0, 0, "error","error", "error", "error", "error");
                }

                DataBaseHelper dataBaseHelper = new DataBaseHelper(addNewCar.this);
                boolean success = dataBaseHelper.addOne(carModel1);

                Toast.makeText(addNewCar.this, "Success= " + success, Toast.LENGTH_SHORT).show();

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(addNewCar.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            selImage = selectedImage;
            File finalFile = new File(getRealPathFromURI(selectedImage));
            carPic.setImageURI(selImage);

            Toast.makeText(this, ""+Uri.fromFile(finalFile), Toast.LENGTH_SHORT).show();
        }
    }
}