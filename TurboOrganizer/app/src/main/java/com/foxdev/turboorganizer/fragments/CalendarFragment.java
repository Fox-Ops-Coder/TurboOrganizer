package com.foxdev.turboorganizer.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.foxdev.turboorganizer.R;
import com.foxdev.turboorganizer.activities.CoreActivity;

import java.util.Calendar;
import java.util.Date;

public final class CalendarFragment extends Fragment {

    public CalendarFragment() {}

    public static CalendarFragment NewInstance(int year, int month, int day) {
        Bundle bundle = new Bundle();

        bundle.putInt("Year", year);
        bundle.putInt("Month", month);
        bundle.putInt("Day", day);

        CalendarFragment calendarFragment = new CalendarFragment();
        calendarFragment.setArguments(bundle);

        return calendarFragment;
    }

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        assert bundle != null;

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        int savedYear = bundle.getInt("Year");
        int savedMonth = bundle.getInt("Month");
        int savedDay = bundle.getInt("Day");

        Calendar calendarInstance = Calendar.getInstance();
        calendarInstance.set(savedYear, savedMonth, savedDay);

        CalendarView calendar = view.findViewById(R.id.calendar);

        calendar.setDate(calendarInstance.getTime().getTime());

        calendar.setOnDateChangeListener((v, year, month, dayOfMonth) -> {
                    ((CoreActivity)requireActivity()).SwitchToDay(year, month, dayOfMonth);
                });

        return view;
    }
}