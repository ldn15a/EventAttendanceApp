package com.dev.mobile.eventattendance;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Member.class, attendanceEntry.class, benefitsData.class}, version = 1, exportSchema = false)
public abstract class appDatabase extends RoomDatabase{

    public abstract dbInterface dbInterface();

}
