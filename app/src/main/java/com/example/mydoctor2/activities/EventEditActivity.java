package com.example.mydoctor2.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mydoctor2.R;
import com.example.mydoctor2.ui.calendar.CalendarUtils;
import com.example.mydoctor2.ui.calendar.Event;

public class EventEditActivity extends AppCompatActivity {
    private EditText eventNameET;
    private TextView eventDateTV;
    private TimePicker timePicker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();
        timePicker.setIs24HourView(false);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                // display a toast with changed values of time picker
                Toast.makeText(getApplicationContext(), hourOfDay + "  " + minute, Toast.LENGTH_SHORT).show();
            }
        });
        eventDateTV.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
    }

    private void initWidgets() {
        eventNameET = findViewById(R.id.eventNameET);
        eventDateTV = findViewById(R.id.eventDateTV);
        timePicker = findViewById(R.id.time);
    }

    public void saveEventAction(View view) {
        String eventName = eventNameET.getText().toString();
        String eventTime = timePicker.getHour() + ":" + timePicker.getMinute();
        Event newEvent = new Event(eventName, CalendarUtils.selectedDate, eventTime);
        Event.eventsList.add(newEvent);
        finish();
    }
}
