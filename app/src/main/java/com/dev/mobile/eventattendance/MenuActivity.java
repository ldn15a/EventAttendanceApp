package com.dev.mobile.eventattendance;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class MenuActivity extends AppCompatActivity {

    private static final int REFRESH_PERIOD = 7;
    public AppDatabase db;

    public void newResetTime(Member member)
    {
        int latestDateID = db.dbInterface().newestEntryID(member.ID);
        AttendanceEntry latestDate = db.dbInterface().findEntryByID(latestDateID);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        try
        {
            Calendar c = Calendar.getInstance();
            c.setTime(simpleDateFormat.parse(latestDate.dateAttended));
            c.add(Calendar.DATE, REFRESH_PERIOD);
            String resetDate = simpleDateFormat.format(c.getTime());

            member.resetTime = resetDate;
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void createInitialMembers()
    {
        new Member(db, "Karen", "Cukrowski");
        new Member(db, "James", "Prather");
        Member Fillion = new Member(db, "Nathan", "Fillian");
        Fillion.picture = "nathan_fillion";
        new Member(db, "Rich", "Tanner");
        new Member(db, "Jesse", "James");
        new Member(db, "Scarlet", "Johansen");
        new Member(db, "Wonder", "Woman");
        new Member(db, "Venom");
        new Member(db, "Katie", "Perry");
        new Member(db, "Haley", "Kiyoko");
        new Member(db, "Jeff", "Goldbloom");
        new Member(db, "Chris", "Pratt");
        new Member(db, "Chris", "Hemsworth");
        new Member(db, "Chris", "Evans");
        new Member(db, "Christopher", "Rabbit");
        new Member(db, "Bugs", "Bunny");
        new Member(db, "Steven", "Rogers");
    }

    private void createInitialAttendance(Member[] members, String lesson)
    {
        Random rand = new Random();
        for (Member member : members)
        {
            int numberOfEntries = rand.nextInt(2) + 1;
            for (int count = 0; count < numberOfEntries; count++)
            {
                int month = rand.nextInt(11) + 1;
                int day = rand.nextInt(30) + 1;
                int year = rand.nextInt(1) + 2017;
                if (year == 2018) {
                    if (month >= 11 && day > 19) {
                        day = 19;
                        if (month == 12) {
                            month = 11;
                        }
                    }
                }
                String date = month + "/" + day + "/" + year;

                new AttendanceEntry(db, member.ID, lesson, date);
                member.benefits++;
                newResetTime(member);
                member.updateDB(db);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button attendanceButton = (Button) findViewById(R.id.attendanceButton);
        attendanceButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, AttendanceActivity.class));
            }
        });

        Button materialsButton = (Button) findViewById(R.id.materialsButton);
        materialsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, MaterialMenu.class));
            }
        });

        Button newMemberButton = (Button) findViewById(R.id.addStudentButton);
        newMemberButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, newMember.class));
            }
        });

        db = AppDatabase.getAppDatabase(getApplicationContext());
        Member[] members = db.dbInterface().getAll();
        if (members.length == 0) {
            createInitialMembers(); //creates dummy Members to fill the database;
            members = db.dbInterface().getAll();
            createInitialAttendance(members, "empty"); //creates dummy attendance entries to fill the database;
        }
    }
}
