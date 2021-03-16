package com.example.apijava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

        Button submitButton = (Button) findViewById(R.id.btnSend);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                JSONObject postData = new JSONObject();
                try {

                    postData.put("provinsi", (EditText)findViewById(txtProvinsi).getText().toString());
                    postData.put("kasusPosi", (EditText)findViewById(txtKasus).getText().toString());

                    new HttpPostHandler().execute("https://indonesia-covid-19.mathdro.id/api/provinsi/", postData.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}