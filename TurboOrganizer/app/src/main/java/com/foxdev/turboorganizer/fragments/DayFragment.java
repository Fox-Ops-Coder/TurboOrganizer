package com.foxdev.turboorganizer.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foxdev.turboorganizer.R;
import com.foxdev.turboorganizer.activities.CoreActivity;
import com.foxdev.turboorganizer.adapters.TaskAdapter;
import com.foxdev.turboorganizer.objects.Task;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public final class DayFragment extends Fragment {

    public DayFragment() {}

    public static DayFragment NewInstance(@NonNull Bundle bundle) {
        DayFragment dayFragment = new DayFragment();
        dayFragment.setArguments(bundle);

        return dayFragment;
    }

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                   Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        assert bundle != null;

        View view = inflater.inflate(R.layout.fragment_day, container, false);

        if (bundle.getBoolean("hasData")) {
            Task[] task = (Task[])bundle.getParcelableArray("data");



            final TaskAdapter adapter = new TaskAdapter();
            adapter.SetTaskListener(t -> {

                if (((CoreActivity)requireActivity()).DeleteTask(t)) {
                    adapter.DeleteTask(t);

                    if (adapter.getItemCount() == 0) {
                        view.findViewById(R.id.day_tasks).setVisibility(View.GONE);
                        view.findViewById(R.id.no_tasks).setVisibility(View.VISIBLE);
                    }
                }

            }, t -> {
                if (((CoreActivity) requireActivity()).ChangeCompletedState(t)) {
                    adapter.UpdateTasks(adapter.FindTask(t));
                }
            });

            adapter.SetTasks(task);
            ((RecyclerView)view.findViewById(R.id.day_tasks)).setAdapter(adapter);
        } else {
            view.findViewById(R.id.day_tasks).setVisibility(View.GONE);
            view.findViewById(R.id.no_tasks).setVisibility(View.VISIBLE);
        }

        int year = bundle.getInt("year");
        int month = bundle.getInt("month");
        int day = bundle.getInt("day");

        ((TextView)view.findViewById(R.id.current_day)).setText(year + "/" + month + "/" + day);

        view.findViewById(R.id.add_task_button).setOnClickListener(v -> {
            ((CoreActivity)requireActivity()).GoToAddTask(year, month, day);
        });

        // Inflate the layout for this fragment
        return view;
    }
}