package com.foxdev.turboorganizer.data.sql;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.foxdev.turboorganizer.objects.Task;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;

@Database(entities = { Task.class }, version = 1)
public abstract class TaskDatabase extends RoomDatabase {
    public abstract TaskDao GetDatabase();
}
