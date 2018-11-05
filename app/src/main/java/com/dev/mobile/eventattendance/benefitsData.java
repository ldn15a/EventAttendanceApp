package com.dev.mobile.eventattendance;

import android.support.annotation.NonNull;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class benefitsData {

    benefitsData() {benefitsName = 0;}

    @PrimaryKey
    private int benefitsName;

    public int doesReset;
    public String resetTime;

    public void setBenefitsName(int newBenefitsName) {}
    public int getBenefitsName() {return benefitsName;} //The db uses this.
}
