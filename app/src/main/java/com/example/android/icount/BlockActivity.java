package com.example.android.icount;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.ArrayList;
import java.util.HashMap;

public class BlockActivity extends AppCompatActivity {

    String emp_name;
    //Button scanbtn;
    int number=0;
    public static HashMap<String,Boolean> map1=new HashMap<>();
    public static HashMap<String,Integer> map2=new HashMap<>();
    public static HashMap <String,String> map3=new HashMap<>();
    public static HashMap <String,Integer> map4=new HashMap<>();
    public static ArrayList<String> name=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block);
        Button scanBtn=(Button)findViewById(R.id.scan);
        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BlockActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });

        Button entryList=(Button)findViewById(R.id.entry_List);
        entryList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BlockActivity.this,NameListActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==0)
        {
            if(resultCode== CommonStatusCodes.SUCCESS) {
                if(data!=null)
                {
                    TextView qrCode=(TextView)findViewById(R.id.qr);
                    Barcode barcode=data.getParcelableExtra("barcode");
                    String s=String.valueOf(barcode.displayValue.toCharArray(),0,barcode.displayValue.length());
                    qrCode.setText(s);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
