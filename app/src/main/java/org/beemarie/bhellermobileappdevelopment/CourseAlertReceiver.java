package org.beemarie.bhellermobileappdevelopment;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import org.beemarie.bhellermobileappdevelopment.view.CourseListActivity;

public class CourseAlertReceiver extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification_id";
    public static String NOTIFICATION = "notification";


    @Override
    public void onReceive(Context context, Intent intent) {
        createNotification(context, "Course is starting", "Your course is starting", "Alert");
    }

    public void createNotification(Context context, String msgTitle, String msgText, String msgAlert){
        PendingIntent notificationIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, CourseListActivity.class), 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(msgTitle)
                .setTicker(msgAlert)
                .setContentText(msgText);

        mBuilder.setContentIntent(notificationIntent);

        mBuilder.setDefaults(NotificationCompat.DEFAULT_VIBRATE);

        mBuilder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1, mBuilder.build());


    }
}
