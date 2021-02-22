package com.example.broadcastreceiver;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NewAppWidget extends AppWidgetProvider {
    private static final String SHARED_PREF_FILE=BuildConfig.APPLICATION_ID+".PREF";
    private static final String COUNT_KEY="count";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        SharedPreferences prefs=context.getSharedPreferences(SHARED_PREF_FILE, 0);
        int count=prefs.getInt(COUNT_KEY+appWidgetId,0);
        count++;

        String dateString= DateFormat.getTimeInstance(DateFormat.SHORT).format(new Date());
        String tanggal = new SimpleDateFormat("EEEEEE,dd/MM/YYYY", new Locale("in", "ID")).format(new Date());
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setTextViewText(R.id.appwidget_id, appWidgetId+"");
        views.setTextViewText(R.id.appwidget_update, count+"@"+dateString);
        views.setTextViewText(R.id.txt_view_date, tanggal+"");

        SharedPreferences.Editor prefEditor=prefs.edit();
        prefEditor.putInt(COUNT_KEY+appWidgetId,count);
        prefEditor.apply();

        Intent intentUpdate = new Intent(context, NewAppWidget.class);
        intentUpdate.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int [] idarray = new int[] {appWidgetId};
        intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idarray);

        PendingIntent pendingIntent=PendingIntent.getBroadcast(context,appWidgetId,intentUpdate, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.btn_update, pendingIntent);




        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}