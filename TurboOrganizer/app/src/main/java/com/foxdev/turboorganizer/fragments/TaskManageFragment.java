package com.foxdev.turboorganizer.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.foxdev.turboorganizer.R;
import com.foxdev.turboorganizer.activities.CoreActivity;
import com.foxdev.turboorganizer.objects.Task;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

public final class TaskManageFragment extends Fragment {

    private int _year;
    private int _month;
    private int _day;

    public TaskManageFragment() {}

    public static TaskManageFragment NewInstance(@NonNull Bundle bundle) {
        TaskManageFragment taskManageFragment = new TaskManageFragment();
        taskManageFragment.setArguments(bundle);

        return taskManageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_manage, container, false);

        Bundle args = getArguments();
        assert args != null;

        if (args.getBoolean("hasData")) {
            _year = args.getInt("year");
            _month = args.getInt("month");
            _day = args.getInt("day");

            ((TextView)view.findViewById(R.id.date_picker))
                    .setText(_year + "/" + _month + "/" + _day);
        } else {
            ((TextView)view.findViewById(R.id.date_picker))
                    .setText("День не выбран");
        }


        view.findViewById(R.id.date_picker)
                .setOnClickListener(v -> {
                    final Calendar cldr = Calendar.getInstance();
                    int day = cldr.get(Calendar.DAY_OF_MONTH);
                    int month = cldr.get(Calendar.MONTH);
                    int year = cldr.get(Calendar.YEAR);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(
                            requireActivity(),
                            (datePicker, pickedYear, pickedMonth, pickedDay) -> {
                                ((TextView)v)
                                        .setText(pickedYear + "/" + pickedMonth + "/" + pickedDay);
                                _year = pickedYear;
                                _month = (byte) pickedMonth;
                                _day = pickedDay;
                            }, year, month, day);

                    datePickerDialog.show();
                });

        view.findViewById(R.id.complete_task_management)
                .setOnClickListener(v -> {

                    final String taskName = ((EditText)view.findViewById(R.id.task_name_text))
                            .getText().toString();

                    final String taskDescription = ((EditText)view.findViewById(R.id.task_description_text))
                            .getText().toString();

                    if (!taskName.isEmpty() && !taskDescription.isEmpty()) {
                        Task newTask = new Task();
                        newTask.taskName = taskName;
                        newTask.taskDescription = taskDescription;

                        newTask.taskYear = _year;
                        newTask.taskMonth = (byte) _month;
                        newTask.taskDate = _day;

                        CoreActivity activity = (CoreActivity) requireActivity();

                        if (activity.AddTask(newTask)) {
                            activity.SwitchToCalendar();
                        }
                    }
                });

        return view;
    }
}