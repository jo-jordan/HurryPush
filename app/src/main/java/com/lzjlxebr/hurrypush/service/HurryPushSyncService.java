package com.lzjlxebr.hurrypush.service;

import android.app.IntentService;
import android.content.Intent;

import com.lzjlxebr.hurrypush.entity.NetSyncTaskComplete;

import org.greenrobot.eventbus.EventBus;

public class HurryPushSyncService extends IntentService {

    public HurryPushSyncService() {
        super("HurryPushSyncService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //HurryPushSyncTask.syncClientInfo(this);
        HurryPushSyncTask.syncLevelRule(this);
        //HurryPushSyncTask.syncDefecationRecord(this);
        HurryPushSyncTask.syncAchievementProgress(this);
        //stopSelf();
    }

    @Override
    public void onDestroy() {
        //NotificationCreator.sendSimpleNotification(this);
        sendOnCompleteSync();
        super.onDestroy();
    }

    private void sendOnCompleteSync() {
        EventBus.getDefault().post(new NetSyncTaskComplete(true));
    }
}
