package com.example.dcelmer.workmanagersyncer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.dcelmer.workmanagersyncer.workers.LoopWorker;
import com.example.dcelmer.workmanagersyncer.workers.RegenerateTokenWorker;
import com.example.dcelmer.workmanagersyncer.workers.SyncRemoteCallsWorker;

import java.util.concurrent.TimeUnit;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Context context = getApplicationContext();
//        Intent intent = new Intent();
//        String packageName = context.getPackageName();
//        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
//
//        if (pm.isIgnoringBatteryOptimizations(packageName))
//            intent.setAction(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
//        else {
//            intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
//            intent.setData(Uri.parse("package:" + packageName));
//        }
//        startActivity(intent);

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        final PeriodicWorkRequest regenerateTokenRequest = new PeriodicWorkRequest.Builder(RegenerateTokenWorker.class, 15, TimeUnit.MINUTES)
                .addTag(RegenerateTokenWorker.TAG)
                .setConstraints(constraints)
                .build();

        final OneTimeWorkRequest loopRequest = new OneTimeWorkRequest.Builder(LoopWorker.class)
                .addTag(SyncRemoteCallsWorker.TAG)
                .build();



        findViewById(R.id.sync_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkManager.getInstance().enqueue(regenerateTokenRequest);
                WorkManager.getInstance().enqueue(loopRequest);
            }
        });

    }
}
