package com.lzjlxebr.hurrypush.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lzjlxebr.hurrypush.R;
import com.lzjlxebr.hurrypush.adapter.CardAdapter;
import com.lzjlxebr.hurrypush.db.HurryPushContract;
import com.lzjlxebr.hurrypush.entity.DefecationFinalRecord;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SurveyResultCardFragment extends SurveyCardAbstractFragment {
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
    @BindView(R.id.survey_card_view)
    CardView mCardView;
    @BindView(R.id.tv_final_card_title)
    TextView mTvCardTitle;
    @BindView(R.id.tv_final_card_content)
    TextView mTvCardContent;
    @BindString(R.string.card_content5)
    String content;
    @BindString(R.string.card_title5)
    String title;
    DefecationFinalRecord defecationFinalRecord;

    @Override
    public void setCardTitleAndContent() {
        String tag = getArguments().getString("card_fragment");

        if ("crad_fragment_5".equals(tag)) {
            mTvCardTitle.setText(title);
        }
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_survey_result_card, container, false);
        ButterKnife.bind(this, view);
        //mCardView = view.findViewById(R.id.survey_card_view);

        EventBus.getDefault().register(this);

        setCardTitleAndContent();

        mCardView.setMaxCardElevation(mCardView.getCardElevation()
                * CardAdapter.MAX_ELEVATION_FACTOR);
        return view;
    }

    @Override
    public CardView getCardView() {
        return mCardView;
    }

    @OnClick(R.id.button_survey_done)
    public void submitResult(Button button) {
        Intent intent = new Intent(getContext(), MainActivity.class);

        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventOnMain(DefecationFinalRecord event) {
        if (event instanceof DefecationFinalRecord) {
            double gainExp = event.getGainExp();
            double ratting = event.getOverallRatting();
            String content = "获得经验值：" + gainExp + "\n" + "综合评分：" + ratting;

            mTvCardContent.setText(content);
        }
    }
}
