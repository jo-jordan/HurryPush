package com.lzjlxebr.hurrypush.service;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.lzjlxebr.hurrypush.db.HurryPushContract;
import com.lzjlxebr.hurrypush.util.HurryPushJsonUtils;
import com.lzjlxebr.hurrypush.util.NetworkUtils;

import java.net.URL;

public class HurryPushSyncTask {
    private static final String LOG_TAG = HurryPushSyncTask.class.getSimpleName();

    /**
     * Performs the network request for updated client info, parses the JSON from that request, and
     * inserts the new client info into ContentProvider. Will notify the user that new
     * client info has been loaded if the user hasn't been notified of the client info
     * AND they haven't disabled notifications in the preferences screen.
     */
    synchronized public static void syncClientInfo(Context context) {
        Log.d(LOG_TAG, "entry of sync lient info method.");
        try {
            // build client info url
            URL requestUrl = NetworkUtils.buildClientInfoUrl();
            Log.d(LOG_TAG, "requestUrl: " + requestUrl);
            // get response from http request
            String jsonResponse = NetworkUtils.getResponseFromHttpUrl(requestUrl);
            Log.d(LOG_TAG, "jsonResponse: " + jsonResponse);
            // build content values for content provider
            ContentValues[] contentValues = HurryPushJsonUtils.getClientInfoContentValuesFromJson(context, jsonResponse);
            Log.d(LOG_TAG, "contentValues is null? " + (contentValues == null));
            // save data
            if (contentValues != null && contentValues.length != 0) {
                ContentResolver contentResolver = context.getContentResolver();

                contentResolver.delete(
                        HurryPushContract.ClientInfoEntry.CLIENT_INFO_URI,
                        null,
                        null
                );

                int rows = contentResolver.bulkInsert(
                        HurryPushContract.ClientInfoEntry.CLIENT_INFO_URI,
                        contentValues
                );
                Log.d(LOG_TAG, "bulk inserted rows: " + rows);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    synchronized public static void syncLevelRule(Context context) {
        try {
            // build client info url
            URL requestUrl = NetworkUtils.buildLevelRuleUrl();
            // get response from http request
            String jsonResponse = NetworkUtils.getResponseFromHttpUrl(requestUrl);
            // build content values for content provider
            ContentValues[] contentValues = HurryPushJsonUtils.getLevelRuleContentValuesFromJson(context, jsonResponse);
            // save data
            if (contentValues != null && contentValues.length != 0) {
                ContentResolver contentResolver = context.getContentResolver();

                contentResolver.delete(
                        HurryPushContract.LevelRuleEntry.LEVEL_RULE_URI,
                        null,
                        null
                );

                contentResolver.bulkInsert(
                        HurryPushContract.LevelRuleEntry.LEVEL_RULE_URI,
                        contentValues
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    synchronized public static void syncDefecationRecord(Context context) {
        try {
            // build client info url
            URL requestUrl = NetworkUtils.buildDefecationRecordUrl();
            // get response from http request
            String jsonResponse = NetworkUtils.getResponseFromHttpUrl(requestUrl);
            // build content values for content provider
            ContentValues[] contentValues = HurryPushJsonUtils.getDefecationRecordContentValuesFromJson(context, jsonResponse);
            // save data
            if (contentValues != null && contentValues.length != 0) {
                ContentResolver contentResolver = context.getContentResolver();

                contentResolver.delete(
                        HurryPushContract.DefecationRecordEntry.DEFECATION_RECORD_URI,
                        null,
                        null
                );

                contentResolver.bulkInsert(
                        HurryPushContract.DefecationRecordEntry.DEFECATION_RECORD_URI,
                        contentValues
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    synchronized public static void syncAchievementProgress(Context context) {
        try {
            // build client info url
            URL requestUrl = NetworkUtils.buildAchievementProgressUrl();
            // get response from http request
            String jsonResponse = NetworkUtils.getResponseFromHttpUrl(requestUrl);
            // build content values for content provider
            ContentValues[] contentValues = HurryPushJsonUtils.getAchievementProgressContentValuesFromJson(context, jsonResponse);
            // save data
            if (contentValues != null && contentValues.length != 0) {
                ContentResolver contentResolver = context.getContentResolver();

                contentResolver.delete(
                        HurryPushContract.AchievementProgressEntry.ACHIEVEMENT_PROGRESS_URI,
                        null,
                        null
                );

                contentResolver.bulkInsert(
                        HurryPushContract.AchievementProgressEntry.ACHIEVEMENT_PROGRESS_URI,
                        contentValues
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
