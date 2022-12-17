package com.example.mydoctor2.ui.calendar;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mydoctor2.R;

public class CalendarActivity extends AppCompatActivity {

    CalendarView calendar;
    TextView date_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendar = (CalendarView) findViewById(R.id.calendar);
        date_view = (TextView) findViewById(R.id.date_view);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            // In this method we will get the value of DAYS, MONTHS, YEARS
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // Store the value of date with format in String type Variable
                // Add 1 in month because month index is start with 0
                String Date = dayOfMonth + "-" + (month + 1) + "-" + year;
                date_view.setText(Date);
            }
        });
    }
}
