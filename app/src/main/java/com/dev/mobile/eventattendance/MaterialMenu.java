package com.dev.mobile.eventattendance;
import com.github.barteksc.pdfviewer.PDFView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class MaterialMenu extends AppCompatActivity {

    public AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_menu);

        db = AppDatabase.getAppDatabase(getApplicationContext());
        final String[] fileNamesOriginal = db.dbInterface().getMaterials();
        final String[] fileNames = new String [fileNamesOriginal.length];

        for (int i = 0; i < fileNamesOriginal.length; i++) {
            fileNames [i] = fileNamesOriginal [i];
        }

        fileNames [0] = "button_particles.mp4";
        fileNames [fileNames.length - 1] = "buttonParticles.mp4";

        ConstraintLayout constraintLayout = findViewById(R.id.materialLayout);
        Button previousButton = null;

        for (int i = 0; i < fileNames.length; i++) {
            Button newButton = new Button(this);
            newButton.setText(fileNames [i]);
            newButton.setId(View.generateViewId());

            final int iCopy = i;    //  This line is really dumb but needed
            newButton.setOnClickListener(new View.OnClickListener() {
                public void onClick (View v){
                    Intent intent;
                    if (fileNames [iCopy].contains(".pdf")) {
                        intent = new Intent(MaterialMenu.this, PDFViewer.class);
                        intent.putExtra("src", fileNames [iCopy]);
                    }
                    else {
                        intent = new Intent(MaterialMenu.this, videoViewer.class);
                        int resId = getResources().getIdentifier("raw/button_particles", null, getApplicationContext().getPackageName());
                        intent.putExtra("src", resId);
                    }
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

            constraints.centerHorizontally(newButton.getId(), constraintLayout.getId ());
            constraints.constrainHeight(newButton.getId (), 1400);
            constraints.constrainWidth(newButton.getId (), 900);

            constraints.applyTo(constraintLayout);
            previousButton = newButton;
        }
    }
}
