package com.example.dcelmer.workmanagersyncer.workers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import com.example.dcelmer.workmanagersyncer.R;
import com.example.dcelmer.workmanagersyncer.communication.FocusRequest;
import com.example.dcelmer.workmanagersyncer.communication.FocusService;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class SyncRemoteCallsWorker extends Worker {

    public static final String TAG = "SYNC_REMOTE_CALLS_WORKER";

    public SyncRemoteCallsWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        displayNotification("Synchronizuje połączenia", "Synchronizuje połączenia...");
        syncCalls();
        return Result.SUCCESS;
    }

    private void syncCalls() {
        FocusService focusService = FocusService.getInstance(getApplicationContext());
        focusService.syncCalls();
    }

    private void displayNotification(String title, String task) {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("simplifiedcoding", "simplifiedcoding", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), "simplifiedcoding")
                .setContentTitle(title)
                .setContentText(task)
                .setSmallIcon(R.mipmap.ic_launcher);

        notificationManager.notify(1, notification.build());
    }
}
