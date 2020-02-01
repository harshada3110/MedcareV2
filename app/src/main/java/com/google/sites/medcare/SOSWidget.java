package com.google.sites.medcare;

import android.Manifest;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.telephony.SmsManager;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.sites.medcare.Home.Home;

/**
 * Implementation of App Widget functionality.
 */
public class SOSWidget extends AppWidgetProvider {

    private static final String ACTION_SEND_SMS = "ACTION_SEND_SMS";

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.soswidget);

        views.setOnClickPendingIntent(R.id.widget, getPendingSelfIntent(context, ACTION_SEND_SMS));
        //views.setTextViewText(R.id.textView, "MedCare SOS");
        views.setImageViewResource(R.id.imageView, R.drawable.sos1);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);//add this line

        if (ACTION_SEND_SMS.equals(intent.getAction())){
            //your onClick action is here
            //display in short period of time
            SharedPreferences userDetails = context.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
            String nEmer = userDetails.getString("EmerContact", null);
            if (nEmer == null){
                Toast.makeText(context, "No emergency number assigned", Toast.LENGTH_SHORT).show();
            }
            else {
                SmsManager sm = SmsManager.getDefault();
                sm.sendTextMessage(nEmer, null, "Medical Emergency", null, null);
                Toast.makeText(context, "SMS Sent", Toast.LENGTH_SHORT).show();
            }
        }
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

