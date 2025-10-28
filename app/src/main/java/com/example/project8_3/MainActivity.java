package com.example.project8_3;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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
public class MainActivity extends AppCompatActivity {
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
    }}