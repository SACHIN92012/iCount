package com.example.android.icount;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import data.dataContract;
import data.dataDbHelper;

public class MainActivity extends AppCompatActivity {

    TextView barcodeResult;
    int mGender=0;
    String emp_name;
    //Button scanbtn;
    int number=0;
    public static HashMap<String,Boolean> map1=new HashMap<>();
    public static HashMap<String,Integer> map2=new HashMap<>();
    public static HashMap <String,String> map3=new HashMap<>();
    public static ArrayList <String> name=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        barcodeResult=(TextView)findViewById(R.id.barcode_result);
        //scanbtn=(Button)findViewById(R.id.scan_barcode);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mGender==0)
                {
                    Toast.makeText(MainActivity.this, "Select Building First", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(MainActivity.this, ScanBarcodeActivity.class);
                    startActivityForResult(intent, 0);
                }
            }
        });
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
            case R.id.block3:
                mGender=3;
                Toast.makeText(MainActivity.this,"Block 3 is selected",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.block4:
                mGender=4;
                Toast.makeText(MainActivity.this,"Block 4 is selected",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.entryList2:
                Intent intent=new Intent(MainActivity.this,NameListActivity.class);
                startActivity(intent);
                return true;
            case R.id.add:
                Intent intent1=new Intent(MainActivity.this,AddActivity.class);
                startActivity(intent1);
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
                    emp_name=s2;
                    String entry;
                    Boolean b1=map1.get(s2);


                    dataDbHelper mdataDbHelper=new dataDbHelper(MainActivity.this);

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
                    if(flag==1)
                    {
                        Toast.makeText(MainActivity.this,"Person is not allowed in this Building",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        if(b1==null)
                        {
                            map3.put(s2,time);
                            map2.put(s2,number);
                            number++;
                            map1.put(s2,true);
                            entry=(number)+". Entry Time :"+time+"\n"+s2;
                            name.add(entry);
                            barcodeResult.setText(s2+"\n"+"Entered");
                        }
                        else
                        {
                            map1.put(s2,null);
                            entry=(map2.get(s2)+1)+". Entry Time :"+map3.get(s2)+"\t\t\t"+"Exit Time :"+time+"\n"+s2;
                            name.add(map2.get(s2),entry);
                            name.remove(map2.get(s2)+1);
                            barcodeResult.setText(s2+"\n"+"Exited");
                        }
                    }

                }
                else {
                    barcodeResult.setText("No Barcode Found");
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
//    public void entryList(View view)
//    {
//        Intent intent=new Intent(MainActivity.this,NameListActivity.class);
//        startActivity(intent);
//    }
}
