package com.dev.mobile.eventattendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import java.io.File;
import java.net.URI;

public class videoViewer extends AppCompatActivity {

    private int sourceFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_viewer);


        Intent intent = getIntent();
        try {
            sourceFile = intent.getExtras().getInt("src");
        } catch (Exception E) {
            System.out.println("intent.getExtras().getString(\"src\") returned null");
        }

        // initiate a video view
        VideoView simpleVideoView = (VideoView) findViewById(R.id.simpleVideoView);
        simpleVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + sourceFile));
        simpleVideoView.start();
    }
}