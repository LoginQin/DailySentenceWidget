package com.dailysentence.widget;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class DailySentenceWidgetActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        DataSource data = new DataSource();
        try {
			data.getRemoteJSONdata(data.getSentenceUri());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

//        	HttpClient client = new DefaultHttpClient();
//        	StringBuilder builder = new StringBuilder();
//
//        	HttpGet myget = new HttpGet("http://bulo.yeshj.com/app/api/mobile_BuloDaySentence.ashx?userid=8874421&lang=en&pubdate=20111128&action=get");
//        	try {
//        		HttpResponse response = client.execute(myget);
//        		BufferedReader reader = new BufferedReader(new InputStreamReader(
//        		response.getEntity().getContent()));
//        		for (String s = reader.readLine(); s != null; s = reader.readLine()) {
//        			builder.append(s);
//        		}
//        		JSONObject jsonObject = new JSONObject(builder.toString());
//        		Log.v("url response", "true="+builder.toString());
//        	} catch (Exception e) {
//        		Log.v("url response", "false");
//        		e.printStackTrace();
//        	}
//   
   
        
        
    }

}