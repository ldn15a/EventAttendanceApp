package com.dev.mobile.eventattendance;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Member.class, AttendanceEntry.class, benefitsData.class, Lesson.class, Material.class}, version = 3, exportSchema = false)
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
        final AssetManager assets = context.getAssets();
        String[] names = new String[1];
        try
        {
            names = assets.list( "" );
        }
        catch (IOException e)
        {
            //fill in something.
        }

        for (String name : names)
        {
            Material newMaterial = new Material();
            newMaterial.material = name;
            INSTANCE.dbInterface().insertMaterial(newMaterial);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
