package com.example.vmac.WatBot.Reviewapp;

import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.vmac.WatBot.R;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class createTicketActivity extends AppCompatActivity {
    String connect = "https://script.google.com/macros/s/AKfycbxS-4uGiQlQy95S7iCIHZqGVW1dXV_3WZPwx9_saKmnGTCrL6mBsKUpYkMDVx9O0iaD/exec";
    Button send;
    JSONObject data = new JSONObject();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createticket);
        final Spinner dropdown = findViewById(R.id.spinner1);
        final Spinner dropdown2 = findViewById(R.id.spinner2);
        final Spinner dropdown3 = findViewById(R.id.spinner3);
        final Spinner dropdown4 = findViewById(R.id.spinner4);
        final Spinner dropdown5 = findViewById(R.id.spinner5);
        final Spinner dropdown6 = findViewById(R.id.spinner6);
        final Spinner dropdown7 = findViewById(R.id.spinner7);
        send=findViewById(R.id.aaa);
        String[] items = new String[]{"1", "2", "3","4","5"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown2.setAdapter(adapter);
        dropdown3.setAdapter(adapter);
        dropdown4.setAdapter(adapter);
        dropdown5.setAdapter(adapter);
        dropdown6.setAdapter(adapter);
        dropdown7.setAdapter(adapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    data.put("q1",dropdown.getSelectedItemPosition()+1);
                    data.put("q2",dropdown2.getSelectedItemPosition()+1);
                    data.put("q3",dropdown3.getSelectedItemPosition()+1);
                    data.put("q4",dropdown4.getSelectedItemPosition()+1);
                    data.put("q5",dropdown5.getSelectedItemPosition()+1);
                    data.put("q6",dropdown6.getSelectedItemPosition()+1);
                    data.put("q7",dropdown7.getSelectedItemPosition()+1);



                } catch (JSONException e) {

                }
                final String[] response = {""};
                Thread a = new Thread(new Runnable(){
                    @Override
                    public void run() {
                        Post example = new Post();
                        try {
                    response[0] = example.post(connect, data.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                    }
                });
                a.start();
                try {
                    a.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(!response[0].isEmpty()){
                    System.out.print(response[0]);
                    showDialog("done");
            }
            else {
    showerrorDialog("please check your input!");

}
            }
        });



    }

    public class Post {
        public final MediaType JSON = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        String post(String url, String json) throws IOException {
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if (response.code() == 400) {
                    return null;
                }
                return response.body().string();

            }
        }
    }

    private void showDialog(String message){
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(createTicketActivity.this);
        normalDialog.setTitle("message");
        normalDialog.setMessage(message);
        normalDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        normalDialog.show();
    }
    private void showerrorDialog(String message){
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(createTicketActivity.this);
        normalDialog.setTitle("message");
        normalDialog.setMessage(message);
        normalDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      //  finish();
                    }
                });
        normalDialog.show();
    }
}
