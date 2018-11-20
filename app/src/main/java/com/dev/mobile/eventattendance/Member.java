package com.dev.mobile.eventattendance;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Member {

    Member() {ID = 0; resetTime = "01/01/0001"; benefits = 0;} //DON"T USE THIS
    Member(AppDatabase db) {ID = db.dbInterface().newestMemberID() + 1; resetTime = "01/01/0001"; benefits = 0; db.dbInterface().insertMember(this);}
    Member(AppDatabase db, String name1) {ID = db.dbInterface().newestMemberID() + 1; firstName = name1;  resetTime = "01/01/0001"; benefits = 0; db.dbInterface().insertMember(this);}
    Member(AppDatabase db, String name1, String name2) {ID = db.dbInterface().newestMemberID() + 1; firstName = name1; lastName = name2;  resetTime = "01/01/0001"; benefits = 0; db.dbInterface().insertMember(this);}

    @PrimaryKey @NonNull
    public int ID;

    public String firstName;
    public String lastName;
    public int age;
    public String picture; //stores the location of the picture, not the picture itself.
    public String notes;
    public int benefits;
    public String resetTime;

    public void updateDB(AppDatabase db) {
        if (db.dbInterface().findByID(ID) != null) {
            db.dbInterface().insertMember(this);
        } else {
            db.dbInterface().updateMember(this);
        }
    }

    public boolean isPresent() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        try
        {
            Date resetDate = simpleDateFormat.parse(resetTime);
            if (new Date().after(resetDate))
                return false;
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    private static final int REFRESH_PERIOD = 7;
    public void newResetTime(AppDatabase db)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        int latestDateID = db.dbInterface().newestEntryID(ID);
        AttendanceEntry latestDate = db.dbInterface().findEntryByID(latestDateID);
        try
        {
            Calendar c = Calendar.getInstance();
            c.setTime(simpleDateFormat.parse(latestDate.dateAttended));
            c.add(Calendar.DATE, REFRESH_PERIOD);
            String resetDate = simpleDateFormat.format(c.getTime());

            resetTime = resetDate;
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
