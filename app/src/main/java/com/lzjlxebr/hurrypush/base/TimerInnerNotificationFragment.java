package com.lzjlxebr.hurrypush.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzjlxebr.hurrypush.R;
import com.lzjlxebr.hurrypush.adapter.TimerInnerNotificationFragmentAdapter;
import com.lzjlxebr.hurrypush.entity.EmptyEvent;
import com.lzjlxebr.hurrypush.entity.TimerInnerNotificationEntry;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class TimerInnerNotificationFragment extends Fragment{

    private static final String LOG_TAG = "NotificationFragment";

    private TimerInnerNotificationFragmentAdapter mTimerInnerNotificationFragmentAdapter;
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer_inner_notification, container, false);
        EventBus.getDefault().register(this);

        mRecyclerView = view.findViewById(R.id.timer_inner_notification_recycler_view);

        mTimerInnerNotificationFragmentAdapter = new TimerInnerNotificationFragmentAdapter();

        initRecyclerView();

        return view;
    }

    public void initRecyclerView(){
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,true));
        mRecyclerView.setAdapter(mTimerInnerNotificationFragmentAdapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int id = (int) viewHolder.itemView.getTag();
                mTimerInnerNotificationFragmentAdapter.deleteItem(id);
                mTimerInnerNotificationFragmentAdapter.notifyItemRemoved(id);
                mTimerInnerNotificationFragmentAdapter.notifyItemChanged(id);
                //mRecyclerView.scrollToPosition(mTimerInnerNotificationFragmentAdapter.getItemCount());

            }
        }).attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private int mTime = 0;


    @Subscribe(threadMode = ThreadMode.MAIN,priority = 0)
    public void onEventMain(EmptyEvent event) {
        if (event instanceof TimerInnerNotificationEntry) {
            int newTime = ((TimerInnerNotificationEntry) event).getmTime();

            if (newTime != mTime){
                mTime = newTime;
            }else {
                return;
            }

            TimerInnerNotificationEntry entry = new TimerInnerNotificationEntry(((TimerInnerNotificationEntry) event).getmTitle(),((TimerInnerNotificationEntry) event).getmContent(),mTime);

            addItemCard(entry);
        }
    }

    public void addItemCard(TimerInnerNotificationEntry entry){
        Log.d(LOG_TAG, "NOTIFICATION_FRAGMENT GET THE EVENT: " + entry.getmContent() + ", and adpter is null ? " + (mTimerInnerNotificationFragmentAdapter == null));
        int count = mTimerInnerNotificationFragmentAdapter.getItemCount();



        mTimerInnerNotificationFragmentAdapter.addItem(entry, count);

        mTimerInnerNotificationFragmentAdapter.notifyItemInserted(count);
        mTimerInnerNotificationFragmentAdapter.notifyItemChanged(count);

        mRecyclerView.scrollToPosition(mTimerInnerNotificationFragmentAdapter.getItemCount());
    }
}
