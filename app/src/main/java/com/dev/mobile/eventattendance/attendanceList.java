package com.dev.mobile.eventattendance;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(primaryKeys = {"member, dateAttended"}, foreignKeys = @ForeignKey(entity = Member.class, parentColumns = "id", childColumns = "member"))
public class attendanceList {

    public int member;
    public String dateAttended;
}
