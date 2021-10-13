package com.foxdev.turboorganizer.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.foxdev.turboorganizer.R;
import com.foxdev.turboorganizer.activities.CoreActivity;

public final class CalendarFragment extends Fragment {

    public CalendarFragment() {}

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        ((CalendarView)(view.findViewById(R.id.calendar)))
                .setOnDateChangeListener((v, year, month, dayOfMonth) -> {
                    ((CoreActivity)requireActivity()).SwitchToDay(year, month, dayOfMonth);
                });

        return view;
    }
}