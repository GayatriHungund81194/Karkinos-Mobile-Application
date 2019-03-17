package karkinos.gayatri.com.kankinos;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

public class NotificationReceiver extends BroadcastReceiver{

    MediaPlayer mp;
    @Override
    public void onReceive(Context context, Intent intent) {


        GlobalData g = GlobalData.getInstance();
        String message=g.getData();
        mp=MediaPlayer.create(context, Settings.System.DEFAULT_ALARM_ALERT_URI);
        mp.start();
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
