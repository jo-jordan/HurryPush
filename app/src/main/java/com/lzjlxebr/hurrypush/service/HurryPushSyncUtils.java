package com.lzjlxebr.hurrypush.service;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

public class HurryPushSyncUtils {
    public static void startImmediateCoverSync(@NonNull final Context context) {
        Intent intentToImmediately = new Intent(context, HurryPushSyncService.class);
        intentToImmediately.putExtra("update", false);
        context.startService(intentToImmediately);
    }

    public static void startImmediateUpdateSync(@NonNull final Context context) {
        Intent intent = new Intent(context, HurryPushSyncService.class);
        intent.putExtra("update", true);
        context.startService(intent);
    }
}
