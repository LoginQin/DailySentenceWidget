package com.dailysentence.widget;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONObject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Button;
import android.widget.RemoteViews;

public class UpdateService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		Log.d("Service", "Service start");
		AppWidgetManager awm = AppWidgetManager.getInstance(this);
		int[] ids = awm.getAppWidgetIds(new ComponentName(this,
				DailyWidget.class));

		// requestUpdate(manager.getAppWidgetIds(new ComponentName(this,
		// MedAppWidget.class)));
		// AppWidgetManager awm = AppWidgetManager.getInstance(this);
		// RemoteViews views = new RemoteViews(this.getPackageName(),
		// R.layout.daily_appwidget_layout);
		if (ids.length >= 1) { //判断widget id数目如果为0，关闭定时器
			DataSource currData = new DataSource();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
			StringBuilder date = new StringBuilder(formatter.format(curDate));
			// currData.setDate("20111113");
			JSONObject jsonObject = null;
			try {
				Log.d("widget_onUpdate", "URI:" + currData.getSentenceUri());
				jsonObject = currData.getRemoteJSONdata(currData
						.getSentenceUri());

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.d("widget_onUpdate", "error  json1");
			}

			updateAppWidget(this, jsonObject, date.toString());
		} else {
			Intent service = new Intent(this, UpdateService.class);
			PendingIntent sender = PendingIntent
					.getService(this, 0, service, 0);
			AlarmManager am = (AlarmManager) this
					.getSystemService(Context.ALARM_SERVICE);
			am.cancel(sender);
			stopSelf();
			Log.d("SERVICE", "SERVICE stop");

		}

	}

	public void setNextStartAlarm(Context context) {
		Intent service = new Intent(this, UpdateService.class);
		PendingIntent sender = PendingIntent.getService(context, 0, service, 0);

		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());
		c.add(Calendar.DAY_OF_MONTH, 1);// 第二天6点启动服务更新
		c.set(Calendar.HOUR_OF_DAY, 6);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);

		am.setRepeating(AlarmManager.RTC, c.getTimeInMillis(),
				AlarmManager.INTERVAL_DAY, sender);

		SimpleDateFormat formatter2 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Log.d("NEXTALARM", formatter2.format(c.getTime()));
	}

	public void updateAppWidget(Context context, JSONObject jsonObject,
			String date) {
		AppWidgetManager awm = AppWidgetManager.getInstance(context);
		String sentence = null;
		boolean success = true;
		String trans = null;
		String sentencepoint = null;
		SimpleDateFormat oldformatter = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat newformatter = new SimpleDateFormat("yyyy年MM月dd日");
		Date updatetime = null;
		String str_date = null;
		Button btn_restart = new Button(context);
		RemoteViews views = new RemoteViews(context.getPackageName(),
				R.layout.daily_appwidget_layout);

		if (jsonObject != null) {
			try {
				sentence = jsonObject.getString("sentence");
				sentence = sentence.replace("][", "] [");
				// sentence = sentence.replace("]", "");

				trans = jsonObject.getString("trans");
				sentencepoint = jsonObject.getString("sentencepoint");
				sentencepoint = sentencepoint.replace("{br}", "\n");
				updatetime = oldformatter.parse(date.toString());
				str_date = newformatter.format(updatetime);

			} catch (Exception e) {
				Log.d("widget_onUpdate", "error  json2");
				e.getStackTrace();
			}
		} else {
			success = false;
			sentence = "     获取数据失败";
			trans = " 请检查网络连接或者时间设置是否正确。";
			sentencepoint = "数据源自沪江网 BY ChineseTiger";
			str_date = "\n http://weibo.com/chinesetiger";
			btn_restart.findViewById(R.id.btn_restart);

			views.setViewVisibility(R.id.btn_restart, Button.VISIBLE);

			Intent service = new Intent(context, UpdateService.class);
			PendingIntent sender = PendingIntent.getService(context, 0,
					service, 0);
			views.setOnClickPendingIntent(R.id.btn_restart, sender);

		}

		views.setTextViewText(R.id.sentence, "       " + sentence);
		views.setTextViewText(R.id.trans, trans);
		views.setTextViewText(R.id.sentencepoint, "句子要点：\n" + sentencepoint);
		views.setTextViewText(R.id.date, (CharSequence) "每日英语@" + str_date);
		if (success)
			views.setViewVisibility(R.id.btn_restart, Button.GONE);
		awm.updateAppWidget(new ComponentName(context, DailyWidget.class),
				views);
		setNextStartAlarm(context);
		stopSelf();
	}

}
