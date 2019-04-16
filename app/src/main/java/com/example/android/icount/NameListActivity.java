package com.example.android.icount;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class NameListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        ArrayAdapter<String> itemsAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, MainActivity2.name2[MainActivity.mBlock]);
        //Toast.makeText(NameListActivity.this,MainActivity2.index+"",Toast.LENGTH_SHORT).show();
        ListView listView=(ListView)findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);
    }
}
