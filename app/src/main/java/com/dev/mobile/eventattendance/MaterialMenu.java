package com.dev.mobile.eventattendance;
import com.github.barteksc.pdfviewer.PDFView;

import android.app.Activity;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MaterialMenu extends AppCompatActivity {
    private String [] fileNames = /* db.getFileNames (); */
    {
            //  Not hardcoded when function works and is present
            "MobileAppProposal.pdf",
            "MobileAppProposal2.pdf"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_menu);

        ConstraintLayout constraintLayout = findViewById(R.id.materialLayout);
        Button previousButton = null;

        for (int i = 0; i < fileNames.length; i++) {
            Button newButton = new Button(this);
            newButton.setText(fileNames [i]);
            newButton.setId(View.generateViewId());

            final int iCopy = i;    //  This line is really dumb but needed
            newButton.setOnClickListener(new View.OnClickListener() {
                public void onClick (View v){
                    Intent intent = new Intent(MaterialMenu.this, PDFViewer.class);
                    intent.putExtra("src", fileNames [iCopy]);
                    startActivity(intent);
                }
            });

            constraintLayout.addView(newButton);

            //  Add constraints to the button
            ConstraintSet constraints = new ConstraintSet();
            constraints.clone(constraintLayout);

            constraints.connect(newButton.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT, 32);
            if (previousButton != null) {
                constraints.connect(newButton.getId(), ConstraintSet.TOP, previousButton.getId(), ConstraintSet.BOTTOM, 32);
            } else {
                constraints.connect(newButton.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, 32);
            }

            constraints.applyTo(constraintLayout);
            previousButton = newButton;
        }
    }
}
