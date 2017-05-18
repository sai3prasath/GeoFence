package com.devops.saiprasath.geofence;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mongodb.DBObject;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by saiprasath on 5/18/2017.
 */

public class Rest_Service_2 extends AppCompatActivity  {

        private static final String TAG = "RestService Activity";
        public ArrayList<DBObject> array;
        int response=1;
        public JSONObject jsonObject=null;
        ListView listView;
        public rest_service_2 restservice;
        public String line;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.rest_layout);
            listView = (ListView)findViewById(R.id.listview);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getApplicationContext(),"You Clicked "+(position+1),Toast.LENGTH_LONG).show();
                }
            });
            restservice.execute();
        }
        private class rest_service_2 extends AsyncTask<Void,Void,JSONObject> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Log.e(TAG,"IN onPreExecute()");
            }

            @Override
            protected JSONObject doInBackground(Void... params) {

                char[] cbuf;
                try
                {
                    URL url = new URL("http://127.0.0.1:27017");
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setAllowUserInteraction(true);
                    httpURLConnection.setConnectTimeout(300000);
                    httpURLConnection.connect();
                    if (httpURLConnection.getResponseCode()!=-1)
                    {
                        Log.e(TAG,"Cannot get Response Code");
                    }
                    InputStream instream = httpURLConnection.getInputStream();
                    StringBuffer stringBuffer = new StringBuffer();
                    if (instream==null) return null;
                    BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
                    while ((line=reader.readLine())!=null){
                        stringBuffer.append(line);
                }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                return jsonObject;
            }

            @Override
            protected void onPostExecute(JSONObject arrayList) {
                super.onPostExecute(arrayList);
                arrayList = jsonObject;
                Log.e(TAG,"IN onPostExecute()");
            }
        }
    }

}
