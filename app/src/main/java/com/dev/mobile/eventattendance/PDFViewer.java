package com.dev.mobile.eventattendance;

import android.content.Intent;
import com.github.barteksc.pdfviewer.PDFView;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class PDFViewer extends AppCompatActivity {
    private String sourceFile = "";

    PDFView PDFRenderer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);

        Intent intent = getIntent();
        String sourceFile = intent.getExtras().getString("src");

        PDFRenderer = findViewById(R.id.PDFRenderer);
        PDFRenderer.fromAsset(sourceFile).load();
    }
}
