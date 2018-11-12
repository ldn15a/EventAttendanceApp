package com.dev.mobile.eventattendance;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import java.sql.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class AttendanceActivity extends AppCompatActivity {

    private class Member {
        String name;        int benefits;
        String latest_date;
        String[] dates;

        public Member(String _name, String _latest_date, String[] _dates) {
            name = _name;
            benefits = 0;
            latest_date = _latest_date;
            dates = _dates;
        }
        public Member(String _name, int _benefits, String _latest_date, String[] _dates) {
            name = _name;
            benefits = _benefits;
            latest_date = _latest_date;
            dates = _dates;
        }

        public void addDate(String date) {
            dates[dates.length] = latest_date;
            latest_date = date;
        }

        public void addBenefit() {
            benefits++;
        }

        public void removeBenefit() {
            if(benefits > 0) {
                benefits--;
            }
        }
    }

    private Member[] members = {
            new Member("Karen Cukrowski","2018-10-23", new String[] {"2018-6-1"}),
            new Member("Nathan Fillian","2018-10-23", new String[] {"2018-6-1", "2018-8-9"}),
            new Member("Scarlet Johansen","2018-10-23", new String[] {"2018-5-2"}),
            new Member("Captain America","2018-10-23", new String[] {"2018-5-2"}),
            new Member("Dr. Aquino","2018-9-23", new String[] {"2018-3-20", "2018-5-7"}),
            new Member("JRR Tolkien","2018-6-8", new String[] {"2018-5-14"}),
            new Member("Elon Musk","2017-1-11", new String[] {"2016-3-25"}),
            new Member("Jeff Goldblume","2018-9-23", new String[] {"2018-7-12"}),
            new Member("Jeff Goldblume","2018-9-23", new String[] {"2018-7-12"}),
            new Member("Jeff Goldblume","2018-9-23", new String[] {"2018-7-12"}),
            new Member("Jeff Goldblume","2018-9-23", new String[] {"2018-7-12"}),
            new Member("Jeff Goldblume","2018-9-23", new String[] {"2018-7-12"}),
            new Member("Jeff Goldblume","2018-9-23", new String[] {"2018-7-12"}),
            new Member("Jeff Goldblume","2018-9-23", new String[] {"2018-7-12"}),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        // Get all the member data from database
        // temporarily we're storing that in the members variable instead

        // TODO add a search bar
        // TODO when the bar is searched, filter members that match the name

        // Create a new name button and toggle button pair for each user
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.layout);

        Button previousMemberButton = null;

        for (final Member member : members) {

            // Main button
            Button memberButton = new Button(this);
            memberButton.setText(member.name);
            memberButton.setId(View.generateViewId());
            memberButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //startActivity(new Intent(AttendanceActivity.this,MemberActivity.class));
                }
            });
            constraintLayout.addView(memberButton);


            // Benefits
            final TextView benefitsCounter = new TextView(this);
            benefitsCounter.setText(Integer.toString(member.benefits));
            benefitsCounter.setId(View.generateViewId());
            constraintLayout.addView(benefitsCounter);

            Button benefitsRemoveButton = new Button(this);
            benefitsRemoveButton.setText("-");
            benefitsRemoveButton.setId(View.generateViewId());
            benefitsRemoveButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    member.removeBenefit();
                    benefitsCounter.setText(Integer.toString(member.benefits));
                }
            });
            constraintLayout.addView(benefitsRemoveButton);

            Button benefitsAddButton = new Button(this);
            benefitsAddButton.setText("+");
            benefitsAddButton.setId(View.generateViewId());
            benefitsAddButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    member.addBenefit();
                    benefitsCounter.setText(Integer.toString(member.benefits));
                }
            });
            constraintLayout.addView(benefitsAddButton);

            // Switch to record date
            Switch switchRecordDate = new Switch(this);
            switchRecordDate.setId(View.generateViewId());
            // toggle should stay switched or not based on the reset date
            // if toggle is switched on manually
            // add date to database
            // if toggle is switched off manually
            // remove the latest date from database
            constraintLayout.addView(switchRecordDate);

            // Create constraints
            ConstraintSet set = new ConstraintSet();
            set.clone(constraintLayout);

            set.connect(memberButton.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT, 32);
            if (previousMemberButton != null) {
                set.connect(memberButton.getId(), ConstraintSet.TOP, previousMemberButton.getId(), ConstraintSet.BOTTOM, 32);
            } else {
                set.connect(memberButton.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, 32);
            }

            set.centerVertically(benefitsRemoveButton.getId(), memberButton.getId());
            set.connect(benefitsRemoveButton.getId(), ConstraintSet.LEFT, memberButton.getId(), ConstraintSet.RIGHT, 16);
            set.constrainWidth(benefitsRemoveButton.getId(),128);

            set.centerVertically(benefitsCounter.getId(), memberButton.getId());
            set.connect(benefitsCounter.getId(), ConstraintSet.LEFT, benefitsRemoveButton.getId(), ConstraintSet.RIGHT, 16);

            set.centerVertically(benefitsAddButton.getId(), memberButton.getId());
            set.connect(benefitsAddButton.getId(), ConstraintSet.LEFT, benefitsCounter.getId(), ConstraintSet.RIGHT, 16);
            set.constrainWidth(benefitsAddButton.getId(),128);

            set.centerVertically(switchRecordDate.getId(), memberButton.getId());
            set.connect(switchRecordDate.getId(), ConstraintSet.LEFT, benefitsAddButton.getId(), ConstraintSet.RIGHT, 16);

            set.applyTo(constraintLayout);

            // Iterate button
            previousMemberButton = memberButton;
        }

        // also create a new page for the member which shows their history

    }
}
