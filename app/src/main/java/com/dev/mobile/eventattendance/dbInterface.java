package com.dev.mobile.eventattendance;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

//NOTE: Before using this, make sure that you create the database by calling Room.databaseBuilder().
//NOTE: Use these functions (listed under the @query statements) to access the database.

@Dao
public interface dbInterface {

    //pulling data from the database
    @Query("SELECT * FROM Member")
    List<Member> getAll(); //returns all members stored in the database as Member objects (see Member.java for how to access their fields)

    @Query("SELECT * FROM Member where ID = :memberID")
    Member findByID(int memberID); //returns the member stored in the database with the given ID.

    @Query ("SELECT * FROM Member WHERE firstName LIKE :first AND " + "lastName LIKE :last LIMIT 1")
    Member findByName(String first, String last); //finds a Member by it's first and last name. BEWARE: names are not guaranteed to be unique.

    @Query("SELECT * FROM attendanceEntry where member = :memberID")
    List<attendanceEntry> memberAttendance(int memberID); //returns all cases where the Member has

    //adding data to the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMember(Member newMember); //Inserts a Member object's variables into the database's respective fields.

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAttendance(attendanceEntry newAttendance);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void updateResetTime(benefitsData newBenefits); //Use this to change the date/time and switch for resetting the number of visits in the database.

    //changing data in the database
    @Update
    void updateMember(Member changedMember); //Updates an already existing Member. Functions like insertMember, but only works on existing members

    //deleting data from the database
    @Delete
    void deleteMember(Member badMember);

    @Delete
    void deleteAttendance(attendanceEntry badAttendance);

}
