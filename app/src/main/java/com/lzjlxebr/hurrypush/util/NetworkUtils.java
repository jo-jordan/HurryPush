package com.lzjlxebr.hurrypush.util;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    private static final String FAKE_DATA_URL =
            "https://raw.githubusercontent.com/lzjlxebr/hurrypush-data/master/fake_data";

    private static final String FAKE_CLIENT_INFO_SUFFIX = "client_info.json";

    private static final String FAKE_LEVEL_RULE_SUFFIX = "level_rule.json";

    private static final String FAKE_DEFECATION_RECORD = "defecation_record.json";
    private static final String FAKE_ACHIEVEMENT_PROGRESS = "achievement_progress.json";


    // build client_info url
    public static URL buildClientInfoUrl(){
        Uri clientInfoUri = Uri.parse(FAKE_DATA_URL).buildUpon()
                .appendPath(FAKE_CLIENT_INFO_SUFFIX)
                .build();

        try {
            URL clientInfoUrl = new URL(clientInfoUri.toString());
            Log.d(LOG_TAG,"clientInfoUrl: " + clientInfoUrl);
            return clientInfoUrl;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // build level_rule url
    public static URL buildLevelRuleUrl(){
        Uri levelRuleUri = Uri.parse(FAKE_DATA_URL).buildUpon()
                .appendPath(FAKE_LEVEL_RULE_SUFFIX)
                .build();

        try {
            URL levelRuleUrl = new URL(levelRuleUri.toString());
            Log.d(LOG_TAG,"levelRuleUrl: " + levelRuleUrl);
            return levelRuleUrl;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // build defecation_record url
    public static URL buildDefecationRecordUrl(){
        Uri defecationRecordUri = Uri.parse(FAKE_DATA_URL).buildUpon()
                .appendPath(FAKE_DEFECATION_RECORD)
                .build();

        try {
            URL defecationRecordUrl = new URL(defecationRecordUri.toString());
            Log.d(LOG_TAG,"defecationRecordUrl: " + defecationRecordUrl);
            return defecationRecordUrl;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // get fake data from server
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");

        Log.d(LOG_TAG,"http response: " + urlConnection.getResponseCode() + urlConnection.getResponseMessage());
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            String response = null;
            if (hasInput) {
                response = scanner.next();
            }
            scanner.close();
            Log.d(LOG_TAG,"response body: " + response);
            return response;
        } finally {
            urlConnection.disconnect();
        }
    }

    public static URL buildAchievementProgressUrl() {
        Uri achievementProgressUri = Uri.parse(FAKE_DATA_URL).buildUpon()
                .appendPath(FAKE_ACHIEVEMENT_PROGRESS)
                .build();

        try {
            URL achievementProgressUrl = new URL(achievementProgressUri.toString());
            Log.d(LOG_TAG,"defecationRecordUrl: " + achievementProgressUrl);
            return achievementProgressUrl;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
