package com.if5b.myalarm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextView tvOnceTime, tvOnceDate, tvRepeatingTime;
    private ImageButton ibOnceTime, ibOnceDate, ibRepeatingTime;
    private EditText etOnceMessage, etRepeatingMessage;
    private Button btnSetOnceAlarm, btnSetRepeatingAlarm, btnCancelRepeatingAlarm;

    private AlarmReceiver alarmReceiver;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private int mHourRepeat, mMinuteRepeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        tvOnceTime = findViewById(R.id.tvOnceTime);
        tvOnceDate = findViewById(R.id.tvOnceDate);
        tvRepeatingTime = findViewById(R.id.tvRepeatTime);
        ibOnceDate = findViewById(R.id.ibOnceDate);
        ibOnceTime = findViewById(R.id.ibOnceTime);
        ibRepeatingTime = findViewById(R.id.ibRepeatTime);
        etOnceMessage = findViewById(R.id.etOnce);
        etRepeatingMessage = findViewById(R.id.etRepeat);
        btnSetOnceAlarm = findViewById(R.id.btnOnce);
        btnSetRepeatingAlarm = findViewById(R.id.btnSetRepeat);
        btnCancelRepeatingAlarm = findViewById(R.id.btnCancelRepeat);

        alarmReceiver = new AlarmReceiver();
        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);
        mHourRepeat = mHour;
        mMinuteRepeat = mMinute;

        ibOnceDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        tvOnceDate.setText(String.format("%04d-%02d-%02d", i, i1 + 1, i2));
                        mYear = i;
                        mMonth = i1;
                        mDay = i2;
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        ibOnceTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        tvOnceTime.setText(String.format("%02d:%02d", i, i1));
                        mHour = i;
                        mMinute = i1;
                    }
                }, mHour, mMinute, true);
                timePickerDialog.show();
            }
        });

        ibRepeatingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        tvRepeatingTime.setText(String.format("%02d:%02d", i, i1));
                        mHourRepeat = i;
                        mMinuteRepeat = i1;
                    }
                }, mHourRepeat, mMinuteRepeat, true);
                timePickerDialog.show();
            }
        });

        btnSetOnceAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tvOnceDate.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(MainActivity.this, "Date is empty", Toast.LENGTH_SHORT).show();
                }
                else if(tvOnceTime.toString().equalsIgnoreCase("")) {
                    Toast.makeText(MainActivity.this, "Time is empty", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(etOnceMessage.getText().toString())) {
                    etOnceMessage.setError("Message can't be empty");
                }
                else {
                    alarmReceiver.setOneTimeAlarm(MainActivity.this, AlarmReceiver.TYPE_ONE_TIME, tvOnceDate.getText().toString(),
                            tvOnceTime.getText().toString(), etOnceMessage.getText().toString());
                }
            }
        });

        btnSetRepeatingAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tvRepeatingTime.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(MainActivity.this, "Time is empty", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(etRepeatingMessage.getText().toString())) {
                    etRepeatingMessage.setError("Message can't be empty");
                }
                else {
                    alarmReceiver.setRepeatingAlarm(MainActivity.this, AlarmReceiver.TYPE_REPEATING, tvRepeatingTime.getText().toString(),
                            etRepeatingMessage.getText().toString());
                }
            }
        });

        btnCancelRepeatingAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(alarmReceiver.isAlarmSet(MainActivity.this, AlarmReceiver.TYPE_REPEATING)) {
                    tvRepeatingTime.setText("");
                    etRepeatingMessage.setText("");
                    alarmReceiver.cancelAlarm(MainActivity.this, AlarmReceiver.TYPE_REPEATING);
                }
            }
        });
    }
}