package com.dev.mobile.eventattendance;

import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Date;

public class MemberProfileActivity extends AppCompatActivity {

    public String name = null;
    public int benefits = 0;
    public String[] dates = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_profile);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            name = extras.getString("name");
            benefits = extras.getInt("benefits");
            dates = extras.getStringArray("dates");
        }

        // TODO only apply if the person is present this week
        if  (false) {
            ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
            constraintLayout.setBackgroundColor(getColor(R.color.colorPresent));
        }

        TextView textViewName = findViewById(R.id.textViewName);
        textViewName.setText(name);

        final TextView textViewBenefits = findViewById(R.id.textViewBenefits);
        textViewBenefits.setText("Benefits: " + Integer.toString(benefits));

        for (String date : dates) {
            addDate(date);
        }

        Button buttonAddBenefit = findViewById(R.id.buttonAddBenefit);
        buttonAddBenefit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                benefits++;
                textViewBenefits.setText("Benefits: " + Integer.toString(benefits));
            }
        });

        Button buttonRemoveBenefit = findViewById(R.id.buttonRemoveBenefit);
        buttonRemoveBenefit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                benefits--;
                textViewBenefits.setText("Benefits: " + Integer.toString(benefits));
            }
        });

        Button buttonRecordDate = findViewById(R.id.buttonRecordDate);
        buttonRecordDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO only apply if the person is present this week
                if (false) {
                    benefits++;
                    textViewBenefits.setText("Benefits: " + Integer.toString(benefits));
                }
                addDate(new Date().toString());
            }
        });
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
