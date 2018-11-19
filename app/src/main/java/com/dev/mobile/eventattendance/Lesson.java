package com.dev.mobile.eventattendance;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Lesson {

    Lesson() {lessonName = "";} //DON"T USE THIS!
    Lesson(AppDatabase db, String name) {lessonName = name; db.dbInterface().insertLesson(this);}

    @PrimaryKey @NonNull
    public String lessonName;
}
