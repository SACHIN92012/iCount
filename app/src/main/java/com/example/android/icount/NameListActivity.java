package com.example.android.icount;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class NameListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        ArrayAdapter<String> itemsAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,MainActivity.name);
        ListView listView=(ListView)findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);
    }
}
