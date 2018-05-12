package com.lzjlxebr.hurrypush.ui.base;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzjlxebr.hurrypush.R;
import com.lzjlxebr.hurrypush.adapter.CardAdapter;
import com.lzjlxebr.hurrypush.db.HurryPushContract;
import com.lzjlxebr.hurrypush.entity.DefecationEvent;
import com.lzjlxebr.hurrypush.entity.DefecationFinalRecord;
import com.lzjlxebr.hurrypush.entity.SurveyEntry;
import com.lzjlxebr.hurrypush.util.BillCalculator;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SurveyFinalCardFragment extends SurveyCardAbstractFragment {
    @BindView(R.id.survey_card_view)
    CardView mCardView;

    @BindView(R.id.tv_final_card_title)
    TextView mTvCardTitle;

    @BindView(R.id.tv_final_card_content)
    TextView mTvCardContent;

    @BindView(R.id.survey_done)
    ImageView mTick;


    @BindString(R.string.card_content4)
    String content4;

    @BindString(R.string.card_title4)
    String title4;

    public static final int INDEX_COLUMN_LEVEL_ID = 0;
    public static final int INDEX_COLUMN_LEVEL_NUMBER = 1;
    public static final int INDEX_COLUMN_LEVEL_EXP_SINGLE = 2;
    public static final int INDEX_COLUMN_LEVEL_EXP_TOTAL = 3;
    public static String[] LEVEL_RULE_PROJECTION = {
            HurryPushContract.LevelRuleEntry.COLUMN_LEVEL_ID,
            HurryPushContract.LevelRuleEntry.COLUMN_LEVEL_NUMBER,
            HurryPushContract.LevelRuleEntry.COLUMN_LEVEL_EXP_SINGLE,
            HurryPushContract.LevelRuleEntry.COLUMN_LEVEL_EXP_TOTAL,
    };

    @Override
    public void setCardTitleAndContent() {
        String tag = getArguments().getString("card_fragment");

        if ("crad_fragment_3".equals(tag)) {
            mTvCardTitle.setText(title4);
            mTvCardContent.setText(content4);
        }
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_survey_final_card, container, false);
        ButterKnife.bind(this, view);
        //mCardView = view.findViewById(R.id.survey_card_view);

        setCardTitleAndContent();

        mCardView.setMaxCardElevation(mCardView.getCardElevation()
                * CardAdapter.MAX_ELEVATION_FACTOR);
        return view;
    }

    @Override
    public CardView getCardView() {
        return mCardView;
    }

    DefecationFinalRecord defecationFinalRecord;

    @OnClick(R.id.button_survey_done)
    public void submitResult(Button button) {
        SurveyActivity activity = (SurveyActivity) getActivity();

        SurveyEntry surveyEntry = activity.getSurveyEntry();
        DefecationEvent defecationEvent = activity.getDefecationEvent();

        defecationFinalRecord = BillCalculator.calculate(surveyEntry, defecationEvent);

        updateDefecationDataTable(defecationFinalRecord);
        updateExp();

    }

    private void updateDefecationDataTable(DefecationFinalRecord defecationFinalRecord) {

        long id = defecationFinalRecord.getId();
        int smell = defecationFinalRecord.getSmell();
        int constipation = defecationFinalRecord.getConstipation();
        int stickiness = defecationFinalRecord.getStickiness();
        int isUserFinishde = defecationFinalRecord.getIsUserFinished();
        double gainExp = defecationFinalRecord.getGainExp();
        double ratting = defecationFinalRecord.getOverallRatting();

        ContentValues contentValues = new ContentValues();
        //contentValues.put(HurryPushContract.DefecationRecordEntry.COLUMN_INSERT_TIME, Calendar.getInstance().getTimeInMillis());
        //contentValues.put(HurryPushContract.DefecationRecordEntry.COLUMN_START_TIME,mStartTimeInMillis);
        //contentValues.put(HurryPushContract.DefecationRecordEntry.COLUMN_END_TIME,mEndTimeInMillis);
        contentValues.put(HurryPushContract.DefecationRecordEntry.COLUMN_CONSTIPATION, constipation);
        contentValues.put(HurryPushContract.DefecationRecordEntry.COLUMN_STICKINESS, stickiness);
        contentValues.put(HurryPushContract.DefecationRecordEntry.COLUMN_SMELL, smell);
        contentValues.put(HurryPushContract.DefecationRecordEntry.COLUMN_GAIN_EXP, gainExp);
        contentValues.put(HurryPushContract.DefecationRecordEntry.COLUMN_OVERALL_RATING, ratting);
        //contentValues.put(HurryPushContract.DefecationRecordEntry.COLUMN_SERVER_CALL_BACK,0);
        contentValues.put(HurryPushContract.DefecationRecordEntry.COLUMN_IS_USER_FINISH, isUserFinishde);
        //contentValues.put(HurryPushContract.DefecationRecordEntry.COLUMN_UPLOAD_TO_SERVER,0);

        Uri updateUri = HurryPushContract.DefecationRecordEntry.DEFECATION_RECORD_URI;
        this.getActivity().getContentResolver().update(updateUri, contentValues, "_id=?", new String[]{"" + id});
    }

    private void updateExp() {
        // get current level and exp and upgrade exp
        Uri clienInfoUri = HurryPushContract.ClientInfoEntry.CLIENT_INFO_URI;
        Uri levelRuleUri = HurryPushContract.LevelRuleEntry.LEVEL_RULE_URI;

        Cursor cursorClient = this.getActivity().getContentResolver().query(
                clienInfoUri, MainActivity.TODAY_PROJECTION, null, new String[]{"1"}, null
        );

        if (cursorClient.getCount() > 0) {
            cursorClient.moveToFirst();
            int currentLevelId = cursorClient.getInt(MainActivity.INDEX_COLUMN_CURRENT_LEVEL_ID);
            int currentLevelExp = cursorClient.getInt(MainActivity.INDEX_COLUMN_CURRENT_EXP);
            int upgradeExp = cursorClient.getInt(MainActivity.INDEX_COLUMN_UPGRADE_EXP);

            cursorClient.close();

            double gainExp = defecationFinalRecord.getGainExp();

            double afterGainExp = currentLevelExp + gainExp;

            double diff = upgradeExp - afterGainExp;

            if (diff > 0) {
                // 没达到升级要求经验值
                ContentValues updateClientNoGainValues = new ContentValues();
                updateClientNoGainValues.put(HurryPushContract.ClientInfoEntry.COLUMN_CURRENT_EXP, afterGainExp);
                getActivity().getContentResolver().update(clienInfoUri, updateClientNoGainValues, null, new String[]{"1"});
                //return;
            } else {
                if (currentLevelId >= 10) {
                    //满级
                    return;
                }
                // 达到升级条件，查询下一级需要的经验值，并更新等级及经验
                Cursor cursorLevelRule = getActivity().getContentResolver().query(
                        levelRuleUri, LEVEL_RULE_PROJECTION, null, new String[]{"" + (currentLevelId + 1)}, null
                );
                if (cursorLevelRule != null) {
                    cursorLevelRule.moveToFirst();
                    int levelExpSingle = cursorLevelRule.getInt(INDEX_COLUMN_LEVEL_EXP_SINGLE);
                    int levelId = cursorLevelRule.getInt(INDEX_COLUMN_LEVEL_ID);

                    //更新等级
                    ContentValues updateClientGainValues = new ContentValues();
                    updateClientGainValues.put(HurryPushContract.ClientInfoEntry.COLUMN_CURRENT_EXP, -diff);
                    updateClientGainValues.put(HurryPushContract.ClientInfoEntry.COLUMN_UPGRADE_EXP, levelExpSingle);
                    updateClientGainValues.put(HurryPushContract.ClientInfoEntry.COLUMN_CURRENT_LEVEL_ID, levelId);
                    getActivity().getContentResolver().update(clienInfoUri, updateClientGainValues, null, new String[]{"1"});

                    cursorLevelRule.close();
                }
            }
        } else {
            return;
        }
    }

    private void updateAchievement() {

    }
}
