package com.example.android.icount;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import data.dataContract;
import data.entryTableHelper;

public class SearchActivity extends AppCompatActivity {

    EditText editText;
    public static ArrayList<String> list =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        editText=(EditText)findViewById(R.id.search1);
    }
    public void search(View v)
    {
        list.clear();
        entryTableHelper mdataDbHelper=new entryTableHelper(SearchActivity.this);
        SQLiteDatabase db=mdataDbHelper.getReadableDatabase();
        String selection= dataContract.dataEntry.ROLL_NO+"=?";
        String []selectionArgs={editText.getText().toString()};

        Cursor cursor = db.query(dataContract.dataEntry.TABLE_NAME2, null, selection, selectionArgs, null, null, null);
        try{
            int EntryString = cursor.getColumnIndex(dataContract.dataEntry.ENTRY_TEXT);
            while(cursor.moveToNext())
            {
                String entryString=cursor.getString(EntryString);
                list.add(entryString);
            }
        }
        finally {
            cursor.close();
        }
        Intent intent=new Intent(SearchActivity.this,SearchListActivity.class);
        startActivity(intent);

    }
}
