package com.example.android.icount;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import data.dataContract;
import data.dataDbHelper;

public class AddActivity extends AppCompatActivity {

    private int mGender=0;
    private Spinner mGenderSpinner;
    private dataDbHelper mdataDbHelper;
    String emp_name="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        mGenderSpinner = (Spinner) findViewById(R.id.spinner_gender);
        setupSpinner();
        mdataDbHelper=new dataDbHelper(this);
    }
    public void scanQRCode(View view){
        Intent intent=new Intent(AddActivity.this,ScanBarcodeActivity.class);
        startActivityForResult(intent,0);
    }
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mGenderSpinner.setAdapter(genderSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals("Block 4")) {
                        mGender = 4;
                    } else if (selection.equals("Block 5")) {
                        mGender = 5;
                    } else {
                        mGender = 6;
                    }
                }
            }
            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGender = 0;
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==0)
        {
            if(resultCode== CommonStatusCodes.SUCCESS)
            {
                //scanbtn.setVisibility(View.GONE);
                if(data!=null)
                {
                    Barcode barcode=data.getParcelableExtra("barcode");
                    Calendar calendar=Calendar.getInstance();
                    SimpleDateFormat format=new SimpleDateFormat("HH:mm:ss");
                    String time=format.format(calendar.getTime());
                    String p="";
                    //barcodeResult.setText(barcode.displayValue);
                    String s=String.valueOf(barcode.displayValue.toCharArray(),0,barcode.displayValue.length());
                    int count=0,i;
                    for(i=0;i<s.length();i++)
                    {
                        if(s.charAt(i)=='\n') {
                            if (count == 1)
                                break;
                            else {
                                count++;
                            }
                        }
                    }
                    String s2=String.valueOf(barcode.displayValue.toCharArray(),0,i+1);
                    emp_name=emp_name+s2;
                    Toast.makeText(AddActivity.this,s2,Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(AddActivity.this,"No QR code found",Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void selection(View view)
    {
        if(mGender==0)
        {
            Toast.makeText(this,"Select the Block",Toast.LENGTH_SHORT).show();
        }
        else if(emp_name.equals(""))
        {
            Toast.makeText(this,"Scan QR code",Toast.LENGTH_SHORT).show();
        }
        else
        {
            SQLiteDatabase db1=mdataDbHelper.getReadableDatabase();
            String []projection={
                    dataContract.dataEntry.COLUMN_NAME,
                    dataContract.dataEntry.COLUMN_BUILDING_NUMBER
            };
            String selection=dataContract.dataEntry.COLUMN_NAME+"=?";
            String []selectionArgs={emp_name};
            Cursor cursor=db1.query(dataContract.dataEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,null);

            int nameColumnIndex=cursor.getColumnIndex(dataContract.dataEntry.COLUMN_NAME);
            int buildingNumber=cursor.getColumnIndex(dataContract.dataEntry.COLUMN_BUILDING_NUMBER);
            int flag=0;
            while(cursor.moveToNext())
            {
                String currentName=cursor.getString(nameColumnIndex);
                int currentBuildingNo=cursor.getInt(buildingNumber);
                if(currentName.equals(emp_name) && mGender==currentBuildingNo)
                {
                    Toast.makeText(AddActivity.this,"Person Already exist in database",Toast.LENGTH_SHORT).show();
                    flag=1;
                    finish();
                }
            }
            if(flag==0) {
                SQLiteDatabase db = mdataDbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(dataContract.dataEntry.COLUMN_NAME, emp_name);
                values.put(dataContract.dataEntry.COLUMN_BUILDING_NUMBER, mGender);
                long newrowid = db.insert(dataContract.dataEntry.TABLE_NAME, null, values);
                Toast.makeText(this, "QR code added", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

}
