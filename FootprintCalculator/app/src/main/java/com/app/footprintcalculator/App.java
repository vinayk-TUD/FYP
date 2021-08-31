package com.app.footprintcalculator;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;
// channells for notifications
public class App extends Application {
    public static final String CHANNEL_ONE_ID = "channel_one_id";
    public static final String CHANNEL_TWO_ID = "channel_two_id";
    @Override
    public void onCreate() {
        super.onCreate();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channelOne = new NotificationChannel(CHANNEL_ONE_ID,"Channel One"
            , NotificationManager.IMPORTANCE_HIGH);
            channelOne.setDescription("This is channel one for video noification");
            channelOne.enableVibration(true);
            channelOne.enableLights(true);


            NotificationChannel channelTwo = new NotificationChannel(CHANNEL_TWO_ID,"Channel Two"
                    , NotificationManager.IMPORTANCE_DEFAULT);
            channelOne.setDescription("This is channel one for audio noification");

            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            List<NotificationChannel> channels = new ArrayList<>();
            channels.add(channelOne);
            channels.add(channelTwo);

            manager.createNotificationChannels(channels);
        }
    }
}
