package com.dev.mobile.eventattendance;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Material {

    @PrimaryKey @NonNull
    public String material;
}
