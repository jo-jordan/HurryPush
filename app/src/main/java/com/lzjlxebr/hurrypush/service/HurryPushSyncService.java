package com.lzjlxebr.hurrypush.service;

import android.app.IntentService;
import android.content.Intent;
import android.view.Gravity;
import android.widget.Toast;

import com.lzjlxebr.hurrypush.entity.NetSyncTaskComplete;

import org.greenrobot.eventbus.EventBus;

public class HurryPushSyncService extends IntentService {

    private boolean update_flag;

    public HurryPushSyncService() {
        super("HurryPushSyncService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //HurryPushSyncTask.syncClientInfo(this);
        HurryPushSyncTask.syncLevelRule(this);
        //HurryPushSyncTask.syncDefecationRecord(this);
        update_flag = intent.getBooleanExtra("update", true);
        if (update_flag) {
            HurryPushSyncTask.syncUpdateAchievementProgress(this);
        } else {
            HurryPushSyncTask.syncAchievementProgress(this);
        }
        //stopSelf();
    }

    @Override
    public void onDestroy() {
        //NotificationCreator.sendSimpleNotification(this);
        if (update_flag) {
            sendOnCompleteSync();
        } else {
            Toast toast = Toast.makeText(this, "成就与等级数据结构更新完成", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        super.onDestroy();
    }

    private void sendOnCompleteSync() {
        EventBus.getDefault().post(new NetSyncTaskComplete(true));
    }
}
