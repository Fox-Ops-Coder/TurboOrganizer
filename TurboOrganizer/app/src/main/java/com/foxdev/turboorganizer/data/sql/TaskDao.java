package com.foxdev.turboorganizer.data.sql;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.foxdev.turboorganizer.objects.Task;

import dagger.Provides;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM Tasks WHERE taskYear = :year AND taskMonth = :month")
    Task[] GetMonthTasks(int year, byte month);

    @Query("SELECT * FROM Tasks WHERE taskYear = :year AND taskDate = :day AND taskMonth = :month")
    Task[] GetDayTask(int year, int month, int day);

    @Insert
    void InsertTask(@NonNull Task task);

    @Update
    void ChangeTask(@NonNull Task task);

    @Delete
    void DeleteTask(@NonNull Task task);
}
