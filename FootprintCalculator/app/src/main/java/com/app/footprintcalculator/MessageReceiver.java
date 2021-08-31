package com.app.footprintcalculator;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.core.app.NotificationCompat;

import com.app.footprintcalculator.App;
import com.app.footprintcalculator.R;

import static android.content.Context.NOTIFICATION_SERVICE;
// Notification channels for opt ins
public class MessageReceiver extends BroadcastReceiver {
    public static final String CHANNEL_ONE_ID = "channel_one_id";
    public static final String CHANNEL_TWO_ID = "channel_two_id";

    Context ctx;

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("message");
        String title = intent.getStringExtra("title");
        ctx = context;
        callNotification(context, message, title);

    }

    private void callNotification(Context context, String message, String title) {
        Bitmap bitmap = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.washing_machine);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context.getApplicationContext() , App.CHANNEL_ONE_ID);
        builder.setSmallIcon(R.drawable.sad_sun).setContentTitle(title)
                //.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap).bigLargeIcon(null))
                //.setLargeIcon(bitmap)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentText(message)
                .setAutoCancel(true )
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();
        builder.setAutoCancel(true);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1,builder.build());
    }
}
