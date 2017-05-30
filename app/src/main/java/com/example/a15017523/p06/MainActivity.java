package com.example.a15017523.p06;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button buttonAdd;
    ListView listViewTask;
    ArrayAdapter arrayAdapter;
    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper dbh = new DatabaseHelper(MainActivity.this);

        buttonAdd = (Button) findViewById(R.id.btnAdd);
        listViewTask= (ListView) findViewById(R.id.lvTask);

        arrayList = new ArrayList<String>();
        arrayList.addAll(dbh.getAllTask());
        arrayAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listViewTask.setAdapter(arrayAdapter);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(i, 9);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 9){
            buttonAdd.performClick();
            DatabaseHelper dbh = new DatabaseHelper(MainActivity.this);
            arrayList.clear();
            arrayList.addAll(dbh.getAllTask());
            dbh.close();
            arrayAdapter.notifyDataSetChanged();
        }
    }
}
