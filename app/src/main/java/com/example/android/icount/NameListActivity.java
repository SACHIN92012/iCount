package com.example.android.icount;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import java.util.Collections;

public class NameListActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    public ArrayList<String> name2=new ArrayList();
//    ArrayAdapter<String> itemsAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, name2);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        makeList();

    }

    public void makeList(){

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Fetching Data ...");
        progressDialog.show();

        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, Constants.URL_FETCH_ENTRY_TABLE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray products = new JSONArray(response);
                    //String s="";

                    for (int i = 0; i < products.length(); i++) {
                        JSONObject productObject = products.getJSONObject(i);

                        int buildingNumber = productObject.getInt("buildingNumber");
                        String EntryText=productObject.getString("EntryText");
                        String RollNumber=productObject.getString("RollNumber");

                        if(buildingNumber==MainActivity.mBlock)
                        {
                            name2.add(EntryText);
                        }

                    }
                    progressDialog.dismiss();
                    showList();
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

    public void showList()
    {
        ArrayAdapter<String> itemsAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, name2);
        Collections.reverse(name2);
        //Toast.makeText(NameListActivity.this,MainActivity2.index+"",Toast.LENGTH_SHORT).show();
        ListView listView=(ListView)findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);
    }
}
