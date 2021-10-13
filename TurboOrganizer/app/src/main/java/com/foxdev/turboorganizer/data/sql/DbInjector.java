package com.foxdev.turboorganizer.data.sql;

import android.app.Application;

import androidx.room.Room;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(ViewModelComponent.class)
public final class DbInjector {

    @Provides
    public TaskDatabase GetDataBase(Application app) {
        return Room
                .databaseBuilder(app.getApplicationContext(),
                        TaskDatabase.class,
                        "TaskDb")
                .build();
    }
}
