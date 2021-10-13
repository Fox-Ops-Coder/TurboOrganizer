package com.foxdev.turboorganizer.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.foxdev.turboorganizer.R;
import com.foxdev.turboorganizer.fragments.CalendarFragment;
import com.foxdev.turboorganizer.fragments.DayFragment;
import com.foxdev.turboorganizer.fragments.TaskManageFragment;
import com.foxdev.turboorganizer.objects.Task;
import com.foxdev.turboorganizer.viewmodel.AppViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public final class CoreActivity extends AppCompatActivity {

    private AppViewModel _appViewModel;

    private int _year;
    private int _month;
    private int _day;

    private boolean _isTranzition;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_core);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, new CalendarFragment())
                .commit();

        _appViewModel = new ViewModelProvider(this)
                .get(AppViewModel.class);

        _appViewModel.GetTaskLiveData().observe(this, tasks -> {
            Bundle bundle = new Bundle();

            if (tasks == null || tasks.length == 0) {
                bundle.putBoolean("hasData", false);
            } else {
                bundle.putBoolean("hasData", true);
                bundle.putParcelableArray("data", tasks);
            }

            bundle.putInt("year", _year);
            bundle.putInt("month", _month);
            bundle.putInt("day", _day);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, DayFragment.NewInstance(bundle))
                    .commit();

            _isTranzition = true;
            ((BottomNavigationView) findViewById(R.id.bottom_navigation))
                    .setSelectedItemId(R.id.day_menu_nav);
            _isTranzition = false;
        });

        ((BottomNavigationView)findViewById(R.id.bottom_navigation)).setOnItemSelectedListener(v -> {

            if (!_isTranzition) {
                switch (v.getItemId()) {
                    case R.id.day_menu_nav:
                        _appViewModel.GetTasksForDay(_year, _month, _day);
                        break;

                    case R.id.calendar_menu_nav:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new CalendarFragment())
                                .commit();
                        break;

                    case R.id.add_task_nav:
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("hasData", false);

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, TaskManageFragment.NewInstance(bundle))
                                .commit();
                        break;
                }
            }

            return true;
        });
    }

    public void SwitchToDay(int year, int month, int day) {
        _year = year;
        _month = month;
        _day = day;

        _appViewModel.GetTasksForDay(year, month, day);
    }

    public void SwitchToCalendar() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new CalendarFragment())
                .commit();

        _isTranzition = true;
        ((BottomNavigationView) findViewById(R.id.bottom_navigation))
                .setSelectedItemId(R.id.calendar_menu_nav);
        _isTranzition = false;
    }

    public void GoToAddTask(int year, int month, int day) {
        Bundle bundle = new Bundle();

        bundle.putBoolean("hasData", true);
        bundle.putInt("year", year);
        bundle.putInt("month", month);
        bundle.putInt("day", day);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, TaskManageFragment.NewInstance(bundle))
                .commit();

        _isTranzition = true;
        ((BottomNavigationView) findViewById(R.id.bottom_navigation))
                .setSelectedItemId(R.id.add_task_nav);
        _isTranzition = false;

    }

    public boolean AddTask(@NonNull Task task) {
        try {
            _appViewModel.AddTask(task);
            return  true;

        } catch (Exception ignored) {

            return false;
        }
    }

    public boolean ChangeCompletedState(@NonNull Task task) {
        try {
            task.taskCompleted = task.taskCompleted == 0 ? 1 : 0;
            _appViewModel.UpdateTask(task);

            return true;

        } catch (Exception ignored) {
            return false;
        }
    }

    public boolean DeleteTask(@NonNull Task task) {
        try {
            _appViewModel.DeleteTask(task);

            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

}