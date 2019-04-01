package com.example.android.icount;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SearchListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);
        ArrayAdapter<String> itemsAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,SearchActivity.list);
        ListView listView=(ListView)findViewById(R.id.activity_search_list_items);
        listView.setAdapter(itemsAdapter);
    }
}
