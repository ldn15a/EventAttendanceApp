package com.dev.mobile.eventattendance;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class benefitsData {

    benefitsData() {resetTime = 7;} //DON"T USE THIS!
    benefitsData(AppDatabase db, int newTime) {benefitsName = 0; resetTime = newTime; db.dbInterface().updateResetTime(this);}

    @PrimaryKey
    private int benefitsName;

    public int resetTime;

    public void setBenefitsName(int newBenefitsName) {} //the db uses this.
    public int getBenefitsName() {return benefitsName;} //The db uses this.
}
