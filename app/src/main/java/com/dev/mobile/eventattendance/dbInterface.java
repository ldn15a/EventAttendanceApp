package com.dev.mobile.eventattendance;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

//NOTE: Before using this, make sure that you create the database by with this code:
//  AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class, "database-name").build();
//
//NOTE: Use these functions (listed under the @query statements) to access the database.
//      to make calls to these functions after building the database, type db.dbInterface().<function name here>;

@Dao
public interface dbInterface {

    //pulling data from the database
    @Query("SELECT * FROM Member")
    Member[] getAll(); //returns all members stored in the database as Member objects (see Member.java for how to access their fields)

    @Query("SELECT Member.ID, firstName, lastName, age, picture, notes, benefits, resetTime FROM Member Left Join AttendanceEntry ON (Member.ID = AttendanceEntry.member) where lesson = :lesson")
    Member[] getAllClassMembers(String lesson);

    @Query("SELECT * FROM Member where ID = :memberID")
    Member findByID(int memberID); //returns the member stored in the database with the given ID.

    @Query ("SELECT * FROM Member WHERE firstName+' '+lastName LIKE :name LIMIT 1")
    Member[] findByName(String name); //finds a Member by it's first and last name. BEWARE: names are not guaranteed to be unique.

    @Query ("SELECT MAX(ID) FROM Member")
    int newestMemberID(); //returns the member with the largest ID.

    @Query("SELECT * FROM attendanceEntry where member = :memberID")
    AttendanceEntry[] memberAttendance(int memberID); //returns all cases where the Member has attended class.

    @Query("SELECT * FROM attendanceEntry where ID = :id")
    AttendanceEntry findEntryByID(int id);

    @Query("SELECT COUNT(*) FROM attendanceEntry where member = :memberID")
    int countMemberAttendance(int memberID); //returns all cases where the Member has

    @Query("SELECT MAX(ID) FROM attendanceEntry")
    int newestEntryID(); //returns the attendance entry with the largest ID.

    @Query("SELECT MAX(ID) FROM attendanceEntry where member = :memberID")
    int newestEntryID(int memberID); //returns the most recent attendance entry for this member

    //adding data to the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMember(Member newMember); //Inserts a Member object's variables into the database's respective fields.

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAttendance(AttendanceEntry newAttendance);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void updateResetTime(benefitsData newBenefits); //Use this to change the date/time and switch for resetting the number of visits in the database.

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLesson(Lesson newLesson);

    //changing data in the database
    @Update
    void updateMember(Member changedMember); //Updates an already existing Member. Functions like insertMember, but only works on existing members

    //deleting data from the database
    @Delete
    void deleteMember(Member badMember);

    @Delete
    void deleteAttendance(AttendanceEntry badAttendance);

}
