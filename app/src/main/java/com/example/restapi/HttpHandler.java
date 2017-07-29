package com.example.restapi;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class HttpHandler {
    String response;
    String TAG = HttpHandler.class.getSimpleName();
    public HttpHandler (){}

    public String makeServiceCall(String requrl) {
        response = null;
        try
        {
            URL url = new URL(requrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("GET");
            //read the response
            InputStream in=new BufferedInputStream(conn.getInputStream());
             //then convert stream to string
            response = convertStreamToString(in);

        }catch (MalformedURLException e){
            Log.e(TAG, "MalformedURLException: "+e.getMessage());
        }catch (IOException e){
            Log.e(TAG, "IOException: "+e.getMessage());
        }catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }

        return response;
    }
    public String convertStreamToString(InputStream is){
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line=null;
        try {
            while ((line=reader.readLine())!=null){
            sb.append(line).append("\n");
        }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                is.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}