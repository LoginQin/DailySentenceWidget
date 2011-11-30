package com.dailysentence.widget;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import android.util.Log;

public class DataSource {
	private final String UPDATE_URI_PRE= "http://bulo.yeshj.com/app/api/mobile_BuloDaySentence.ashx?userid=8874421";
	//private StringBuilder  =  "http://bulo.yeshj.com/app/api/mobile_BuloDaySentence.ashx?userid=8874421&lang=en&pubdate=20111127&action=get";
	private final String DATE_PRE = "&pubdate=";
	private final String ACTION_PRE ="&action=";
	private final String LANG_PRE = "&lang=";
	private StringBuilder date ;
	private StringBuilder action_type;
	private StringBuilder lang;
	
	public DataSource() { //默认用GET方式获取当天的英语信息
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMdd");
		Date curDate = new Date(System.currentTimeMillis());//获取当前时间
		this.date = new StringBuilder(formatter.format(curDate));
		this.action_type = new StringBuilder("get"); //default type
		this.lang = new  StringBuilder("en"); //default get the english

	}
	
	public String getSentenceUri() {
		return UPDATE_URI_PRE + LANG_PRE + this.lang.toString() + DATE_PRE + this.date.toString() + ACTION_PRE + this.action_type.toString();
	}

    
    public JSONObject getRemoteJSONdata(String uri) throws Exception {
    	JSONObject jsonObject = null;
    	HttpClient client = new DefaultHttpClient();
    	StringBuilder builder = new StringBuilder();
    	HttpGet myget = new HttpGet(uri);
    	//try {
    		HttpResponse response = client.execute(myget);
    		BufferedReader reader = new BufferedReader(new InputStreamReader(
    		response.getEntity().getContent()));
    		for (String s = reader.readLine(); s != null; s = reader.readLine()) {
    			builder.append(s);
    		}
    		 jsonObject = new JSONObject(builder.toString());
//    		String sentence = jsonObject.getString("sentence");
//    		String trans = jsonObject.getString("trans");
//    		String audio = jsonObject.getString("audio");
//    		//setTitle("用户id_"+re_user_id);
//    		Log.v("url response","true="+builder);
//    		Log.v("url response", "true="+sentence);
//    		Log.v("url response", "true="+trans);
//    		Log.v("url response", "true="+audio);
   // 	} catch (Exception e) {
    //		Log.v("url response", "false");
    //		e.printStackTrace();
    //	}
    		Log.d("DataSource", "builder"+builder.toString());
		return  jsonObject;
    }
    
    public void setLangSetting(String lang) {
    	this.lang = new StringBuilder(lang);   
    }
    
    public void  setDate(String date){
    	this.date = new StringBuilder(date); 	
    }
    
    public void setActionType(String action_type) {
    	this.action_type = new StringBuilder(action_type);
    }
}
