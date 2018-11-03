package com.dev.mobile.eventattendance;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Member {

    @PrimaryKey
    public int ID;

    public String firstName;
    public String lastName;
    public int age;
    public String picture;
    public String notes;
    public int timesVisited;
}
