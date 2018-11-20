package com.dev.mobile.eventattendance;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MemberProfileActivity extends AppCompatActivity {

    public AppDatabase db;

    public Member member = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_profile);

        db = AppDatabase.getAppDatabase(getApplicationContext());

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            int id = extras.getInt("MEMBER_ID");
            member = db.dbInterface().findByID(id);
        }

        AttendanceEntry[] attendanceList = db.dbInterface().memberAttendance(member.ID);
        ArrayList<String> dates = new ArrayList<>();
        for (AttendanceEntry attendance : attendanceList) {
            dates.add(attendance.dateAttended);
        }

        updatePresentColor();

        TextView textViewName = findViewById(R.id.textViewName);
        String name = member.firstName + " " + member.lastName;
        textViewName.setText(name);

        final TextView textViewBenefits = findViewById(R.id.textViewBenefits);
        textViewBenefits.setText("Benefits: " + Integer.toString(member.benefits));

        for (String date : dates) {
            addDate(date);
        }

        Button buttonAddBenefit = findViewById(R.id.buttonAddBenefit);
        buttonAddBenefit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                member.benefits++;
                textViewBenefits.setText("Benefits: " + Integer.toString(member.benefits));
                member.updateDB(db);
            }
        });

        Button buttonRemoveBenefit = findViewById(R.id.buttonRemoveBenefit);
        buttonRemoveBenefit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                member.benefits--;
                textViewBenefits.setText("Benefits: " + Integer.toString(member.benefits));
                member.updateDB(db);
            }
        });

        Button buttonRecordDate = findViewById(R.id.buttonRecordDate);
        buttonRecordDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!member.isPresent()) {
                    member.benefits++;
                    textViewBenefits.setText("Benefits: " + Integer.toString(member.benefits));
                }

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
                addDate(simpleDateFormat.format(new Date()));
                AttendanceEntry entry = new AttendanceEntry(db, member.ID, "empty");
                entry.updateDB(db);

                member.newResetTime(db);
                member.updateDB(db);

                updatePresentColor();
            }
        });
    }

    public void updatePresentColor() {
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
        if (member.isPresent()) {
            constraintLayout.setBackgroundColor(getColor(R.color.colorPresent));
        }
    }

    private boolean IS_COLORED = false;

    public void addDate(String date) {
        TextView newTextView = new TextView(this);
        newTextView.setText(date);
        newTextView.setTextSize(24);
        newTextView.setPadding(48,8,0,8);

        if (IS_COLORED) {
            newTextView.setBackgroundColor(getColor(R.color.colorDate));
        }
        IS_COLORED = !IS_COLORED;

        LinearLayout linearLayout = findViewById(R.id.layoutDates);
        linearLayout.addView(newTextView);
    }
}
