package com.lzjlxebr.hurrypush.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class HurryPushProvider extends ContentProvider {
    private static final String LOG_TAG = HurryPushProvider.class.getSimpleName();
    // add matcher for client_info
    public static final int CODE_CLIENT_INFO_ONLY_ONE = 100;

    // add matcher for level_rule
    public static final int CODE_LEVEL_RULE = 200;
    public static final int CODE_LEVEL_RULE_WITH_LEVEL_ID = 201;

    // add matcher for defecation_record
    public static final int CODE_DEFECATION_RECORD = 300;
    public static final int CODE_DEFECATION_RECORD_WITH_INSERT_TIME = 301;
    public static final int CODE_DEFECATION_RECORD_BY_10 = 310;

    // add matcher for achievement
    public static final int CODE_ACHIEVEMENT = 400;

    // add all needed uri matcher
    private static final UriMatcher uriMatcher = buildUriMatcher();
    private HurryPushDbHelper hurryPushDbHelper;

    public static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = HurryPushContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, HurryPushContract.ClientInfoEntry.PATH_CLIENT_INFO, CODE_CLIENT_INFO_ONLY_ONE);

        matcher.addURI(authority, HurryPushContract.LevelRuleEntry.PATH_LEVEL_RULE, CODE_LEVEL_RULE);
        matcher.addURI(authority, HurryPushContract.LevelRuleEntry.PATH_LEVEL_RULE + "/#", CODE_LEVEL_RULE_WITH_LEVEL_ID);

        matcher.addURI(authority, HurryPushContract.DefecationRecordEntry.PATH_DEFECATION_RECORD, CODE_DEFECATION_RECORD);
        matcher.addURI(authority, HurryPushContract.DefecationRecordEntry.PATH_DEFECATION_RECORD + "/#", CODE_DEFECATION_RECORD_WITH_INSERT_TIME);
        matcher.addURI(authority, HurryPushContract.DefecationRecordEntry.PATH_DEFECATION_RECORD + "/10", CODE_DEFECATION_RECORD_BY_10);


        matcher.addURI(authority, HurryPushContract.AchievementProgressEntry.PATH_ACHIEVEMENT_PROGRESS, CODE_ACHIEVEMENT);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        hurryPushDbHelper = new HurryPushDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            // for client_info
            case CODE_CLIENT_INFO_ONLY_ONE: {
                cursor = hurryPushDbHelper.getReadableDatabase().query(
                        HurryPushContract.ClientInfoEntry.TABLE_NAME,
                        projection,
                        HurryPushContract.ClientInfoEntry.COLUMN_APP_ID + " =? ",
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            // for level_rule
            case CODE_LEVEL_RULE: {
                cursor = hurryPushDbHelper.getReadableDatabase().query(
                        HurryPushContract.LevelRuleEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case CODE_LEVEL_RULE_WITH_LEVEL_ID: {
                cursor = hurryPushDbHelper.getReadableDatabase().query(
                        HurryPushContract.LevelRuleEntry.TABLE_NAME,
                        projection,
                        HurryPushContract.LevelRuleEntry.COLUMN_LEVEL_ID + " =? ",
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            // for defecation_record
            case CODE_DEFECATION_RECORD: {
                cursor = hurryPushDbHelper.getReadableDatabase().query(
                        HurryPushContract.DefecationRecordEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case CODE_DEFECATION_RECORD_WITH_INSERT_TIME: {
                cursor = hurryPushDbHelper.getReadableDatabase().query(
                        HurryPushContract.DefecationRecordEntry.TABLE_NAME,
                        projection,
                        HurryPushContract.DefecationRecordEntry.COLUMN_INSERT_TIME + " between ? and ? ",
                        selectionArgs,
                        null,
                        null,
                        sortOrder,
                        "20"
                );
                break;
            }
            case CODE_DEFECATION_RECORD_BY_10: {
                cursor = hurryPushDbHelper.getReadableDatabase().query(
                        HurryPushContract.DefecationRecordEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder,
                        "10"
                );
                break;
            }

            case CODE_ACHIEVEMENT: {
                cursor = hurryPushDbHelper.getReadableDatabase().query(
                        HurryPushContract.AchievementProgressEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new RuntimeException("We are not implementing getType in HurryPush.");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long numRowInserted;
        switch (uriMatcher.match(uri)) {
            case CODE_DEFECATION_RECORD: {
                numRowInserted = hurryPushDbHelper.getWritableDatabase().insert(
                        HurryPushContract.DefecationRecordEntry.TABLE_NAME,
                        null,
                        values
                );
                if (numRowInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                    return uri.buildUpon().appendQueryParameter("insertedId",numRowInserted+"").build();
                } else {
                    return null;
                }
            }
            default:
                throw new UnsupportedOperationException("This uri is not supported to delete: " + uri);
        }
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = hurryPushDbHelper.getWritableDatabase();

        switch (uriMatcher.match(uri)) {
            case CODE_CLIENT_INFO_ONLY_ONE: {
                db.beginTransaction();
                int rowsInserted = 0;
                try {

                    for (ContentValues value : values) {
                        long _id = db.insert(
                                HurryPushContract.ClientInfoEntry.TABLE_NAME,
                                null,
                                value
                        );

                        if (_id != -1) {
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                if (rowsInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                return rowsInserted;
            }
            case CODE_LEVEL_RULE: {
                db.beginTransaction();
                int rowsInserted = 0;
                try {

                    for (ContentValues value : values) {
                        long _id = db.insert(
                                HurryPushContract.LevelRuleEntry.TABLE_NAME,
                                null,
                                value
                        );

                        if (_id != -1) {
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                if (rowsInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                return rowsInserted;
            }
            case CODE_DEFECATION_RECORD: {
                db.beginTransaction();
                int rowsInserted = 0;
                try {

                    for (ContentValues value : values) {
                        long _id = db.insert(
                                HurryPushContract.DefecationRecordEntry.TABLE_NAME,
                                null,
                                value
                        );

                        if (_id != -1) {
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                if (rowsInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                return rowsInserted;
            }
            case CODE_ACHIEVEMENT: {
                db.beginTransaction();
                int rowsInserted = 0;
                try {

                    for (ContentValues value : values) {
                        long _id = db.insert(
                                HurryPushContract.AchievementProgressEntry.TABLE_NAME,
                                null,
                                value
                        );

                        if (_id != -1) {
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                if (rowsInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                return rowsInserted;
            }
            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int numRowDeleted;
        Log.d(LOG_TAG,"match uri: " + uri.toString());
        Log.d(LOG_TAG,"match code: " + uriMatcher.match(uri));
        switch (uriMatcher.match(uri)) {
            case CODE_CLIENT_INFO_ONLY_ONE: {
                numRowDeleted = hurryPushDbHelper.getWritableDatabase().delete(
                        HurryPushContract.ClientInfoEntry.TABLE_NAME,
                        selection,
                        selectionArgs
                );
                break;
            }
            case CODE_DEFECATION_RECORD: {
                numRowDeleted = hurryPushDbHelper.getWritableDatabase().delete(
                        HurryPushContract.DefecationRecordEntry.TABLE_NAME,
                        selection,
                        selectionArgs
                );
                break;
            }
            case CODE_LEVEL_RULE: {
                numRowDeleted = hurryPushDbHelper.getWritableDatabase().delete(
                        HurryPushContract.LevelRuleEntry.TABLE_NAME,
                        selection,
                        selectionArgs
                );
                break;
            }
            case CODE_ACHIEVEMENT: {
                numRowDeleted = hurryPushDbHelper.getWritableDatabase().delete(
                        HurryPushContract.AchievementProgressEntry.TABLE_NAME,
                        selection,
                        selectionArgs
                );
                break;
            }
            default:
                throw new UnsupportedOperationException("This uri is not supported to delete: " + uri);
        }
        if (numRowDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return numRowDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int numRowUpdated;
        switch (uriMatcher.match(uri)) {
            case CODE_CLIENT_INFO_ONLY_ONE:
                numRowUpdated = hurryPushDbHelper.getWritableDatabase().update(
                        HurryPushContract.ClientInfoEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs
                );
                break;
            default:
                throw new UnsupportedOperationException("This uri is not supported to update: " + uri);
        }
        if (numRowUpdated != 0) {

        }
        return 0;
    }
}
