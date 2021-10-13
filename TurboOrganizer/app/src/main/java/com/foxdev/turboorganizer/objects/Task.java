package com.foxdev.turboorganizer.objects;

import static androidx.room.ColumnInfo.INTEGER;
import static androidx.room.ColumnInfo.TEXT;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Tasks")
public final class Task implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(typeAffinity = INTEGER)
    public int taskId;

    @ColumnInfo(typeAffinity = INTEGER)
    public int taskYear;

    @ColumnInfo(typeAffinity = INTEGER)
    public byte taskMonth;

    @ColumnInfo(typeAffinity = INTEGER)
    public int taskDate;

    @NonNull
    @ColumnInfo(typeAffinity = TEXT)
    public String taskName;

    @NonNull
    @ColumnInfo(typeAffinity = TEXT)
    public String taskDescription;

    @ColumnInfo(typeAffinity = INTEGER)
    public int taskCompleted;

    public Task() {
        taskName = "";
        taskDescription = "";
    }

    protected Task(Parcel in) {
        taskId = in.readInt();
        taskYear = in.readInt();
        taskMonth = in.readByte();
        taskDate = in.readInt();
        taskCompleted = in.readInt();
        taskName = in.readString();
        taskDescription = in.readString();
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(taskId);
        dest.writeInt(taskYear);
        dest.writeByte(taskMonth);
        dest.writeInt(taskDate);
        dest.writeInt(taskCompleted);
        dest.writeString(taskName);
        dest.writeString(taskDescription);
    }
}
