package com.example.project8_3;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
public class DownloadImageWorker extends Worker {
    public static final String apiUrl = "https://random.dog/woof.json";
    public static final String TAG = "TAG_WORKER";

    public DownloadImageWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }
    @NonNull @Override
    public Result doWork() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(apiUrl).build();
            Response response = client.newCall(request).execute();
            JSONObject jsonObject = new JSONObject(response.body().string());
            String imageUrl = jsonObject.getString("url");
            Data outputData = new Data.Builder().putString(TAG, imageUrl).build();
            return Result.success(outputData);
        }
        catch (Exception e) {e.printStackTrace();}
        return Result.failure();
    }
    public String func1(Object[] objs){
        return objs[10].toString();
    }
}
