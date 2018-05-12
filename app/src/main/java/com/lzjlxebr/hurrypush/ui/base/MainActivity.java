package com.lzjlxebr.hurrypush.ui.base;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.lzjlxebr.hurrypush.R;
import com.lzjlxebr.hurrypush.adapter.MainActivityAdapter;
import com.lzjlxebr.hurrypush.db.HurryPushContract;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener, LoaderManager.LoaderCallbacks<Cursor> {

    private final String LOG_TAG = MainActivity.class.getSimpleName();

    private static final int CLIENT_INFO_LODER_ID = 1001;
    public static final int INDEX_COLUMN_IS_FIRST_START = 5;

    public static final int INDEX_COLUMN_APP_ID = 0;
    public static final int INDEX_COLUMN_GENDER = 1;
    public static final int INDEX_COLUMN_CURRENT_LEVEL_ID = 2;
    public static final int INDEX_COLUMN_CURRENT_EXP = 3;
    public static final int INDEX_COLUMN_UPGRADE_EXP = 4;
    public static String[] TODAY_PROJECTION = {
            HurryPushContract.ClientInfoEntry.COLUMN_APP_ID,
            HurryPushContract.ClientInfoEntry.COLUMN_GENDER,
            HurryPushContract.ClientInfoEntry.COLUMN_CURRENT_LEVEL_ID,
            HurryPushContract.ClientInfoEntry.COLUMN_CURRENT_EXP,
            HurryPushContract.ClientInfoEntry.COLUMN_UPGRADE_EXP,
            HurryPushContract.ClientInfoEntry.COLUMN_IS_FIRST_START
    };
    @BindString(R.string.first_time_title)
    String title;
    @BindString(R.string.first_time_content)
    String content;
    @BindString(R.string.first_time_agree)
    String agree;
    @BindString(R.string.first_time_disagree)
    String disagree;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    private final Context mContext = this;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    // tool bar
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private MainActivityAdapter mainFragmentAdapter;

    private TabLayout.Tab one;
    private TabLayout.Tab two;
    private TabLayout.Tab three;
    //navigation menu
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    //ActionBarDrawerToggle mDrawerToggle;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    //nav header info
    TextView mNickName;
    View headerLayout;
    TextView mCurrentLevel;
    ProgressBar mExpIndicator;
    TextView mCurrentLevelRate;
    // fab
    @BindView(R.id.go_to_fly_fam)
    FloatingActionMenu mFloatingActionMenu;
    @BindView(R.id.go_to_fly_fab)
    FloatingActionButton startFly;
    private Cursor mCursor;
    private int isFirst = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        getSupportLoaderManager().initLoader(CLIENT_INFO_LODER_ID, null, this);

        ButterKnife.bind(this);


        initNavMenu();

        initToolBar();
        initNavTop();

        initFAB();
        setupSharedPreference();


        //checkFirstTimeStart();
    }

    public void initFAB() {
//        mFloatingActionMenu = findViewById(R.id.go_to_fly_fam);
//        startFly = findViewById(R.id.go_to_fly_fab);

        startFly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startStartFlyActivity = new Intent(mContext, StartFlyActivity.class);
                startActivity(startStartFlyActivity);
            }
        });
    }

    public void setupSharedPreference() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String nickNameDefault = sharedPreferences.getString(getString(R.string.client_nick_name_key), getString(R.string.client_nick_name_default_value));

        if ("点这里换个称号".equalsIgnoreCase(nickNameDefault))
            nickNameDefault = "去设置里面换个名字吧";

        Log.d(LOG_TAG, "this is nick name: " + nickNameDefault);
        mNickName.setText(nickNameDefault);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    private void initNavTop() {
        mainFragmentAdapter = new MainActivityAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mainFragmentAdapter);

        mTabLayout.setupWithViewPager(mViewPager);

        one = mTabLayout.getTabAt(0);
        two = mTabLayout.getTabAt(1);
        three = mTabLayout.getTabAt(2);

        one.setIcon(R.drawable.ic_today);
        two.setIcon(R.drawable.ic_thumb_head_unselected);
        three.setIcon(R.drawable.ic_pie_chart_unselected);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.equals(one)) {
                    one.setIcon(R.drawable.ic_today);
                    two.setIcon(R.drawable.ic_thumb_head_unselected);
                    three.setIcon(R.drawable.ic_pie_chart_unselected);
                    mFloatingActionMenu.showMenuButton(true);
                }
                if (tab.equals(two)) {
                    two.setIcon(R.drawable.ic_thumb_head);
                    one.setIcon(R.drawable.ic_today_unselected);
                    three.setIcon(R.drawable.ic_pie_chart_unselected);
                    mFloatingActionMenu.hideMenuButton(true);
                }
                if (tab.equals(three)) {
                    three.setIcon(R.drawable.ic_pie_chart);
                    one.setIcon(R.drawable.ic_today_unselected);
                    two.setIcon(R.drawable.ic_thumb_head_unselected);
                    mFloatingActionMenu.hideMenuButton(true);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    // initialize the nav drawer
    public void initNavMenu() {
        headerLayout = mNavigationView.getHeaderView(0);
        mNickName = headerLayout.findViewById(R.id.nav_header_nick_name);

        final Context context = this;

        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.nav_settings:
                                Intent startSettingsActivity = new Intent(context, SettingsActivity.class);
                                mDrawerLayout.closeDrawers();
                                startActivity(startSettingsActivity);
                                return true;
                            default:
                                Intent startSettingsActivity1 = new Intent(context, SettingsActivity.class);
                                startActivity(startSettingsActivity1);
                                mDrawerLayout.closeDrawers();
                                return true;
                        }
                    }
                });
    }

    // set toolbar as actionbar
    public void initToolBar() {
        // = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    //inflate menu for main activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if (mDrawerToggle.onOptionsItemSelected(item))
//            return true;
        int id = item.getItemId();
        switch (id) {
            // open settings activity
            case R.id.menu_action_settings:
                //Toast.makeText(this, "This is menu.", Toast.LENGTH_LONG).show();
                Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
                startActivity(startSettingsActivity);
                return true;
            // open nav drawer
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.d(LOG_TAG, "preference changed.");
        String nickNameDefault = sharedPreferences.getString(getString(R.string.client_nick_name_key), getString(R.string.client_nick_name_default_value));
        if (key.equals(getString(R.string.client_nick_name_key))) {
            mNickName.setText(nickNameDefault);

            Log.d(LOG_TAG, "preference nick name has been changed to: " + nickNameDefault);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister VisualizerActivity as an OnPreferenceChangedListener to avoid any memory leaks.
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        Log.d(LOG_TAG, "Create Loader");
        switch (id) {
            case CLIENT_INFO_LODER_ID: {
                Uri clientInfoUri = HurryPushContract.ClientInfoEntry.CLIENT_INFO_URI;

                return new CursorLoader(
                        this,
                        clientInfoUri,
                        TODAY_PROJECTION,
                        null,
                        new String[]{"1"},
                        "_id"
                );
            }
            default:
                throw new RuntimeException("Loader" + id);
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        swapCursor(data);
        Log.d(LOG_TAG, "Load Finished");

        initData();
        checkFirstTimeStart();
    }

    private void initData() {

        int currentLevelId = 1;
        int currentLevelExp = 0;
        int upgradeExp = 0;

        if (mCursor.getCount() > 0) {
            mCursor.moveToFirst();
            currentLevelId = mCursor.getInt(INDEX_COLUMN_CURRENT_LEVEL_ID);
            currentLevelExp = mCursor.getInt(INDEX_COLUMN_CURRENT_EXP);
            upgradeExp = mCursor.getInt(INDEX_COLUMN_UPGRADE_EXP);
            isFirst = mCursor.getInt(INDEX_COLUMN_IS_FIRST_START);

            Log.d(LOG_TAG, "is first time ? : " + isFirst);
        }

        double rate = Math.round((currentLevelExp * 100.0) / (upgradeExp * 100.0)) / 100.0;
        String rateStr = rate + "%";

        String currentLevelText = "等级: " + currentLevelId;


        mCurrentLevel = headerLayout.findViewById(R.id.nav_header_current_level);
        mExpIndicator = headerLayout.findViewById(R.id.nav_header_current_exp);
        mCurrentLevelRate = headerLayout.findViewById(R.id.nav_header_current_level_rate);

        mCurrentLevel.setText(currentLevelText);
        mExpIndicator.setMax(upgradeExp);
        mExpIndicator.setProgress(currentLevelExp);
        mCurrentLevelRate.setText(rateStr);
    }

    private void swapCursor(Cursor cursor) {
        mCursor = cursor;
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        swapCursor(null);
    }


    private void checkFirstTimeStart() {
        if (isFirst == 1)
            setNickNameAtFirstTime();
    }

    private void setNickNameAtFirstTime() {

        final Context context = this;

        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(title)
                .positiveText(agree)
                .input("名字想多长就多长", null, false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        // Do something
                    }
                })
                .inputRange(1, 20, 0xB9E90D0D)
                .buttonsGravity(GravityEnum.CENTER)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        String nickName = dialog.getInputEditText().getText().toString();
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                        sharedPreferences.edit().putString(getString(R.string.client_nick_name_key), nickName).apply();
                        Log.d(LOG_TAG, "mFirstTime nick name: " + nickName);
                        setClientAlreadyStarted();
                    }
                })
                .show();
    }

    private void setClientAlreadyStarted() {
        int is_first_start = 0;
        String app_id = "b0fb3e80-82ed-44c5-aaaa-824bb129efe7";
        Uri updateArreadyStartedUri = HurryPushContract.ClientInfoEntry.CLIENT_INFO_URI;

        ContentValues contentValue = new ContentValues();
        contentValue.put(HurryPushContract.ClientInfoEntry.COLUMN_IS_FIRST_START, is_first_start);

        ContentResolver contentResolver = this.getContentResolver();
        int updateFlag = contentResolver.update(updateArreadyStartedUri, contentValue, null, new String[]{"1"});

        if (updateFlag == 0) {
            int gender = 1;
            int current_level_id = 3;
            int current_level_exp = 300;
            int upgrade_exp = 300;

            ContentValues contentValue_insert = new ContentValues();
            contentValue_insert.put(HurryPushContract.ClientInfoEntry.COLUMN_APP_ID, app_id);
            contentValue_insert.put(HurryPushContract.ClientInfoEntry.COLUMN_GENDER, gender);
            contentValue_insert.put(HurryPushContract.ClientInfoEntry.COLUMN_CURRENT_LEVEL_ID, current_level_id);
            contentValue_insert.put(HurryPushContract.ClientInfoEntry.COLUMN_CURRENT_EXP, current_level_exp);
            contentValue_insert.put(HurryPushContract.ClientInfoEntry.COLUMN_UPGRADE_EXP, upgrade_exp);
            contentValue_insert.put(HurryPushContract.ClientInfoEntry.COLUMN_IS_FIRST_START, is_first_start);
            Uri insertedRoewId = contentResolver.insert(updateArreadyStartedUri, contentValue_insert);

            Log.d(LOG_TAG, "has inserted a row uri: " + insertedRoewId);
            Log.d(LOG_TAG, "cursor is null ? : " + (mCursor == null));
        }
        Log.d(LOG_TAG, "cursor is null ? : " + (mCursor == null));


    }
}
