package com.example.android.icount;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView barcodeResult;
    Button scanbtn;
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
        scanbtn=(Button)findViewById(R.id.scan_barcode);
    }

    public void scanBarcode(View view)
    {
        Intent intent=new Intent(this,ScanBarcodeActivity.class);
        startActivityForResult(intent,0);
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
                    for(i=0;i<25;i++)
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
                    String entry;
                    Boolean b1=map1.get(s2);
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
                else {
                    barcodeResult.setText("No Barcode Found");
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void entryList(View view)
    {
        Intent intent=new Intent(MainActivity.this,NameListActivity.class);
        startActivity(intent);
    }
}
