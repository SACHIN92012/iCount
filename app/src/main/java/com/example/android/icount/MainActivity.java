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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView barcodeResult;
    Button scanbtn;
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
                    barcodeResult.setText(barcode.displayValue);
                    name.add(barcode.displayValue);
                }
                else
                {
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
