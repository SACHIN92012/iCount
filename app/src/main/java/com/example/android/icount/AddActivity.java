package com.example.android.icount;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import data.dataContract;
import data.dataDbHelper;

public class AddActivity extends AppCompatActivity {

    private int mGender = 0;
    private Spinner mGenderSpinner;
    private dataDbHelper mdataDbHelper;
    String emp_name = "";
    ProgressDialog progressDialog;
    public static int flag = 0, check = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        mGenderSpinner = (Spinner) findViewById(R.id.spinner_gender);
        setupSpinner();
        mdataDbHelper = new dataDbHelper(this);
        progressDialog = new ProgressDialog(this);
    }

    public void scanQRCode(View view) {
        Intent intent = new Intent(AddActivity.this, ScanBarcodeActivity.class);
        startActivityForResult(intent, 0);
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
                    if (selection.equals("Block 3")) {
                        mGender = 3;
                    } else if (selection.equals("Block 4")) {
                        mGender = 4;
                    } else {
                        mGender = 5;
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
        if (requestCode == 0) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                //scanbtn.setVisibility(View.GONE);
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra("barcode");
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                    String time = format.format(calendar.getTime());
                    String p = "";
                    //barcodeResult.setText(barcode.displayValue);
                    String s = String.valueOf(barcode.displayValue.toCharArray(), 0, barcode.displayValue.length());
                    int count = 0, i;
                    for (i = 0; i < s.length(); i++) {
                        if (s.charAt(i) == '\n') {
                            if (count == 1)
                                break;
                            else {
                                count++;
                            }
                        }
                    }
                    String s2 = String.valueOf(barcode.displayValue.toCharArray(), 0, i + 1);
                    emp_name =  s2;
                    Toast.makeText(AddActivity.this, s2, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddActivity.this, "No QR code found", Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void selection(View view) {
        if (mGender == 0) {
            Toast.makeText(this, "Select the Block", Toast.LENGTH_SHORT).show();
        } else if (emp_name.equals("")) {
            Toast.makeText(this, "Scan QR code", Toast.LENGTH_SHORT).show();
        } else {

            flag = 0;
            check = 0;
            StringRequest stringRequest1 = new StringRequest(Request.Method.GET, Constants.URL_FETCH_DATA, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    check = 1;
                    try {
                        JSONArray products = new JSONArray(response);
                        //String s="";
                        for (int i = 0; i < products.length(); i++) {
                            JSONObject productObject = products.getJSONObject(i);
                            int buildingNumber = productObject.getInt("buildingNumber");
                            String name = productObject.getString("name");
                            // s=s+buildingNumber+"\t"+name+"\n";
                            if (name.equals(emp_name) && buildingNumber == mGender) {
                                Toast.makeText(AddActivity.this, "Person Already exist in database", Toast.LENGTH_SHORT).show();
                                flag = 1;
                                break;
                            }

                        }
                        if(flag==0)
                            sachin();


                        //Toast.makeText(AddActivity.this,s,Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    check = 1;
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest1);


        }
    }

    public void sachin()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //Toast.makeText(AddActivity.this, "QR code Added", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {

                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> response = new HashMap<>();

                response.put("name", emp_name);
                response.put("buildingNumber", mGender + "");
                return response;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
        //Toast.makeText(getApplicationContext(),"sachin is great",Toast.LENGTH_SHORT).show();
        //finish();
    }

}
