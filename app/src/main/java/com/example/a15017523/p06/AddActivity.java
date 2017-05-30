package com.example.a15017523.p06;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    EditText editTextName, editTextDesc;
    Button buttonAdd, buttonCancel;
    TimePicker timePicker;
    DatePicker datePicker;
    int reqCode = 12345;
    Task content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editTextName = (EditText) findViewById(R.id.etName);
        editTextDesc = (EditText) findViewById(R.id.etDesc);

        datePicker = (DatePicker) findViewById(R.id.datePicker);
        timePicker = (TimePicker) findViewById(R.id.timePicker);

        buttonAdd = (Button) findViewById(R.id.btnAdd);
        buttonCancel = (Button) findViewById(R.id.btnCancel);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper dbh = new DatabaseHelper(AddActivity.this);
                String name = editTextName.getText().toString();
                String desc = editTextDesc.getText().toString();

                content = new Task(0, name, desc);
                dbh.insertTask(content);

                Integer minute1 = timePicker.getMinute();
                Integer hour1 = timePicker.getHour();
                Integer dayOfMonth = datePicker.getDayOfMonth();
                Integer month = datePicker.getMonth();
                Integer year = datePicker.getYear();

                Intent i = new Intent();
                setResult(RESULT_OK, i);

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth, hour1, minute1);

                Intent intent = new Intent(AddActivity.this, ScheduleNotificationReceiver.class);
                intent.putExtra("name", name);
                intent.putExtra("desc", desc);

//                Integer month = datePicker.getMonth();
//                Integer year = datePicker.getYear();
//                Integer date = datePicker.getDayOfMonth();

//                Toast.makeText(AddActivity.this, "Name: " + name + "\n desc: " + desc, Toast.LENGTH_LONG).show();

                PendingIntent pendingIntent = PendingIntent.getBroadcast(AddActivity.this, reqCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);

                AlarmManager am = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                finish();

            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}
