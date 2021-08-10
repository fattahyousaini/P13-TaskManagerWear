package com.myapplicationdev.android.p13_taskmanagerwear;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.RemoteInput;

public class ReplyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        CharSequence reply = null;
        Intent intent = getIntent();
        int replyId = intent.getIntExtra("id", 0);

        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null){
            reply = remoteInput.getCharSequence("status");

        }

        if(reply != null && reply.equals("completed")){
            Toast.makeText(ReplyActivity.this, "You have indicated: " + reply,
                    Toast.LENGTH_SHORT).show();
            DBHelper dbh = new DBHelper(ReplyActivity.this);
            long inserted_id = dbh.deleteTask(replyId);
            dbh.close();
            finish();
        }

    }
}