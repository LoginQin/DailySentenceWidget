package com.dailysentence.widget;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;

public class DailyWidget extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		DataSource currData = new DataSource();
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日");
		Date curDate = new Date(System.currentTimeMillis());//获取当前时间
		StringBuilder date = new StringBuilder(formatter.format(curDate));
		currData.setDate("20111113");
		JSONObject jsonObject = null;
		try {
			Log.d("widget_onUpdate", "URI:"+currData.getSentenceUri());
			jsonObject = currData.getRemoteJSONdata(currData.getSentenceUri());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d("widget_onUpdate", "error  json1");
		}
		for (int appWidgetId : appWidgetIds) {
			updateAppWidget(context, appWidgetManager, appWidgetId, jsonObject, date.toString());
			Log.d("widget_onUpdate", "OnUpdate:ID:" + appWidgetId);
		}
	}

	public void updateAppWidget(Context context,
			AppWidgetManager appWidgetManager, int appWidgetId,
			JSONObject jsonObject, String date) {
		String sentence = null;
		String trans = null;
		String sentencepoint = null;
		RemoteViews views = new RemoteViews(context.getPackageName(),
				R.layout.daily_appwidget_layout);

		if (jsonObject != null) {
			try {
				 sentence = jsonObject.getString("sentence");
				sentence = sentence.replace("[", "");
				sentence = sentence.replace("]", "");
				
				 trans = jsonObject.getString("trans");
				 sentencepoint = jsonObject.getString("sentencepoint");
				sentencepoint = sentencepoint.replace("{br}", "\n");
			//	str = sentence + "\n" + "     " + trans + "\n\n" 
			//			+ sentencepoint;
			} catch (Exception e) {
				Log.d("widget_onUpdate", "error  json2");
				e.getStackTrace();
			}
		} else {
			trans = "数据连接失败，请检查网络链接！";

		}
		views.setTextViewText(R.id.sentence, "     "+sentence);
		views.setTextViewText(R.id.trans, trans);
		views.setTextViewText(R.id.sentencepoint, "Sentence Point：\n"+sentencepoint);
		views.setTextViewText(R.id.date, date);
		appWidgetManager.updateAppWidget(appWidgetId, views);
	}

}
