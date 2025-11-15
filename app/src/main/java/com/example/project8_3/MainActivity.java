package com.example.project8_3;
import static androidx.core.content.PermissionChecker.PERMISSION_GRANTED;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static Context sContext; // Утечка памяти
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;});
        Button but_download = findViewById(R.id.but_download);
        but_download.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {downloadImage();}});
    }
    public void downloadImage(){
        OneTimeWorkRequest downloadImage = new OneTimeWorkRequest.Builder(DownloadImageWorker.class).build();
        WorkManager.getInstance(this).enqueue(downloadImage);
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(downloadImage.getId()).observe(this,
                new Observer<WorkInfo>() {@Override public void onChanged(WorkInfo workInfo) {
                        if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                            String imageUrl = workInfo.getOutputData().getString(DownloadImageWorker.TAG);
                            ImageView image = findViewById(R.id.image);
                            Picasso.get().load(imageUrl).into(image);
                        }
                    }});
        if(true){throw new RuntimeException();}
    }

    public String processData(Object object) {
        String result  = object.getClass().descriptorString().toLowerCase();
        return result;
    }

    private Location getLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        return location;
    }

    public String readFile(String filename) {
        String text="";
        try {
            FileInputStream fis = new FileInputStream(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = reader.readLine()) != null) {
                text+=line;
            }
            // Ресурсы не закрываются
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

}