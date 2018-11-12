package com.dev.mobile.eventattendance;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class AttendanceActivity extends AppCompatActivity {

    private class Member {
        String name;
        String date;

        public Member(String _name, String _date) {
            name = _name;
            date = _date;
        }
    }


    private Member member1 = new Member("James Prather", "2018-10-23");
    private Member[] members = {member1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        // Get all the member data from sqlite
        // temporarily we're storying that in the members variable instead

        // TODO add a search bar
        // TODO when the bar is searched, filter members that match the name

        // Create a new name button and toggle button pair for each user
        // also create a new page for the member which shows their history

    }
}
