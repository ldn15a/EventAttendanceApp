package com.dev.mobile.eventattendance;

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

    public void updateDB(AppDatabase db) {db.dbInterface().insertMember(this);}
}
