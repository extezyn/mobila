package com.example.timerforlesson;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    TimePicker timePicker;
    Button btnSetAlarm;
    TextView tvCountdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timePicker = findViewById(R.id.timePicker);
        btnSetAlarm = findViewById(R.id.btnSetAlarm);
        tvCountdown = findViewById(R.id.tvCountdown);

        btnSetAlarm.setOnClickListener(view -> {
            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            if (calendar.before(Calendar.getInstance())) {
                calendar.add(Calendar.DATE, 1);
            }

            long alarmTime = calendar.getTimeInMillis();
            long currentTime = System.currentTimeMillis();
            long timeDifference = alarmTime - currentTime;

            Intent intent = new Intent(MainActivity.this, Alarm.class);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime,
                    PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE));

            Toast.makeText(MainActivity.this, "Будильник запущен!", Toast.LENGTH_SHORT).show();

            updateCountdown(timeDifference);
        });
    }

    private void updateCountdown(long timeDifference) {
        long hours = timeDifference / (1000 * 60 * 60);
        long minutes = (timeDifference % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (timeDifference % (1000 * 60)) / 1000;

        tvCountdown.setText(String.format("Осталось времени: %02d:%02d:%02d", hours, minutes, seconds));
    }
}
