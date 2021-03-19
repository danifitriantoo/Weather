package com.example.apijava;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class weatherActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;
    private List<String> allNames = new ArrayList<String>();

    // URL to get contacts JSON
    private static String url = "http://192.168.56.1:5000/api/TodoItems/";

    TextView x;
    TextView y;
    ImageView plus;

    ArrayList<HashMap<String, String>> contactList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        getSupportActionBar().hide();

        x = (TextView) findViewById(R.id.txtCelcius);
        y = (TextView) findViewById(R.id.txtCity);
        plus = (ImageView) findViewById(R.id.btnA);

        contactList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list2);
        new weatherActivity.GetContacts().execute();

        ImageView btn = (ImageView) findViewById(R.id.btnA);
        btn.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view) {
                    Intent myIntent = new Intent(view.getContext(), loginActivity.class);
                    startActivityForResult(myIntent, 0);
                }

            });

    }



    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(weatherActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    // Getting JSON Array node
                    JSONArray jArr = new JSONArray(jsonStr);

                    for (int count = 0; count < jArr.length(); count++) {
                        JSONObject obj = jArr.getJSONObject(count);
                        String id = obj.getString("id");
                        String name = obj.getString("name");
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("id", id);
                        contact.put("name", name);
                        allNames.add(id);
                        contactList.add(contact);

                        x.setText(id);
                        y.setText(name);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */

            ListAdapter adapter = new SimpleAdapter(
                    weatherActivity.this, contactList,
                    R.layout.list_item, new String[]{"id", "id",
                    "http://openweathermap.org/img/wn/10n@2x.png"}, new int[]{R.id.name,
                    R.id.email,R.id.imageView});
            lv.setAdapter(adapter);
        }

    }
}