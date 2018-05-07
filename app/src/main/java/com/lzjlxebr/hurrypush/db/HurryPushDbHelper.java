package com.lzjlxebr.hurrypush.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class HurryPushDbHelper extends SQLiteOpenHelper {
    private static final String LOG_TAG = HurryPushDbHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "hurrypush.db";
    private static final int DATABASE_VERSION = 2;

    public HurryPushDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_CLIENT_INFO_TABLE =
                "create table " + HurryPushContract.ClientInfoEntry.TABLE_NAME + " (" +
                        HurryPushContract.ClientInfoEntry._ID + " integer primary key autoincrement," +
                        HurryPushContract.ClientInfoEntry.COLUMN_APP_ID + " text not null," +
                        HurryPushContract.ClientInfoEntry.COLUMN_GENDER + " integer not null," +
                        HurryPushContract.ClientInfoEntry.COLUMN_CURRENT_LEVEL_ID + " integer not null default 1," +
                        HurryPushContract.ClientInfoEntry.COLUMN_CURRENT_EXP + " integer not null default 0," +
                        HurryPushContract.ClientInfoEntry.COLUMN_UPGRADE_EXP + " integer not null default 300" +
                        ");";

        final String SQL_CREATE_LEVEL_RULE_TABLE =
                "create table " + HurryPushContract.LevelRuleEntry.TABLE_NAME + " (" +
                        HurryPushContract.LevelRuleEntry._ID + " integer primary key autoincrement," +
                        HurryPushContract.LevelRuleEntry.COLUMN_LEVEL_ID + " integer unique not null," +
                        HurryPushContract.LevelRuleEntry.COLUMN_LEVEL_NUMBER + " integer not null," +
                        HurryPushContract.LevelRuleEntry.COLUMN_LEVEL_EXP_SINGLE + " integer not null," +
                        HurryPushContract.LevelRuleEntry.COLUMN_LEVEL_EXP_TOTAL + " integer not null default 1" +
                        ");";

        final String SQL_CREATE_DEFECATION_RECORD_TABLE =
                "create table " + HurryPushContract.DefecationRecordEntry.TABLE_NAME + " (" +
                        HurryPushContract.DefecationRecordEntry._ID + " integer primary key autoincrement," +
                        HurryPushContract.DefecationRecordEntry.COLUMN_INSERT_TIME + " integer not null," +
                        HurryPushContract.DefecationRecordEntry.COLUMN_START_TIME + " integer not null," +
                        HurryPushContract.DefecationRecordEntry.COLUMN_END_TIME + " integer not null," +
                        HurryPushContract.DefecationRecordEntry.COLUMN_IS_USER_FINISH + " integer not null," +
                        HurryPushContract.DefecationRecordEntry.COLUMN_GAIN_EXP + "  integer not null default 100," +
                        HurryPushContract.DefecationRecordEntry.COLUMN_SMELL + " integer not null," +
                        HurryPushContract.DefecationRecordEntry.COLUMN_CONSTIPATION + " integer not null," +
                        HurryPushContract.DefecationRecordEntry.COLUMN_STICKINESS + " integer not null," +
                        HurryPushContract.DefecationRecordEntry.COLUMN_OVERALL_RATING + " real not null default 0.0," +
                        HurryPushContract.DefecationRecordEntry.COLUMN_UPLOAD_TO_SERVER + " integer not null," +
                        HurryPushContract.DefecationRecordEntry.COLUMN_SERVER_CALL_BACK + " integer not null" +
                        ");";

        final String SQL_CREATE_ACHIEVEMENT_PROGRESS_TABLE =
                "create table " + HurryPushContract.AchievementProgressEntry.TABLE_NAME + " (" +
                        HurryPushContract.AchievementProgressEntry._ID + " integer primary key autoincrement," +
                        HurryPushContract.AchievementProgressEntry.COLUMN_ACHI_TYPE + " integer not null," +
                        HurryPushContract.AchievementProgressEntry.COLUMN_ACHI_REQUIRED_MINUTES + " integer not null," +
                        HurryPushContract.AchievementProgressEntry.COLUMN_ACHI_REQUIRED_DAYS + " integer not null," +
                        HurryPushContract.AchievementProgressEntry.COLUMN_ACHI_ID + " integer not null," +
                        HurryPushContract.AchievementProgressEntry.COLUMN_ACHI_NAME + " text not null," +
                        HurryPushContract.AchievementProgressEntry.COLUMN_ACHI_CONDITION + " integer not null," +
                        HurryPushContract.AchievementProgressEntry.COLUMN_ACHI_PROGRESS + " integer not null," +
                        HurryPushContract.AchievementProgressEntry.COLUMN_UPDATE_TIME + " integer not null" +
                        ");";

        db.execSQL(SQL_CREATE_CLIENT_INFO_TABLE);
        db.execSQL(SQL_CREATE_LEVEL_RULE_TABLE);
        db.execSQL(SQL_CREATE_DEFECATION_RECORD_TABLE);
        db.execSQL(SQL_CREATE_ACHIEVEMENT_PROGRESS_TABLE);


        Log.d(LOG_TAG,"All databases has been created.");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + HurryPushContract.ClientInfoEntry.TABLE_NAME);
        db.execSQL("drop table if exists " + HurryPushContract.LevelRuleEntry.TABLE_NAME);
        db.execSQL("drop table if exists " + HurryPushContract.DefecationRecordEntry.TABLE_NAME);
        db.execSQL("drop table if exists " + HurryPushContract.AchievementProgressEntry.TABLE_NAME);

        onCreate(db);
    }
}
