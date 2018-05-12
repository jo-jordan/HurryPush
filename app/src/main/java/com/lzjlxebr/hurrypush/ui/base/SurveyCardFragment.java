package com.lzjlxebr.hurrypush.ui.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzjlxebr.hurrypush.R;
import com.lzjlxebr.hurrypush.adapter.CardAdapter;
import com.lzjlxebr.hurrypush.entity.SurveyEntry;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SurveyCardFragment extends SurveyCardAbstractFragment {
    private static final int GOOD = 0;
    private static final int OK = 1;
    private static final int BAD = 2;
    @BindView(R.id.survey_card_view)
    CardView mCardView;
    @BindView(R.id.tv_card_title)
    TextView mTvCardTitle;
    @BindView(R.id.tv_card_content)
    TextView mTvCardContent;
    @BindString(R.string.card_title1)
    String title1;
    @BindString(R.string.card_title2)
    String title2;
    @BindString(R.string.card_title3)
    String title3;
    @BindString(R.string.card_content1)
    String content1;
    @BindString(R.string.card_content2)
    String content2;
    @BindString(R.string.card_content3)
    String content3;
    @BindView(R.id.survey_done)
    ImageView mTick;
    private int position;


    @Override
    public void setCardTitleAndContent() {
        String tag = getArguments().getString("card_fragment");

        if ("crad_fragment_0".equals(tag)) {
            mTvCardTitle.setText(title1);
            mTvCardContent.setText(content1);
        }
        if ("crad_fragment_1".equals(tag)) {
            mTvCardTitle.setText(title2);
            mTvCardContent.setText(content2);
        }
        if ("crad_fragment_2".equals(tag)) {
            mTvCardTitle.setText(title3);
            mTvCardContent.setText(content3);
        }
    }

    private SurveyEntry mSurveyEntry;


    public void setSmell(int smell) {
        SurveyActivity activity = ((SurveyActivity) getActivity());
        activity.setSmell(smell);

        //((SurveyActivity) getActivity()).getSurveyEntry().setSmell(smell);
    }

    public void setConstipation(int constipation) {
        SurveyActivity activity = ((SurveyActivity) getActivity());
        activity.setConstipation(constipation);

        //((SurveyActivity) getActivity()).getSurveyEntry().setConstipation(constipation);
    }

    public void setStickiness(int stickiness) {
        SurveyActivity activity = ((SurveyActivity) getActivity());
        activity.setStickiness(stickiness);

        //((SurveyActivity) getActivity()).getSurveyEntry().setStickiness(stickiness);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_survey_card, container, false);
        ButterKnife.bind(this, view);
        setCardTitleAndContent();
        mCardView.setMaxCardElevation(mCardView.getCardElevation()
                * CardAdapter.MAX_ELEVATION_FACTOR);
        return view;
    }

    @Override
    public CardView getCardView() {
        return mCardView;
    }

    @OnClick(R.id.button_good)
    public void setSurveyGoodResults(Button button) {
        String tag = getArguments().getString("card_fragment");

        if ("crad_fragment_0".equals(tag)) {
            setSmell(GOOD);
        }
        if ("crad_fragment_1".equals(tag)) {
            setConstipation(GOOD);
        }
        if ("crad_fragment_2".equals(tag)) {
            setStickiness(GOOD);
        }
        String msg = ((SurveyActivity) getActivity()).getSurveyEntry().toString();
        Log.d("SurveyCard", msg);
        mTick.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.button_ok)
    public void setSurveyOkResults(Button button) {
        String tag = getArguments().getString("card_fragment");
        if ("crad_fragment_0".equals(tag)) {
            setSmell(OK);
        }
        if ("crad_fragment_1".equals(tag)) {
            setConstipation(OK);
        }
        if ("crad_fragment_2".equals(tag)) {
            setStickiness(OK);
        }
        String msg = ((SurveyActivity) getActivity()).getSurveyEntry().toString();
        Log.d("SurveyCard", msg);
        mTick.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.button_bad)
    public void setSurveyBadResults(Button button) {
        String tag = getArguments().getString("card_fragment");
        if ("crad_fragment_0".equals(tag)) {
            setSmell(BAD);
        }
        if ("crad_fragment_1".equals(tag)) {
            setConstipation(BAD);
        }
        if ("crad_fragment_2".equals(tag)) {
            setStickiness(BAD);
        }
        String msg = ((SurveyActivity) getActivity()).getSurveyEntry().toString();
        Log.d("SurveyCard", msg);
        mTick.setVisibility(View.VISIBLE);
    }
}
