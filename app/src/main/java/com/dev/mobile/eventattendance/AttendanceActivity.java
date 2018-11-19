package com.dev.mobile.eventattendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AttendanceActivity extends AppCompatActivity {

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

    public static ArrayList<TextView> benefits = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        db = AppDatabase.getAppDatabase(getApplicationContext());
        Member[] members = db.dbInterface().getAll();

        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);

        final EditText editTextSearch = findViewById(R.id.editTextSearch);
        Button buttonSearch = findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = editTextSearch.getText().toString().trim();
                Member[] results = db.dbInterface().findByName(searchText);
                // TODO swap out the members list with this results list
            }
        });

        View previousMemberView = null;

        for (final Member member : members) {
            // View
            final View memberPresentView = new View(this);
            memberPresentView.setId(View.generateViewId());
            memberPresentView.setMinimumHeight(282);
            updatePresentColor(member, memberPresentView);

            constraintLayout.addView(memberPresentView);

            // Profile
            // TODO change the image resource with the photo stored in database
            ImageView profileImage = new ImageView(this);
            profileImage.setId(View.generateViewId());
            profileImage.setMaxHeight(250);
            profileImage.setMinimumHeight(250);
            profileImage.setMaxWidth(250);
            profileImage.setMinimumWidth(250);
            if (member.picture != null) {
                Context context = profileImage.getContext();
                int id = context.getResources().getIdentifier(member.picture, "drawable", context.getPackageName());
                profileImage.setBackgroundResource(id);
            }
            else
                profileImage.setBackgroundResource(android.R.drawable.sym_def_app_icon);

            constraintLayout.addView(profileImage);

            String memberName = member.firstName + " ";
            if (member.lastName != null)
                memberName += member.lastName;

            Button memberProfileButton = new Button(this);
            memberProfileButton.setText(memberName);
            memberProfileButton.setId(View.generateViewId());
            memberProfileButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent memberProfileIntent = new Intent(AttendanceActivity.this,MemberProfileActivity.class);
                    memberProfileIntent.putExtra("MEMBER_ID",member.ID);
                    startActivity(memberProfileIntent);
                }
            });
            constraintLayout.addView(memberProfileButton);

            // Benefits
            final TextView benefitsCounter = new TextView(this);
            benefitsCounter.setText(Integer.toString(member.benefits));
            benefitsCounter.setId(View.generateViewId());
            benefits.add(benefitsCounter);
            constraintLayout.addView(benefitsCounter);

            Button benefitsRemoveButton = new Button(this);
            benefitsRemoveButton.setText("-");
            benefitsRemoveButton.setId(View.generateViewId());
            benefitsRemoveButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    member.benefits -= 1;
                    benefitsCounter.setText(Integer.toString(member.benefits));
                    member.updateDB(db);
                }
            });
            constraintLayout.addView(benefitsRemoveButton);

            Button benefitsAddButton = new Button(this);
            benefitsAddButton.setText("+");
            benefitsAddButton.setId(View.generateViewId());
            benefitsAddButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    member.benefits += 1;
                    benefitsCounter.setText(Integer.toString(member.benefits));
                    member.updateDB(db);
                }
            });
            constraintLayout.addView(benefitsAddButton);

            // Button to record date
            Button recordDateButton = new Button(this);
            recordDateButton.setText("Record Presence");
            recordDateButton.setId(View.generateViewId());
            recordDateButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (!member.isPresent()) {
                        member.benefits += 1;
                        member.updateDB(db);
                        AttendanceEntry entry = new AttendanceEntry(db, member.ID, "empty");
                        entry.updateDB(db);
                        newResetTime(member);
                        benefitsCounter.setText(Integer.toString(member.benefits));
                    } else {
                        AttendanceEntry entry = new AttendanceEntry(db, member.ID, "empty");
                        entry.updateDB(db);
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

            set.connect(profileImage.getId(), ConstraintSet.LEFT, memberPresentView.getId(), ConstraintSet.LEFT, 16);
            set.connect(profileImage.getId(), ConstraintSet.TOP, memberPresentView.getId(), ConstraintSet.TOP, 16);

            set.connect(memberProfileButton.getId(), ConstraintSet.LEFT, profileImage.getId(), ConstraintSet.RIGHT, 8);
            set.connect(memberProfileButton.getId(), ConstraintSet.TOP, profileImage.getId(), ConstraintSet.TOP);

            set.connect(benefitsRemoveButton.getId(), ConstraintSet.TOP, memberProfileButton.getId(), ConstraintSet.BOTTOM, 8);
            set.connect(benefitsRemoveButton.getId(), ConstraintSet.LEFT, memberProfileButton.getId(), ConstraintSet.LEFT);
            set.constrainWidth(benefitsRemoveButton.getId(),128);

            set.centerVertically(benefitsCounter.getId(), benefitsRemoveButton.getId());
            set.connect(benefitsCounter.getId(), ConstraintSet.LEFT, benefitsRemoveButton.getId(), ConstraintSet.RIGHT, 16);

            set.centerVertically(benefitsAddButton.getId(), benefitsRemoveButton.getId());
            set.connect(benefitsAddButton.getId(), ConstraintSet.LEFT, benefitsCounter.getId(), ConstraintSet.RIGHT, 16);
            set.constrainWidth(benefitsAddButton.getId(),128);

            set.centerVertically(recordDateButton.getId(), benefitsRemoveButton.getId());
            set.connect(recordDateButton.getId(), ConstraintSet.LEFT, benefitsAddButton.getId(), ConstraintSet.RIGHT, 16);

            set.applyTo(constraintLayout);

            // Iterate view
            previousMemberView = memberPresentView;
        }
    }

    public void updatePresentColor(Member member, View v) {
        if (member.isPresent()) {
            v.setBackgroundColor(getColor(R.color.colorPresent));
        }
    }
}
