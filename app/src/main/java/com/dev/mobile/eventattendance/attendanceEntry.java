package com.dev.mobile.eventattendance;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import android.support.annotation.NonNull;

@Entity(foreignKeys = @ForeignKey(entity = Member.class, parentColumns = "ID", childColumns = "member"))
public class attendanceEntry {

    @PrimaryKey
    public int ID;

    public int member;

    public String dateAttended;
}