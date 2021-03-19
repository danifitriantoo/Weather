package com.example.apijava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.apijava.R.id.txtKasus;
import static com.example.apijava.R.id.txtProvinsi;

public class PostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        EditText x = (EditText) findViewById(txtProvinsi);
        EditText y = (EditText) findViewById(txtKasus);
        Button submitButton = (Button) findViewById(R.id.btnSend);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new HttpPostHandler().execute();
            }
        });

    }
}