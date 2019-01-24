package com.example.dcelmer.workmanagersyncer.workers;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class LoopWorker extends Worker {
    public LoopWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        final OneTimeWorkRequest loopRequest = new OneTimeWorkRequest.Builder(LoopWorker.class)
                .addTag(SyncRemoteCallsWorker.TAG)
                .setInitialDelay(2, TimeUnit.MINUTES)
                .build();

        final OneTimeWorkRequest syncLocalCallsRequest = new OneTimeWorkRequest.Builder(SyncLocalCallsWorker.class)
                .addTag(SyncRemoteCallsWorker.TAG)
                .build();

        final OneTimeWorkRequest syncRemoteCallsRequest = new OneTimeWorkRequest.Builder(SyncRemoteCallsWorker.class)
                .addTag(SyncRemoteCallsWorker.TAG)
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance()
                .beginWith(syncLocalCallsRequest)
                .then(syncRemoteCallsRequest)
                .then(loopRequest)
                .enqueue();

        return Result.SUCCESS;
    }
}
