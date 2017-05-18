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

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.json.JSONObject;

/**
 * Created by saiprasath on 5/18/2017.
 */

public class Rest_service extends AppCompatActivity {
    private static final String TAG = "RestService Activity";
    public ArrayList<DBObject> array;
    JSONObject jsonresponse;
    int response=1;
    ListView listView;
    public rest_service restservice;

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
    private class rest_service extends AsyncTask<Void,Void,ArrayList<DBObject>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e(TAG,"IN onPreExecute()");
        }

        @Override
        protected ArrayList<DBObject> doInBackground(Void... params) {

            array = new ArrayList<>();
            try
            {
                MongoClientURI url = new MongoClientURI("http://127.0.0.1:27017");
                MongoClient client = new MongoClient(url);
                DB db = client.getDB("hospitals");
                DBCollection hospitals = db.getCollection("hospitals");
                DBCursor query = hospitals.find().limit(5);
                while (query.hasNext()){
                    DBObject dbObject = query.next();
                    List<DBObject> hospital_list = query.toArray();
                    System.out.print(hospital_list);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return array;
        }

        @Override
        protected void onPostExecute(ArrayList<DBObject> arrayList) {
            super.onPostExecute(arrayList);
            arrayList = array;
            Log.e(TAG,"IN onPostExecute()");
        }
    }
}
