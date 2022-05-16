package com.example.vmac.WatBot.Reviewapp;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.vmac.WatBot.R;
import androidx.appcompat.app.AppCompatActivity;

public class ticketHome extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickethome);
        Button ran = findViewById(R.id.randam);
        ran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ticketHome.this, createTicketActivity.class);
                startActivity(intent);
            }
        });


    }
}

