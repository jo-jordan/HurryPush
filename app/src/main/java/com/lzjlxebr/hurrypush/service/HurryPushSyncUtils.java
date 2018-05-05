package com.lzjlxebr.hurrypush.service;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

public class HurryPushSyncUtils {
    public static void startImmediateSync(@NonNull final Context context){
        Intent intentToImmediately = new Intent(context,HurryPushSyncService.class);
        context.startService(intentToImmediately);
    }
}
