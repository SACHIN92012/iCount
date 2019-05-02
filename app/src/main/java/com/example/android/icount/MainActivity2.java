package com.example.android.icount;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import data.dataContract;
import data.dataDbHelper;
import data.entryTableHelper;

public class MainActivity2 extends AppCompatActivity {

    public static int start=0;
    TextView barcodeResult;
    int mGender=0;
    String emp_name;
    //Button scanbtn;
    public static int index=MainActivity.mBlock;
    //public static int index=5;
    public static int number[]={0,0,0,0,0,0,0};
    public static HashMap<String,Boolean> []map1=new HashMap[7];
    public static HashMap<String,Integer> []map2=new HashMap[7];
    public static HashMap <String,String> []map3=new HashMap[7];
    public static HashMap <String,Integer> []map4=new HashMap[7];
    //public static ArrayList <String> name=new ArrayList<>();
    public static ArrayList <String> name2[]=new ArrayList[7];
    public static Boolean b1;
    public static String s1,s2,time,entry,entry1,date;
    public static int flag1=0,check1=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        barcodeResult=(TextView)findViewById(R.id.barcode_result);
        index=MainActivity.mBlock;
        //scanbtn=(Button)findViewById(R.id.scan_barcode);
        if(start==0)
        {
            start=1;
            for(int i=0;i<7;i++)
            {
                name2[i]=new ArrayList<>();
                map1[i]=new HashMap<>();
                map2[i]=new HashMap<>();
                map3[i]=new HashMap<>();
                map4[i]=new HashMap<>();
            }
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        mGender=index;
        //name2[0].add("Sachin");
        //name2[1].add("Shubham");
        //Toast.makeText(MainActivity2.this,name2[0].get(0)+" "+name2[1].get(0),Toast.LENGTH_SHORT).show();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mGender==0)
                {
                    Toast.makeText(MainActivity2.this, "Select Building First", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(MainActivity2.this, ScanBarcodeActivity.class);
                    startActivityForResult(intent, 0);
                }
            }
        });

        if(name2[0].size()==0)
        {
            StringRequest stringRequest1 = new StringRequest(Request.Method.GET, Constants.URL_FETCH_ENTRY_TABLE, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONArray products = new JSONArray(response);
                        //String s="";
                        int flag2=0;
                        for (int i = 0; i < products.length(); i++) {
                            JSONObject productObject = products.getJSONObject(i);
                            int buildingNumber = productObject.getInt("buildingNumber");
                            String EntryTime=productObject.getString("EntryTime");
                            int isEntered=productObject.getInt("isEntered");
                            String EntryText=productObject.getString("EntryText");
                            int EntryNumber=productObject.getInt("EntryNumber");
                            String name = productObject.getString("name");
                            // s=s+buildingNumber+"\t"+name+"\n";
                            name2[0].add(EntryText);
                                //name2[buildingNumber].add(EntryText);
                            if(isEntered==1)
                            {
                                flag2++;
                                map1[0].put(name,true);
                                map2[0].put(name,EntryNumber);
                                map3[0].put(name,EntryTime);
                                map4[0].put(name,buildingNumber);

                            }
                            number[0]++;
                        }
                        //Toast.makeText(getApplicationContext(),flag2+"",Toast.LENGTH_SHORT).show();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.entryList2:
                Intent intent=new Intent(MainActivity2.this,NameListActivity.class);
                startActivity(intent);
                return true;

        }
        return super.onOptionsItemSelected(item);
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
                    time=format.format(calendar.getTime());

                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                    date = df.format(c);

                    String p="";
                    //barcodeResult.setText(barcode.displayValue);
                    String s=String.valueOf(barcode.displayValue.toCharArray(),0,barcode.displayValue.length());
                    int count=0,i,i1=0;

                    for(i=0;i<s.length();i++)
                    {
                        if(s.charAt(i)=='\n') {
                            if (count == 1)
                                break;
                            else {
                                i1=i;
                                count++;
                            }
                        }
                    }
                    s1=String.valueOf(barcode.displayValue.toCharArray(),i1+1,i-i1-1);
                    s2=String.valueOf(barcode.displayValue.toCharArray(),0,i+1);
                    //Toast.makeText(MainActivity2.this,s1,Toast.LENGTH_SHORT).show();
                    emp_name=s2;
                    //String entry,entry1;
                    b1=map1[0].get(s2);
                    flag1 = 0;
                    check1 = 0;
                    StringRequest stringRequest1 = new StringRequest(Request.Method.GET, Constants.URL_FETCH_DATA, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            check1 = 1;
                            try {
                                JSONArray products = new JSONArray(response);
                                //String s="";
                                for (int i = 0; i < products.length(); i++) {
                                    JSONObject productObject = products.getJSONObject(i);
                                    int buildingNumber = productObject.getInt("buildingNumber");
                                    String name = productObject.getString("name");
                                    // s=s+buildingNumber+"\t"+name+"\n";
                                    if(name.equals(emp_name))
                                        flag1=1;
                                    if (name.equals(emp_name) && buildingNumber == mGender) {
                                        //Toast.makeText(MainActivity2.this, "Person Already exist in database", Toast.LENGTH_SHORT).show();
                                        flag1 = 2;
                                        break;
                                    }

                                }
                                if(flag1==1)
                                    Toast.makeText(MainActivity2.this, "Person is not allowed in this building", Toast.LENGTH_SHORT).show();
                                else
                                {
                                    sachin2();
                                }


                                //Toast.makeText(AddActivity.this,s,Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            check1 = 1;
                            Toast.makeText(getApplicationContext(), "sachin", Toast.LENGTH_SHORT).show();

                        }
                    });

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest1);
                }
                else {
                    barcodeResult.setText("No QR Code Found");
                }
            }
        }

        Thread t=new Thread(){
            @Override
            public void run(){
                try {
                    Thread.sleep(2000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            barcodeResult.setText("");
                        }
                    });
                }
                catch (InterruptedException e)
                {

                }

            }
        };
        t.start();

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void sachin2()
    {
        if(b1!=null && map4[0].get(s2)!=index)
        {
            barcodeResult.setText("Person is already in block"+map4[0].get(s2));
            Toast.makeText(getApplicationContext(),"Person is already in block"+map4[0].get(s2),Toast.LENGTH_SHORT).show();
            return ;
        }
        if(b1==null)
        {
            map3[0].put(s2,time);
            map2[0].put(s2,number[0]);
            map4[0].put(s2,mGender);
            number[0]++;
            map1[0].put(s2,true);
            entry1="Entry Time :"+time+"\n"+"Date : "+date+"\n"+s2+"Block- "+mGender;
            name2[0].add(entry1);
            barcodeResult.setText(s2+"\n Entered");


            name2[index].add(entry1);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_ENTRY_TABLE, new Response.Listener<String>() {
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
                            Toast.makeText(getApplicationContext(), "sachin", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> response = new HashMap<>();

                    response.put("name", emp_name);
                    response.put("buildingNumber", mGender + "");
                    response.put("EntryText",entry1);
                    response.put("RollNumber",s1);
                    response.put("EntryTime",time);
                    response.put("isEntered",1+"");
                    response.put("EntryNumber",map2[0].get(s2)+"");

                    return response;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);



        }
        else
        {

            map1[0].put(s2, null);
            entry1 = "Entry Time :" + map3[0].get(s2) + "\t\t\t" + "\t\t\t\tExit Time :" + time + "\n"+"Date : "+date+"\n" + s2 + "Block- " + mGender;
            name2[0].add(map2[0].get(s2), entry1);
            name2[0].remove(map2[0].get(s2) + 1);
            barcodeResult.setText(s2 + "\n" + "Exited");


            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_UPDATE_ENTRY_TABLE, new Response.Listener<String>() {
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
                            Toast.makeText(getApplicationContext(), "sachin", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> response = new HashMap<>();

                    response.put("EntryText",entry1);
                    response.put("isEntered",2+"");
                    response.put("EntryNumber",map2[0].get(s2)+"");

                    return response;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);

        }






    }

}
