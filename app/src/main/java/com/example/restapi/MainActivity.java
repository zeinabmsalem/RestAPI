package com.example.restapi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String TAG = MainActivity.class.getSimpleName();
    String jsondata;
    String url = "http://www.thecrazyprogrammer.com/example_data/fruits_array.json";
    ListView listview;
    ArrayList fruitlist;
    ArrayAdapter dap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview= (ListView) findViewById(R.id.listview);
        fruitlist = new ArrayList();
        boolean netStatus = isNetworkConnected();
        if(netStatus)
          {new GetJsonData().execute();
          }else {
                Toast toast = Toast.makeText(MainActivity.this, "please check your internet connection", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
               }


    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

   private class GetJsonData extends AsyncTask<String, String, String>{

    @Override
    protected String doInBackground(String... strings) {
        HttpHandler handler = new HttpHandler();
        jsondata = handler.makeServiceCall(url);
        try
        {
            JSONObject obj = new JSONObject(jsondata);
            JSONArray fruitsarray = obj.getJSONArray("fruits");
            for (int i=0;i<fruitsarray.length();i++){
                fruitlist.add(fruitsarray.getString(i));
            }

        }catch (JSONException e){
            Log.e(TAG, "JSONException: "+e.getMessage());
        }

         return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        dap = new ArrayAdapter (MainActivity.this, android.R.layout.simple_list_item_1,fruitlist);
        listview.setAdapter(dap);
    }
}

}
