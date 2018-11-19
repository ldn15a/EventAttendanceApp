package com.dev.mobile.eventattendance;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity
public class AttendanceEntry {

    AttendanceEntry() {} //DON"T USE THIS!
    AttendanceEntry(AppDatabase db) {ID = db.dbInterface().newestEntryID() + 1; db.dbInterface().insertAttendance(this);}
    AttendanceEntry(AppDatabase db, int memberID, String lessonName, String date) {
        ID = db.dbInterface().newestEntryID() + 1;
        member = memberID;
        lesson = lessonName;
        dateAttended = date;
        db.dbInterface().insertAttendance(this);
    }

    AttendanceEntry(AppDatabase db, int memberID, String lessonName) //creating a date to match the current time on attendance entry.
    {
        ID = db.dbInterface().newestEntryID() + 1;
        member = memberID;
        lesson = lessonName;
        db.dbInterface().insertAttendance(this);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Calendar calendar = Calendar.getInstance();
        dateAttended = simpleDateFormat.format(calendar.getTime());
    }

    @PrimaryKey
    public int ID;

    public int member;
    public String lesson;

    public String dateAttended;

    public void updateDB(AppDatabase db) {db.dbInterface().insertAttendance(this);}
}