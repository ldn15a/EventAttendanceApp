package com.dev.mobile.eventattendance;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class AttendanceActivity extends AppCompatActivity {

    // TODO store and retrieve this value from the database
    private static final int REFRESH_PERIOD = 7;

    private class Member {
        String name;
        int benefits;
        ArrayList<Date> dates;

        public Member(String _name, Date[] _dates) {
            name = _name;
            benefits = _dates != null ? _dates.length : 0;
            dates = new ArrayList<>(Arrays.asList(_dates));
        }

        public void addDate() {
            dates.add(new Date());
        }

        public void addBenefit() {
            benefits++;
        }

        public void removeBenefit() {
            if(benefits > 0) {
                benefits--;
            }
        }

        public boolean isPresent() {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dates.get(dates.size() -1));
            calendar.add(Calendar.DAY_OF_YEAR, REFRESH_PERIOD);
            Date expire_date = calendar.getTime();
            return expire_date.after(new Date());
        }
    }

    private Member[] members = {
            new Member("Karen Cukrowski", new Date[] {new Date(118, 11, 6)}),
            new Member("James Prather", new Date[] {new Date(118, 9, 9)}),
            new Member("Nathan Fillian", new Date[] {new Date(118, 10, 21)}),
            new Member("Rich Tanner", new Date[] {new Date(118, 11, 15)}),
            new Member("Jesse James", new Date[] {new Date(118, 11, 3)}),
            new Member("Scarlet Johansen", new Date[] {new Date(118, 5, 1)}),
            new Member("Wonder Woman", new Date[] {new Date(118, 7, 23)}),
            new Member("Venom", new Date[] {new Date(2018, 9, 10)}),
            new Member("Katie Perry", new Date[] {new Date(118, 11, 14)}),
            new Member("Haley Kiyoko", new Date[] {new Date(118, 2, 19)}),
            new Member("Jeff Goldblume", new Date[] {new Date(117, 10, 8)}),
            new Member("Chris Pratt", new Date[] {new Date(118, 9, 26)}),
            new Member("Chris Hemsworth", new Date[] {new Date(118, 11, 1)}),
            new Member("Chris Evans", new Date[] {new Date(118, 9, 5)}),
            new Member("Christopher Rabbit", new Date[] {new Date(118, 11, 1)}),
            new Member("Bugs Bunny", new Date[] {new Date(118, 11, 8)}),
            new Member("Steven Rodgers", new Date[] {new Date(118, 11, 12)}),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        // TODO replace members variable with people stored in database

        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);

        final EditText editTextSearch = findViewById(R.id.editTextSearch);
        Button buttonSearch = findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO make search bar query database
                // editTextSearch.getText().toString().trim();
            }
        });

        View previousMemberView = null;

        for (final Member member : members) {
            // View
            final View memberPresentView = new View(this);
            memberPresentView.setId(View.generateViewId());
            memberPresentView.setMinimumWidth(40);
            memberPresentView.setMinimumHeight(140);
            updatePresentColor(member, memberPresentView);

            constraintLayout.addView(memberPresentView);

            // Profile
            Button memberProfileButton = new Button(this);
            memberProfileButton.setText(member.name);
            memberProfileButton.setId(View.generateViewId());
            memberProfileButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent memberProfileIntent = new Intent(AttendanceActivity.this,MemberProfileActivity.class);
                    memberProfileIntent.putExtra("name",member.name);
                    memberProfileIntent.putExtra("benefits",member.benefits);
                    ArrayList<String> dates = new ArrayList<>();
                    for (Date d : member.dates) {
                        dates.add(d.toString());
                    }
                    memberProfileIntent.putExtra("dates", dates.toArray(new String[dates.size()]));
                    startActivity(memberProfileIntent);
                }
            });
            constraintLayout.addView(memberProfileButton);

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

            // Button to record date
            Button recordDateButton = new Button(this);
            recordDateButton.setText("Record");
            recordDateButton.setId(View.generateViewId());
            recordDateButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    member.addDate();
                    if (!member.isPresent()) {
                        member.addBenefit();
                        benefitsCounter.setText(Integer.toString(member.benefits));
                    }
                    updatePresentColor(member, memberPresentView);
                }
            });
            constraintLayout.addView(recordDateButton);

            // Create constraints
            ConstraintSet set = new ConstraintSet();
            set.clone(constraintLayout);

            set.connect(memberPresentView.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT);
            if (previousMemberView != null) {
                set.connect(memberPresentView.getId(), ConstraintSet.TOP, previousMemberView.getId(), ConstraintSet.BOTTOM, 0);
            } else {
                set.connect(memberPresentView.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, 0);
            }

            set.centerVertically(memberProfileButton.getId(), memberPresentView.getId());
            set.connect(memberProfileButton.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT, 32);

            set.centerVertically(benefitsRemoveButton.getId(), memberProfileButton.getId());
            set.connect(benefitsRemoveButton.getId(), ConstraintSet.LEFT, memberProfileButton.getId(), ConstraintSet.RIGHT, 16);
            set.constrainWidth(benefitsRemoveButton.getId(),128);

            set.centerVertically(benefitsCounter.getId(), memberProfileButton.getId());
            set.connect(benefitsCounter.getId(), ConstraintSet.LEFT, benefitsRemoveButton.getId(), ConstraintSet.RIGHT, 16);

            set.centerVertically(benefitsAddButton.getId(), memberProfileButton.getId());
            set.connect(benefitsAddButton.getId(), ConstraintSet.LEFT, benefitsCounter.getId(), ConstraintSet.RIGHT, 16);
            set.constrainWidth(benefitsAddButton.getId(),128);

            set.centerVertically(recordDateButton.getId(), memberProfileButton.getId());
            set.connect(recordDateButton.getId(), ConstraintSet.LEFT, benefitsAddButton.getId(), ConstraintSet.RIGHT, 16);

            set.applyTo(constraintLayout);

            // Iterate button
            previousMemberView = memberPresentView;
        }
    }

    public void updatePresentColor(Member member, View v) {
        if (member.isPresent()) {
            v.setBackgroundColor(getResources().getColor(R.color.colorPresent));
        }
    }
}
