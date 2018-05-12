package com.lzjlxebr.hurrypush.db;

import android.net.Uri;
import android.provider.BaseColumns;

public class HurryPushContract {
    public static final String CONTENT_AUTHORITY = "com.lzjlxebr.hurrypush";
    public static final String BASE_CONTENT_URI = "content://" + CONTENT_AUTHORITY;

    public static final class ClientInfoEntry implements BaseColumns {
        public static final String PATH_CLIENT_INFO = "client_info";
        public static final Uri CLIENT_INFO_URI = Uri.parse(BASE_CONTENT_URI).buildUpon().appendPath(PATH_CLIENT_INFO).build();

        public static final String TABLE_NAME = "client_info";

        public static final String COLUMN_APP_ID = "app_id";
        public static final String COLUMN_GENDER = "gender";
        public static final String COLUMN_CURRENT_LEVEL_ID = "current_level_id";
        public static final String COLUMN_CURRENT_EXP = "current_exp";
        public static final String COLUMN_UPGRADE_EXP = "upgrade_exp";
        public static final String COLUMN_IS_FIRST_START = "is_first_start";

    }

    public static final class LevelRuleEntry implements BaseColumns {
        public static final String PATH_LEVEL_RULE = "level_rule";
        public static final Uri LEVEL_RULE_URI = Uri.parse(BASE_CONTENT_URI).buildUpon().appendPath(PATH_LEVEL_RULE).build();

        public static final String TABLE_NAME = "level_rule";

        public static final String COLUMN_LEVEL_ID = "level_id";
        public static final String COLUMN_LEVEL_NUMBER = "level_number";
        public static final String COLUMN_LEVEL_EXP_SINGLE = "level_exp_single";
        public static final String COLUMN_LEVEL_EXP_TOTAL = "level_exp_total";
    }

    public static final class DefecationRecordEntry implements BaseColumns {
        public static final String PATH_DEFECATION_RECORD = "defecation_record";
        public static final Uri DEFECATION_RECORD_URI = Uri.parse(BASE_CONTENT_URI).buildUpon().appendPath(PATH_DEFECATION_RECORD).build();

        public static final String TABLE_NAME = "defecation_record";

        public static final String DEFAULT_SORT_ORDER = "insert_time asc";

        public static final String COLUMN_INSERT_TIME = "insert_time";
        public static final String COLUMN_START_TIME = "start_time";
        public static final String COLUMN_END_TIME = "end_time";
        public static final String COLUMN_IS_USER_FINISH = "is_user_finish";
        public static final String COLUMN_GAIN_EXP = "gain_exp";
        public static final String COLUMN_SMELL = "smell";
        public static final String COLUMN_CONSTIPATION = "constipation";
        public static final String COLUMN_STICKINESS = "stickiness";
        public static final String COLUMN_OVERALL_RATING = "overall_rating";
        public static final String COLUMN_UPLOAD_TO_SERVER = "upload_to_server";
        public static final String COLUMN_SERVER_CALL_BACK = "server_call_back";
    }

    public static final class AchievementProgressEntry implements BaseColumns {
        public static final String PATH_ACHIEVEMENT_PROGRESS = "achievement_progress";
        public static final Uri ACHIEVEMENT_PROGRESS_URI = Uri.parse(BASE_CONTENT_URI).buildUpon().appendPath(PATH_ACHIEVEMENT_PROGRESS).build();

        public static final String TABLE_NAME = "achievement_progress";

        public static final String DEFAULT_SORT_ORDER = "achi_id asc";

        public static final String COLUMN_ACHI_TYPE = "achi_type";
        public static final String COLUMN_ACHI_REQUIRED_MINUTES = "achi_required_minutes";
        public static final String COLUMN_ACHI_REQUIRED_DAYS = "achi_required_days";
        public static final String COLUMN_ACHI_ID = "achi_id";
        public static final String COLUMN_ACHI_NAME = "achi_name";
        public static final String COLUMN_ACHI_CONDITION = "achi_condition";
        public static final String COLUMN_ACHI_PROGRESS = "achi_progress";
        public static final String COLUMN_UPDATE_TIME = "update_time";
        public static final String COLUMN_ACHI_DESCRIPTION = "achi_description";
    }
}
