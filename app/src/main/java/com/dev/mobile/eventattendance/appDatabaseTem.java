package com.dev.mobile.eventattendance;

import android.content.Context;
import android.os.Environment;

import java.io.File;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Member.class, AttendanceEntry.class, benefitsData.class, Lesson.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase{

    private static AppDatabase INSTANCE;

    public abstract dbInterface dbInterface();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "EventAttendanceDB")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
        }
        String path = Environment.getExternalStorageDirectory().toString()+"/Pictures";
        File directory = new File(path);
        File[] files = directory.listFiles();
        for (File file : files)
        {
            INSTANCE.dbInterface().insertMaterial(file.getName());
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
