package com.lzjlxebr.hurrypush.util;

import android.content.ContentValues;
import android.content.Context;

import com.lzjlxebr.hurrypush.db.HurryPushContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HurryPushJsonUtils {
    private static final String LOG_TAG = HurryPushJsonUtils.class.getSimpleName();

    // common fields
    private static final String TABLE_NAME = "table_name";
    private static final String TABLE_COLUMNS = "table_columns";

    // client info
    private static final String TABLE_CLIENT_INFO = "client_info";
    private static final String CLIENT_INFO_ID = "_id";
    private static final String CLIENT_APP_ID = "app_id";
    private static final String CLIENT_GENDER = "gender";
    private static final String CLIENT_CURRENT_LEVEL_ID = "current_level_id";
    private static final String CLIENT_CURRENT_LEVEL_EXP = "current_level_exp";
    private static final String CLIENT_UPGRADE_EXP = "upgrade_exp";
    // defecation record
    private static final String TABLE_DEFECATION_RECORD = "defecation_record";
    private static final String DEFECATION_RECORD__ID = "_id";
    private static final String DEFECATION_RECORD_INSERT_TIME = "insert_time";
    private static final String DEFECATION_RECORD_START_TIME = "start_time";
    private static final String DEFECATION_RECORD_END_TIME = "end_time";
    private static final String DEFECATION_RECORD_IS_USER_FINISH = "is_user_finish";
    private static final String DEFECATION_RECORD_GAIN_EXP = "gain_exp";
    private static final String DEFECATION_RECORD_SMELL = "smell";
    private static final String DEFECATION_RECORD_CONSTIPATION = "constipation";
    private static final String DEFECATION_RECORD_STICKINESS = "stickiness";
    private static final String DEFECATION_RECORD_OVERALL_RATING = "overall_rating";
    private static final String DEFECATION_RECORD_UPLOAD_TO_SERVER = "upload_to_server";
    private static final String DEFECATION_RECORD_SERVER_CALL_BACK = "server_call_back";
    // level rule
    private static final String TABLE_LEVEL_RULE = "level_rule";
    private static final String LEVEL_RULE_LEVEL_ID = "level_id";
    private static final String LEVEL_RULE_LEVEL_NUMBER = "level_number";
    private static final String LEVEL_RULE_LEVEL_EXP_SINGLE = "level_exp_single";
    private static final String LEVEL_RULE_LEVEL_TOTAL = "level_total";
    // achievement progress
    private static final String ACHIEVEMENT_PROGRESS_ID = "_id";
    private static final String ACHIEVEMENT_PROGRESS_ACHI_TYPE = "achi_type";
    private static final String ACHIEVEMENT_PROGRESS_ACHI_REQUIRED_MINUTES = "achi_required_minutes";
    private static final String ACHIEVEMENT_PROGRESS_ACHI_REQUIRED_DAYS= "achi_required_days";
    private static final String ACHIEVEMENT_PROGRESS_ACHI_ID = "achi_id";
    private static final String ACHIEVEMENT_PROGRESS_ACHI_NAME = "achi_name";
    private static final String ACHIEVEMENT_PROGRESS_ACHI_CONDITION = "achi_condition";
    private static final String ACHIEVEMENT_PROGRESS_ACHI_PROGRESS = "achi_progress";
    private static final String ACHIEVEMENT_PROGRESS_UPDATE_TIME = "update_time";
    private static final String ACHIEVEMENT_PROGRESS_ACHI_DESCRIPTION = "achi_description";


    public static ContentValues[] getClientInfoContentValuesFromJson(Context context, String jsonResponse) throws JSONException {
        JSONObject clientInfoJson = new JSONObject(jsonResponse);
//        if (!clientInfoJson.has(TABLE_CLIENT_INFO)) {
//            return null;
//        }

        JSONArray jsonArray = clientInfoJson.getJSONArray(TABLE_COLUMNS);

        ContentValues[] contentValues = new ContentValues[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            //int _id;
            String app_id;
            int gender;
            int current_level_id;
            int current_level_exp;
            int upgrade_exp;

            JSONObject column = jsonArray.getJSONObject(i);

            //_id = column.getInt(CLIENT_INFO_ID);
            app_id = column.getString(CLIENT_APP_ID);
            gender = column.getInt(CLIENT_GENDER);
            current_level_id = column.getInt(CLIENT_CURRENT_LEVEL_ID);
            current_level_exp = column.getInt(CLIENT_CURRENT_LEVEL_EXP);
            upgrade_exp = column.getInt(CLIENT_UPGRADE_EXP);

            ContentValues contentValue = new ContentValues();
            contentValue.put(HurryPushContract.ClientInfoEntry.COLUMN_APP_ID, app_id);
            contentValue.put(HurryPushContract.ClientInfoEntry.COLUMN_GENDER, gender);
            contentValue.put(HurryPushContract.ClientInfoEntry.COLUMN_CURRENT_LEVEL_ID, current_level_id);
            contentValue.put(HurryPushContract.ClientInfoEntry.COLUMN_CURRENT_EXP, current_level_exp);
            contentValue.put(HurryPushContract.ClientInfoEntry.COLUMN_UPGRADE_EXP, upgrade_exp);

            contentValues[i] = contentValue;
        }

        //Log.d(LOG_TAG,"today json: "+jsonResponse);


        return contentValues;
    }

    public static ContentValues[] getDefecationRecordContentValuesFromJson(Context context, String jsonResponse) throws JSONException {
        JSONObject defecationRecordJson = new JSONObject(jsonResponse);

        JSONArray jsonArray = defecationRecordJson.getJSONArray(TABLE_COLUMNS);

        ContentValues[] contentValues = new ContentValues[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            String insert_time;
            String start_time;
            String end_time;
            int is_user_finish;
            int gain_exp;
            int smell;
            int constipation;
            int stickiness;
            double overall_rating;
            int upload_to_server;
            int server_call_back;

            JSONObject column = jsonArray.getJSONObject(i);
            insert_time = column.getString(DEFECATION_RECORD_INSERT_TIME);
            start_time = column.getString(DEFECATION_RECORD_START_TIME);
            end_time = column.getString(DEFECATION_RECORD_END_TIME);
            is_user_finish = column.getInt(DEFECATION_RECORD_IS_USER_FINISH);
            gain_exp = column.getInt(DEFECATION_RECORD_GAIN_EXP);
            smell = column.getInt(DEFECATION_RECORD_SMELL);
            constipation = column.getInt(DEFECATION_RECORD_CONSTIPATION);
            stickiness = column.getInt(DEFECATION_RECORD_STICKINESS);
            overall_rating = column.getDouble(DEFECATION_RECORD_OVERALL_RATING);
            upload_to_server = column.getInt(DEFECATION_RECORD_UPLOAD_TO_SERVER);
            server_call_back = column.getInt(DEFECATION_RECORD_SERVER_CALL_BACK);


            ContentValues contentValue = new ContentValues();
            contentValue.put(HurryPushContract.DefecationRecordEntry.COLUMN_INSERT_TIME, DateFormatterUtils.normalizeDateFromString(insert_time));
            contentValue.put(HurryPushContract.DefecationRecordEntry.COLUMN_START_TIME, DateFormatterUtils.normalizeDateFromString(start_time));
            contentValue.put(HurryPushContract.DefecationRecordEntry.COLUMN_END_TIME, DateFormatterUtils.normalizeDateFromString(end_time));
            contentValue.put(HurryPushContract.DefecationRecordEntry.COLUMN_IS_USER_FINISH, is_user_finish);
            contentValue.put(HurryPushContract.DefecationRecordEntry.COLUMN_GAIN_EXP, gain_exp);
            contentValue.put(HurryPushContract.DefecationRecordEntry.COLUMN_SMELL, smell);
            contentValue.put(HurryPushContract.DefecationRecordEntry.COLUMN_CONSTIPATION, constipation);
            contentValue.put(HurryPushContract.DefecationRecordEntry.COLUMN_STICKINESS, stickiness);
            contentValue.put(HurryPushContract.DefecationRecordEntry.COLUMN_OVERALL_RATING, overall_rating);
            contentValue.put(HurryPushContract.DefecationRecordEntry.COLUMN_UPLOAD_TO_SERVER, upload_to_server);
            contentValue.put(HurryPushContract.DefecationRecordEntry.COLUMN_SERVER_CALL_BACK, server_call_back);

            contentValues[i] = contentValue;
        }


        return contentValues;
    }

    public static ContentValues[] getLevelRuleContentValuesFromJson(Context context, String jsonResponse) throws JSONException {
        JSONObject levelRuleJson = new JSONObject(jsonResponse);

        JSONArray jsonArray = levelRuleJson.getJSONArray(TABLE_COLUMNS);

        ContentValues[] contentValues = new ContentValues[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            int level_id;
            int level_number;
            int level_exp_single;
            int level_total;

            JSONObject column = jsonArray.getJSONObject(i);

            level_id = column.getInt(LEVEL_RULE_LEVEL_ID);
            level_number = column.getInt(LEVEL_RULE_LEVEL_NUMBER);
            level_exp_single = column.getInt(LEVEL_RULE_LEVEL_EXP_SINGLE);
            level_total = column.getInt(LEVEL_RULE_LEVEL_TOTAL);

            ContentValues contentValue = new ContentValues();
            contentValue.put(HurryPushContract.LevelRuleEntry.COLUMN_LEVEL_ID, level_id);
            contentValue.put(HurryPushContract.LevelRuleEntry.COLUMN_LEVEL_NUMBER, level_number);
            contentValue.put(HurryPushContract.LevelRuleEntry.COLUMN_LEVEL_EXP_SINGLE, level_exp_single);
            contentValue.put(HurryPushContract.LevelRuleEntry.COLUMN_LEVEL_EXP_TOTAL, level_total);

            contentValues[i] = contentValue;
        }


        return contentValues;
    }

    public static ContentValues[] getAchievementProgressContentValuesFromJson(Context context, String jsonResponse) throws JSONException {
        JSONObject achievementProgressJson = new JSONObject(jsonResponse);

        JSONArray jsonArray = achievementProgressJson.getJSONArray(TABLE_COLUMNS);

        ContentValues[] contentValues = new ContentValues[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            int achi_type;
            int achi_required_minutes;
            int achi_required_days;
            int achi_id;
            String achi_name;
            int achi_condition;
            int achi_progress;
            String update_time;
            String achi_description;

            JSONObject column = jsonArray.getJSONObject(i);

            achi_type = column.getInt(ACHIEVEMENT_PROGRESS_ACHI_TYPE);
            achi_required_minutes = column.getInt(ACHIEVEMENT_PROGRESS_ACHI_REQUIRED_MINUTES);
            achi_required_days = column.getInt(ACHIEVEMENT_PROGRESS_ACHI_REQUIRED_DAYS);
            achi_id = column.getInt(ACHIEVEMENT_PROGRESS_ACHI_ID);
            achi_name = column.getString(ACHIEVEMENT_PROGRESS_ACHI_NAME);
            achi_condition = column.getInt(ACHIEVEMENT_PROGRESS_ACHI_CONDITION);
            achi_progress = column.getInt(ACHIEVEMENT_PROGRESS_ACHI_PROGRESS);
            update_time = column.getString(ACHIEVEMENT_PROGRESS_UPDATE_TIME);
            achi_description = column.getString(ACHIEVEMENT_PROGRESS_ACHI_DESCRIPTION);

            ContentValues contentValue = new ContentValues();
            contentValue.put(HurryPushContract.AchievementProgressEntry.COLUMN_ACHI_TYPE, achi_type);
            contentValue.put(HurryPushContract.AchievementProgressEntry.COLUMN_ACHI_REQUIRED_MINUTES, achi_required_minutes);
            contentValue.put(HurryPushContract.AchievementProgressEntry.COLUMN_ACHI_REQUIRED_DAYS, achi_required_days);
            contentValue.put(HurryPushContract.AchievementProgressEntry.COLUMN_ACHI_ID, achi_id);
            contentValue.put(HurryPushContract.AchievementProgressEntry.COLUMN_ACHI_NAME, achi_name);
            contentValue.put(HurryPushContract.AchievementProgressEntry.COLUMN_ACHI_CONDITION, achi_condition);
            contentValue.put(HurryPushContract.AchievementProgressEntry.COLUMN_ACHI_PROGRESS, achi_progress);
            contentValue.put(HurryPushContract.AchievementProgressEntry.COLUMN_UPDATE_TIME, DateFormatterUtils.normalizeDateFromString(update_time));
            contentValue.put(HurryPushContract.AchievementProgressEntry.COLUMN_ACHI_DESCRIPTION, achi_description);

            contentValues[i] = contentValue;
        }


        return contentValues;
    }
}
