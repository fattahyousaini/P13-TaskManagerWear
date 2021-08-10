package com.myapplicationdev.android.p13_taskmanagerwear;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;

import java.util.ArrayList;
import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    Button btnAddTask, btnCancel;
    EditText etName, etDescription, etRemind;
    int reqCode = 12345;
    ArrayList<Task> tasksList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        btnCancel = findViewById(R.id.btnCancel);
        btnAddTask = findViewById(R.id.btnAddTask);
        etName = findViewById(R.id.etName);
        etDescription = findViewById(R.id.etDescription);
        etRemind = findViewById(R.id.etRemind);

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String dataName = etName.getText().toString();
                String dataDescription = etDescription.getText().toString();
                int dataSeconds = Integer.parseInt(etRemind.getText().toString());
                DBHelper dbh = new DBHelper(AddActivity.this);
                long inserted_id = dbh.addTask(dataName, dataDescription);
                tasksList = new ArrayList<Task>();
                tasksList.addAll(dbh.getAllTasks());
                int indexId = tasksList.size()-1;
                Task object = tasksList.get(indexId);
                int id = object.getId();
                dbh.close();


                if (inserted_id != -1) {
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.SECOND, dataSeconds);

                    Intent i = new Intent(AddActivity.this, ScheduledNotificationReceiver.class);
                    i.putExtra("task" ,dataName);
                    i.putExtra("description" ,dataDescription);
                    i.putExtra("id",id);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(AddActivity.this, reqCode, i, PendingIntent.FLAG_CANCEL_CURRENT);

                    AlarmManager am = (AlarmManager)getSystemService(Activity.ALARM_SERVICE);
                    am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

                    Toast.makeText(AddActivity.this, "Task added!", Toast.LENGTH_SHORT).show();
                    finish();
                }


            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}