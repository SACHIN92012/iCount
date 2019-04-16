package com.example.android.icount;

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

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

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
        //name.clear();
        if(1==1)
         {
            entryTableHelper mdataDbHelper = new entryTableHelper(MainActivity2.this);

            SQLiteDatabase db1 = mdataDbHelper.getReadableDatabase();
            ContentValues values = new ContentValues();

            String[] projection = {
                    dataContract.dataEntry.COLUMN_NAME,
                    dataContract.dataEntry.COLUMN_BUILDING_NUMBER, dataContract.dataEntry.ENTRY_TEXT, dataContract.dataEntry.IS_ENTERED, dataContract.dataEntry.ENTRY_TIME, dataContract.dataEntry.ENTRY_NUMBER
            };
            String selection = dataContract.dataEntry.COLUMN_NAME + "=?";
            String[] selectionArgs = {emp_name};


            Cursor cursor = db1.query(dataContract.dataEntry.BLOCK[index], null, null, null, null, null, null);
            try {

                if(name2[index].size()==0) {

                    int nameColumnIndex = cursor.getColumnIndex(dataContract.dataEntry.COLUMN_NAME);
                    int buildingNumber = cursor.getColumnIndex(dataContract.dataEntry.COLUMN_BUILDING_NUMBER);
                    int EntryString = cursor.getColumnIndex(dataContract.dataEntry.ENTRY_TEXT);
                    int IsEntered = cursor.getColumnIndex(dataContract.dataEntry.IS_ENTERED);
                    int Time = cursor.getColumnIndex(dataContract.dataEntry.ENTRY_TIME);
                    int EntryNumber = cursor.getColumnIndex(dataContract.dataEntry.ENTRY_NUMBER);
                    int flag = 0;

                    while (cursor.moveToNext()) {
                        String currentName = cursor.getString(nameColumnIndex);
                        int currentBuildingNo = cursor.getInt(buildingNumber);
                        String time = cursor.getString(Time);
                        int isEntered = cursor.getInt(IsEntered);
                        String entryString = cursor.getString(EntryString);
                        int entryNumber = cursor.getInt(EntryNumber);

                        name2[index].add(entryString);
                        if (isEntered == 1) {
                            flag++;
                            map1[index].put(currentName, true);
                            map2[index].put(currentName, entryNumber);
                            map3[index].put(currentName, time);
                            map4[index].put(currentName, currentBuildingNo);
                        }
                        number[index]++;
                    }
                }

            } finally {
                cursor.close();
            }


                if(name2[0].size()==0)
                {
                    Cursor cursor2 = db1.query(dataContract.dataEntry.TABLE_NAME2, null, null, null, null, null, null);
                try {

                    int nameColumnIndex2 = cursor2.getColumnIndex(dataContract.dataEntry.COLUMN_NAME);
                    int buildingNumber2 = cursor2.getColumnIndex(dataContract.dataEntry.COLUMN_BUILDING_NUMBER);
                    int EntryString2 = cursor2.getColumnIndex(dataContract.dataEntry.ENTRY_TEXT);
                    int IsEntered2 = cursor2.getColumnIndex(dataContract.dataEntry.IS_ENTERED);
                    int Time2 = cursor2.getColumnIndex(dataContract.dataEntry.ENTRY_TIME);
                    int EntryNumber2 = cursor2.getColumnIndex(dataContract.dataEntry.ENTRY_NUMBER);
                    int flag2 = 0;

                    while (cursor2.moveToNext()) {
                        String currentName = cursor2.getString(nameColumnIndex2);
                        int currentBuildingNo = cursor2.getInt(buildingNumber2);
                        String time = cursor2.getString(Time2);
                        int isEntered = cursor2.getInt(IsEntered2);
                        String entryString = cursor2.getString(EntryString2);
                        int entryNumber = cursor2.getInt(EntryNumber2);

                        name2[0].add(entryString);
                        if (isEntered == 1) {
                            flag2++;
                            map1[0].put(currentName, true);
                            map2[0].put(currentName, entryNumber);
                            map3[0].put(currentName, time);
                            map4[0].put(currentName, currentBuildingNo);
                        }
                        number[0]++;
                    }
                }
                finally {
                    cursor2.close();
                }
                }

        }
    }

//    public void scanBarcode(View view)
//    {
//        Intent intent=new Intent(this,ScanBarcodeActivity.class);
//        startActivityForResult(intent,0);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
//            case R.id.block3:
//                mGender=3;
//                Toast.makeText(MainActivity2.this,"Block 3 is selected",Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.block4:
//                mGender=4;
//                Toast.makeText(MainActivity2.this,"Block 4 is selected",Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.block5:
//                mGender=5;
//                Toast.makeText(MainActivity2.this,"Block 5 is selected",Toast.LENGTH_SHORT).show();
//                return true;
            case R.id.entryList2:
                Intent intent=new Intent(MainActivity2.this,NameListActivity.class);
                startActivity(intent);
                return true;
//            case R.id.add:
//                Intent intent1=new Intent(MainActivity2.this,AddActivity.class);
//                startActivity(intent1);
//                return true;
//            case R.id.search:
//                Intent intent3=new Intent(MainActivity2.this,SearchActivity.class);
//                startActivity(intent3);
//                return true;
//
//            case R.id.graph:
//                Intent intent2=new Intent(MainActivity2.this,GraphActivity.class);
//                startActivity(intent2);
//                return true;
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
                    String time=format.format(calendar.getTime());
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
                    String s1=String.valueOf(barcode.displayValue.toCharArray(),i1+1,i-i1-1);
                    String s2=String.valueOf(barcode.displayValue.toCharArray(),0,i+1);
                    //Toast.makeText(MainActivity2.this,s1,Toast.LENGTH_SHORT).show();
                    emp_name=s2;
                    String entry,entry1;
                    Boolean b1=map1[index].get(s2);


                    dataDbHelper mdataDbHelper=new dataDbHelper(MainActivity2.this);

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
                        if(currentName.equals(emp_name))
                        {
                            flag=1;
                        }
                        if(currentName.equals(emp_name) && mGender==currentBuildingNo)
                        {
                            flag=2;
                            break;
                        }
//                        else if(currentName.equals(emp_name) && mGender==currentBuildingNo)
//                        {
//                            flag=0;
//                            break;
//                        }
                    }
                    cursor.close();
                    if(flag==1)
                    {
                        Toast.makeText(MainActivity2.this,"Person is not allowed in this Building",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        entryTableHelper mentryTableHelper=new entryTableHelper(MainActivity2.this);
                        SQLiteDatabase db2=mentryTableHelper.getWritableDatabase();
                        ContentValues values=new ContentValues();
                        ContentValues values1=new ContentValues();
                        ContentValues values2=new ContentValues();

                        if(b1==null)
                        {



                            map3[0].put(s2,time);
                            map2[0].put(s2,number[0]);
                            map4[0].put(s2,mGender);
                            number[0]++;
                            map1[0].put(s2,true);
                            entry1="Entry Time :"+time+"\n"+s2+"Block- "+mGender;
                            name2[0].add(entry1);




                            values2.put(dataContract.dataEntry.COLUMN_NAME,s2);
                            values2.put(dataContract.dataEntry.ENTRY_TEXT,entry1);
                            values2.put(dataContract.dataEntry.COLUMN_BUILDING_NUMBER,mGender);
                            values2.put(dataContract.dataEntry.ENTRY_TIME,time);
                            values2.put(dataContract.dataEntry.ENTRY_NUMBER,map2[0].get(s2));
                            values2.put(dataContract.dataEntry.IS_ENTERED,1);
                            values2.put(dataContract.dataEntry.ROLL_NO,s1);
                            //values.put(dataContract.dataEntry.NUMBER,number);
                            db2.insert(dataContract.dataEntry.TABLE_NAME2,null,values2);






                            map3[index].put(s2,time);
                            map2[index].put(s2,number[index]);
                            map4[index].put(s2,mGender);
                            number[index]++;
                            map1[index].put(s2,true);
                            entry=(number[index])+". Entry Time :"+time+"\n"+s2+"Block- "+mGender;
                            name2[index].add(entry);
                            barcodeResult.setText(s2+"\n"+"Entered");
                            values.put(dataContract.dataEntry.COLUMN_NAME,s2);
                            values.put(dataContract.dataEntry.ENTRY_TEXT,entry);
                            values.put(dataContract.dataEntry.COLUMN_BUILDING_NUMBER,mGender);
                            values.put(dataContract.dataEntry.ENTRY_TIME,time);
                            values.put(dataContract.dataEntry.ENTRY_NUMBER,map2[index].get(s2));
                            values.put(dataContract.dataEntry.IS_ENTERED,1);
                            values.put(dataContract.dataEntry.ROLL_NO,s1);
                            //values.put(dataContract.dataEntry.NUMBER,number);
                            db2.insert(dataContract.dataEntry.BLOCK[index],null,values);
                            //values1.put(dataContract.dataEntry.NUMBER,number);
//                            if(number!=1)
                            // db2.update(dataContract.dataEntry.NUMBER_TABLE,values1,dataContract.dataEntry._ID+"=1",null);
//                            else
//                                db2.insert(dataContract.dataEntry.NUMBER_TABLE,null,values1);
                            values1.put(dataContract.dataEntry.ENTRY_TIME,time);
                            db2.insert(dataContract.dataEntry.NUMBER_TABLE,null,values1);
                        }
                        else {
                            int p1=1;//can be removed
                            if(map4[0].get(s2)!=mGender && p1==0)
                            {
                                Toast.makeText(MainActivity2.this,"Person is already in Block"+map4[0].get(s2)+"",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                map1[index].put(s2, null);
                                entry = (map2[index].get(s2) + 1) + ". Entry Time :" + map3[index].get(s2) + "\t\t\t" + "Exit Time :" + time + "\n" + s2 + "Block- " + mGender;
                                name2[index].add(map2[index].get(s2), entry);


                                //Toast.makeText(MainActivity2.this,map2[index].get(s2)+1+"",Toast.LENGTH_SHORT).show();

                                name2[index].remove(map2[index].get(s2) + 1);
                                barcodeResult.setText(s2 + "\n" + "Exited");



                                map1[0].put(s2, null);
                                entry1 = "Entry Time :" + map3[0].get(s2) + "\t\t\t" + "Exit Time :" + time + "\n" + s2 + "Block- " + mGender;
                                name2[0].add(map2[0].get(s2), entry1);
                                name2[0].remove(map2[0].get(s2) + 1);
                                barcodeResult.setText(s2 + "\n" + "Exited");



                                //values.put(dataContract.dataEntry.COLUMN_NAME,s2);
                                values2.put(dataContract.dataEntry.ENTRY_TEXT, entry1);
//                              //values.put(dataContract.dataEntry.COLUMN_BUILDING_NUMBER,mGender[index]);
                                values2.put(dataContract.dataEntry.IS_ENTERED, 2);
                                db2.update(dataContract.dataEntry.TABLE_NAME2, values2, dataContract.dataEntry.ENTRY_NUMBER + "=" + map2[0].get(s2), null);




                                //values.put(dataContract.dataEntry.COLUMN_NAME,s2);
                                values.put(dataContract.dataEntry.ENTRY_TEXT, entry);
//                              //values.put(dataContract.dataEntry.COLUMN_BUILDING_NUMBER,mGender[index]);
                                values.put(dataContract.dataEntry.IS_ENTERED, 2);
                                //db2.update(dataContract.dataEntry.BLOCK[index], values, dataContract.dataEntry.ENTRY_NUMBER + "=" + map2[index].get(s2), null);
                                db2.update(dataContract.dataEntry.BLOCK[index], values, dataContract.dataEntry.ENTRY_NUMBER + "=" + map2[index].get(s2), null);
                            }
                        }
                    }

                }
                else {
                    barcodeResult.setText("No Barcode Found");
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
//    public void entryList(View view)
//    {
//        Intent intent=new Intent(MainActivity2.this,NameListActivity.class);
//        startActivity(intent);
//    }
}
