package com.example.android.icount;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import data.dataContract;
import data.entryTableHelper;

public class SearchActivity extends AppCompatActivity {

    EditText editText;
    public static ArrayList<String> list =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        editText=(EditText)findViewById(R.id.search1);
    }
    public void search(View v)
    {
        list.clear();


        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, Constants.URL_FETCH_ENTRY_TABLE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray products = new JSONArray(response);
                    //String s="";

                    for (int i = 0; i < products.length(); i++) {
                        JSONObject productObject = products.getJSONObject(i);

                        String EntryText=productObject.getString("EntryText");
                        String RollNumber=productObject.getString("RollNumber");

                        if(RollNumber.equals(editText.getText().toString()))
                        {
                            list.add(EntryText);
                        }

                    }
                    Collections.reverse(list);
                    Intent intent=new Intent(SearchActivity.this,SearchListActivity.class);
                    startActivity(intent);

                } catch (JSONException e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest1);




    }
}
