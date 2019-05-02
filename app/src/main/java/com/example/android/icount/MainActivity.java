package com.example.android.icount;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static int mBlock=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button block3=(Button)findViewById(R.id.Block3);
        Button block4=(Button)findViewById(R.id.Block4);
        Button block5=(Button)findViewById(R.id.Block5);
        Button search=(Button)findViewById(R.id.search1);
        Button add=(Button)findViewById(R.id.add);
        Button graph=(Button)findViewById(R.id.graph);
        View.OnClickListener mListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId())
                {
                    case R.id.Block3:
                        mBlock=3;
                        Intent intent1=new Intent(MainActivity.this,MainActivity2.class);
                        startActivity(intent1);
                        break;
                    case R.id.Block4:
                        mBlock=4;
                        Intent intent =new Intent(MainActivity.this,MainActivity2.class);
                        startActivity(intent);
                        break;
                    case R.id.Block5:
                        mBlock=5;
                        Intent intent2 =new Intent(MainActivity.this,MainActivity2.class);
                        startActivity(intent2);
                        //mBlock=0;
                        break;
                    case R.id.search1:
                        Intent intent3=new Intent(MainActivity.this,SearchActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.add:
                        Intent intent4=new Intent(MainActivity.this,AddActivity.class);
                        startActivity(intent4);
                        break;
                    case R.id.graph:
                        Intent intent5=new Intent(MainActivity.this,GraphActivity.class);
                        startActivity(intent5);
                        break;
                }
                //mBlock=0;
            }
        };
        block3.setOnClickListener(mListener);
        block4.setOnClickListener(mListener);
        block5.setOnClickListener(mListener);
        search.setOnClickListener(mListener);
        add.setOnClickListener(mListener);
        graph.setOnClickListener(mListener);
    }
}
