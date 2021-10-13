package com.foxdev.turboorganizer.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.foxdev.turboorganizer.data.repos.TaskRepository;
import com.foxdev.turboorganizer.objects.Task;

import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public final class AppViewModel extends ViewModel {
    @NonNull
    private final TaskRepository _taskRepository;

    @Inject
    public AppViewModel(@NonNull TaskRepository taskRepository) {
        _taskRepository = taskRepository;
    }

    public void AddTask(@NonNull Task task) {
        _taskRepository.AddTask(task);
    }

    public void DeleteTask(@NonNull Task task) {
        _taskRepository.DeleteTask(task);
    }

    public void UpdateTask(@NonNull Task task) {
        _taskRepository.UpdateTask(task);
    }

    public void GetTasksForMonth(int year, byte month) {
        _taskRepository.GetTasksForMonth(year, month);
    }

    public void GetTasksForDay(int year, int month, int day) {
        _taskRepository.GetTasksForDay(year, month, day);
    }

    @NonNull
    public LiveData<Task[]> GetTaskLiveData() {
        return _taskRepository.GetTaskLiveData();
    }
}
