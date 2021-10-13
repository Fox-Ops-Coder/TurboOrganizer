package com.foxdev.turboorganizer.data.repos;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.foxdev.turboorganizer.data.sql.TaskDao;
import com.foxdev.turboorganizer.data.sql.TaskDatabase;
import com.foxdev.turboorganizer.objects.Task;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public final class TaskRepository {
    @NonNull
    private final TaskDao _taskDao;
    @NonNull
    private final MutableLiveData<Task[]> _tasks;

    @Inject
    public TaskRepository(@NonNull TaskDatabase taskDao) {
        _taskDao = taskDao.GetDatabase();
        _tasks = new MutableLiveData<>();
    }

    public void AddTask(@NonNull Task task) {
        Executors
                .newSingleThreadExecutor()
                .execute(() -> _taskDao.InsertTask(task));
    }

    public void DeleteTask(@NonNull Task task) {
        Executors
                .newSingleThreadExecutor()
                .execute(() -> _taskDao.DeleteTask(task));
    }

    public void UpdateTask(@NonNull Task task) {
        Executors
                .newSingleThreadExecutor()
                .execute(() -> _taskDao.ChangeTask(task));
    }

    public void GetTasksForMonth(int year, byte month) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> _tasks.postValue(_taskDao.GetMonthTasks(year, month)));
    }

    public void GetTasksForDay(int year, int month, int day) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> _tasks.postValue(_taskDao.GetDayTask(year, month, day)));
    }

    @NonNull
    public LiveData<Task[]> GetTaskLiveData() {
        return _tasks;
    }
}
