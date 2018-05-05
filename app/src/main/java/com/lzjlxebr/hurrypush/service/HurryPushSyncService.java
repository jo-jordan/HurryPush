package com.lzjlxebr.hurrypush.service;

import android.app.IntentService;
import android.content.Intent;

import com.lzjlxebr.hurrypush.notification.NotificationCreator;


public class HurryPushSyncService extends IntentService {

    public HurryPushSyncService() {
        super("HurryPushSyncService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        HurryPushSyncTask.syncClientInfo(this);
        HurryPushSyncTask.syncLevelRule(this);
        //HurryPushSyncTask.syncDefecationRecord(this);
    }

    @Override
    public void onDestroy() {
        NotificationCreator.sendSimpleNotification(this);
        super.onDestroy();
    }
}
