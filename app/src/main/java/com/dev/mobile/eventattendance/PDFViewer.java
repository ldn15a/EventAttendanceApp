package com.dev.mobile.eventattendance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import com.github.barteksc.pdfviewer.PDFView;
import android.os.Bundle;

public class PDFViewer extends AppCompatActivity {

    private PDFView PDFRenderer;
    private String sourceFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);

        Intent intent = getIntent();
        try {
            sourceFile = intent.getExtras().getString("src");
        }
        catch (Exception E)
        {
            System.out.println ("intent.getExtras().getString(\"src\") returned null");
        }

        PDFRenderer = findViewById(R.id.PDFRenderer);
        PDFRenderer.fromAsset(sourceFile).load();
    }
}
