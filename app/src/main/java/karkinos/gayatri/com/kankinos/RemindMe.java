package karkinos.gayatri.com.kankinos;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;

import java.util.Calendar;

public class RemindMe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind_me);

        final TimePicker timePicker1;
         final EditText text;
         TextView time;
         Calendar calendar;
         String format = "";
         Button set;
        final String reminderMessage;
        timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
        calendar = Calendar.getInstance();
        text= (EditText)findViewById(R.id.reminder) ;
        set = (Button)findViewById(R.id.set_button);
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int min = calendar.get(Calendar.MINUTE);

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i("@@@@@@@@@Hour",""+hour);
//                Log.i("@@@@@@@@@Min",""+min);

                int currentHour = timePicker1.getCurrentHour();
                int currentMin = timePicker1.getCurrentMinute();
                Log.i("@@@@@@@@@Hour",""+currentHour);
                Log.i("@@@@@@@@@Min",""+currentMin);
                int diffHour = currentHour-hour;
                int diffMin = currentMin-min;

                diffHour = diffHour*60*60;
                diffMin = diffMin*60;
                int timeAlarm = diffHour+diffMin;
                Log.i("@@@@@@@@@Hour",""+diffHour);
                Log.i("@@@@@@@@@Min",""+diffMin);

                String message=text.getText().toString();
                GlobalData g = GlobalData.getInstance();
                g.setData(message);
                startAlert(timeAlarm,message);
            }
        });

    }

    public void startAlert(int alarm, String message){
        // EditText text = findViewById(R.id.editText1);
        int i = alarm;//Integer.parseInt(text.getText().toString());
        Intent intent = new Intent(this, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(), 234324243, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                + (i * 1000), pendingIntent);
        Toast.makeText(this, "Alarm set in " + i + " seconds",Toast.LENGTH_LONG).show();
    }
}
