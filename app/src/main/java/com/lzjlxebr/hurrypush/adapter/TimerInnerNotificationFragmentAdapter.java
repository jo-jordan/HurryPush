package com.lzjlxebr.hurrypush.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lzjlxebr.hurrypush.R;
import com.lzjlxebr.hurrypush.entity.TimerInnerNotificationEntry;

import java.util.ArrayList;
import java.util.List;

public class TimerInnerNotificationFragmentAdapter extends
        RecyclerView.Adapter<TimerInnerNotificationFragmentAdapter
                .TimerInnerNotificationFragmentAdapterViewHolder> {

    private static final String LOG_TAG = "TimerInnerNotiAdapter";

    public List<TimerInnerNotificationEntry> mDataSet;

    static class TimerInnerNotificationFragmentAdapterViewHolder extends RecyclerView.ViewHolder{
        final TextView mTitle;
        final TextView mContent;

        TimerInnerNotificationFragmentAdapterViewHolder(View view) {
            super(view);
            mTitle = view.findViewById(R.id.tv_title_inner_notification);
            mContent = view.findViewById(R.id.tv_content_inner_notification);
        }
    }

    public TimerInnerNotificationFragmentAdapter() {
    }

    @NonNull
    @Override
    public TimerInnerNotificationFragmentAdapter.TimerInnerNotificationFragmentAdapterViewHolder
    onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.timer_inner_notification_list_items, parent, false);
        return new TimerInnerNotificationFragmentAdapterViewHolder(view);
    }

    @Override
    public void
    onBindViewHolder(@NonNull TimerInnerNotificationFragmentAdapter
            .TimerInnerNotificationFragmentAdapterViewHolder holder, int position) {

        String title = mDataSet.get(position).getmTitle();
        String content = mDataSet.get(position).getmContent();
        int mTime = mDataSet.get(position).getmTime();

        Log.d(LOG_TAG,"onBindView: " + title);


        switch (mTime) {
            case 3:
                holder.mTitle.setText(title);
                holder.mContent.setText(content);
                break;
            case 5:
                holder.mContent.setText(content);
                holder.mTitle.setText(title);
                break;
            case 7:
                holder.mContent.setText(content);
                holder.mTitle.setText(title);
                break;
            case 10:
                holder.mContent.setText(content);
                holder.mTitle.setText(title);
                break;
            case 30:
                holder.mContent.setText(content);
                holder.mTitle.setText(title);
                break;
            case 60:
                holder.mContent.setText(content);
                holder.mTitle.setText(title);
                break;
            default:
                holder.mContent.setText(content);
        }

        holder.itemView.setTag(position);
        Log.d(LOG_TAG,"time point: " + mTime + ", set tag: " + position);
    }



    @Override
    public int getItemCount() {
        if (mDataSet == null)
            return 0;
        return mDataSet.size();
    }


    public void addItem(TimerInnerNotificationEntry data, int index) {
        if (mDataSet == null){
            mDataSet = new ArrayList<>();
        }

        mDataSet.add(index,data);

        Log.d(LOG_TAG,"add item index: " + index);
    }
    public void deleteItem(int index) {



        int id = 0;

        mDataSet.remove(id);
        //mDataSet.set(id,null);

        Log.d(LOG_TAG,"remove item index: " + index + ", now list size: " + mDataSet.size() + ", now adapter item count: " + getItemCount());
    }
}


