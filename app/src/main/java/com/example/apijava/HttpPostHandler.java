package com.example.apijava;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;


class HttpPostHandler extends AsyncTask<String, Void, String> {

    String responseString = "";
    int response;
    InputStream is = null;

    protected String doInBackground(String... Urls) {
        DataOutputStream wr = null;
        try {
            URL url = new URL("http://192.168.43.232:5000/api/TodoItems");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            String urlnew = "http://192.168.43.232:5000/api/TodoItems";
            URL obj = new URL(urlnew);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //add request header
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/json");

            // Send post request
            JSONObject obj2 = new JSONObject();
            obj2.put("id", 8);
            obj2.put("name", "dani");
            System.out.print(obj2);

            BufferedWriter out =
                    new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
            out.write(obj2.toString());
            out.close();

            int responseCode = con.getResponseCode();
            System.out.println("Response Code : " + responseCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}