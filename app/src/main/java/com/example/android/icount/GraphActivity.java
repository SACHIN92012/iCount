package com.example.android.icount;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.HashMap;

import data.dataContract;
import data.entryTableHelper;

public class GraphActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

                GraphView graph = (GraphView) findViewById(R.id.graph);
        BarGraphSeries<DataPoint> series ;
        HashMap <Integer,Integer> map1=new HashMap<>();
        for(int i=0;i<24;i++)
        {
            map1.put(i,0);
        }
        entryTableHelper mdataDbHelper=new entryTableHelper(GraphActivity.this);

        SQLiteDatabase db1=mdataDbHelper.getReadableDatabase();
        ContentValues values=new ContentValues();
        Cursor cursor = db1.query(dataContract.dataEntry.NUMBER_TABLE, null, null, null, null, null, null);
        try{
            int Time = cursor.getColumnIndex(dataContract.dataEntry.ENTRY_TIME);

            while (cursor.moveToNext()) {
                String time = cursor.getString(Time);
                String s = String.valueOf(time.toCharArray(), 0, 2);
                int a=Integer.parseInt(s);
                int b=map1.get(a);
                map1.put(a,b+1);
            }
            DataPoint dp[]=new DataPoint[24];
            int i=0;
            for(i=0;i<24;i++)
            {
                dp[i]=new DataPoint(i,map1.get(i));
            }
            series = new BarGraphSeries<>(dp);
            graph.addSeries(series);
        }
        finally{
            cursor.close();
        }
        String s="";
        for(int i=0;i<24;i++)
        {
            s=s+" "+map1.get(i);
        }
    }
}
