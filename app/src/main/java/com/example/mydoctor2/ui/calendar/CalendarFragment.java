package com.example.mydoctor2.ui.calendar;

import static com.example.mydoctor2.ui.calendar.CalendarUtils.daysInWeekArray;
import static com.example.mydoctor2.ui.calendar.CalendarUtils.monthYearFromDate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mydoctor2.R;
import com.example.mydoctor2.databinding.ActivityCalendarBinding;

import java.time.LocalDate;
import java.util.ArrayList;


public class CalendarFragment extends Fragment implements CalendarAdapter.OnItemListener {

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;

    private ActivityCalendarBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_calendar, container, false);
        calendarRecyclerView = root.findViewById(R.id.calendarRecyclerView);
        monthYearText = root.findViewById(R.id.monthYearTV);
        eventListView = root.findViewById(R.id.eventListView);
        setWeekView();
        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_week_view);

    }

    private void setWeekView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
//        setEventAdpater();
    }


    public void previousWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    public void nextWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        CalendarUtils.selectedDate = date;
        setWeekView();
    }

    @Override
    public void onResume()
    {
        super.onResume();
//        setEventAdpater();
    }
//    private FragmentSlideshowBinding binding;
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        CalendarViewModel slideshowViewModel =
//                new ViewModelProvider(this).get(CalendarViewModel.class);
//
//        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
//
////        final TextView textView = binding.textSlideshow;
////        slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
//        return root;
//    }
//
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}