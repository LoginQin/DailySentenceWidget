package com.dailysentence.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

public class DailyWidget extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		Intent service = new Intent(context, UpdateService.class);
		context.startService(service);
//		DataSource currData = new DataSource();
//		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日");
//		Date curDate = new Date(System.currentTimeMillis());//获取当前时间
//		StringBuilder date = new StringBuilder(formatter.format(curDate));
//		//currData.setDate("20111113");
//		JSONObject jsonObject = null;
//		try {
//			Log.d("widget_onUpdate", "URI:"+currData.getSentenceUri());
//			jsonObject = currData.getRemoteJSONdata(currData.getSentenceUri());
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			Log.d("widget_onUpdate", "error  json1");
//		}
//		for (int appWidgetId : appWidgetIds) {
//			updateAppWidget(context, appWidgetManager, appWidgetId, jsonObject, date.toString());
//			Log.d("widget_onUpdate", "OnUpdate:ID:" + appWidgetId);
//		}
	}

//	public void updateAppWidget(Context context,
//			AppWidgetManager appWidgetManager, int appWidgetId,
//			JSONObject jsonObject, String date) {
//		String sentence = null;
//		String trans = null;
//		String sentencepoint = null;
//		Button btn_restart = new Button(context);
//		RemoteViews views = new RemoteViews(context.getPackageName(),
//				R.layout.daily_appwidget_layout);
//
//		if (jsonObject != null) {
//			try {
//				 sentence = jsonObject.getString("sentence");
//				sentence = sentence.replace("][", "] [");
//				//sentence = sentence.replace("]", "");
//				
//				 trans = jsonObject.getString("trans");
//				 sentencepoint = jsonObject.getString("sentencepoint");
//				sentencepoint = sentencepoint.replace("{br}", "\n");
//			//	str = sentence + "\n" + "     " + trans + "\n\n" 
//			//			+ sentencepoint;
//			} catch (Exception e) {
//				Log.d("widget_onUpdate", "error  json2");
//				e.getStackTrace();
//			}
//		} else {
//			sentence = "     获取数据失败";
//			trans = " 请检查网络连接或者时间设置是否正确。";
//			sentencepoint = "";	
//			btn_restart.findViewById(R.id.btn_restart);
//			views.setViewVisibility(R.id.btn_restart, Button.VISIBLE);
//			
//			Intent service = new Intent(context, UpdateService.class);
//			PendingIntent sender = PendingIntent
//			.getService(context, 0, service, 0);
//			views.setOnClickPendingIntent(R.id.btn_restart, sender);
//
//
//
//		}
//		views.setTextViewText(R.id.sentence, "       "+sentence);
//		views.setTextViewText(R.id.trans, trans);
//		views.setTextViewText(R.id.sentencepoint, "句子要点：\n"+sentencepoint);
//		views.setTextViewText(R.id.date, date);
//		appWidgetManager.updateAppWidget(appWidgetId, views);
//	}
	

}
