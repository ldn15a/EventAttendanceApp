package com.dev.mobile.eventattendance;

import android.support.annotation.NonNull;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Member {

    @PrimaryKey @NonNull
    public int ID;

    public String firstName;
    public String lastName;
    public int age;
    public String picture; //stores the location of the picture, not the picture itself.
    public String notes;
    public int timesVisited;
}
