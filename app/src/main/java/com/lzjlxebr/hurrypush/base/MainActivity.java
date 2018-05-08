package com.lzjlxebr.hurrypush.base;

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
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.lzjlxebr.hurrypush.R;
import com.lzjlxebr.hurrypush.adapter.ClientInfoAdapter;
import com.lzjlxebr.hurrypush.adapter.MainActivityAdapter;
import com.lzjlxebr.hurrypush.db.HurryPushContract;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener, LoaderManager.LoaderCallbacks<Cursor> {

    private final String LOG_TAG = MainActivity.class.getSimpleName();

    private static final int CLIENT_INFO_LODER_ID = 1001;
    public static String[] TODAY_PROJECTION = {
            HurryPushContract.ClientInfoEntry.COLUMN_APP_ID,
            HurryPushContract.ClientInfoEntry.COLUMN_GENDER,
            HurryPushContract.ClientInfoEntry.COLUMN_CURRENT_LEVEL_ID,
            HurryPushContract.ClientInfoEntry.COLUMN_CURRENT_EXP,
            HurryPushContract.ClientInfoEntry.COLUMN_UPGRADE_EXP,
    };

    public static final int INDEX_COLUMN_APP_ID = 0;
    public static final int INDEX_COLUMN_GENDER = 1;
    public static final int INDEX_COLUMN_CURRENT_LEVEL_ID = 2;
    public static final int INDEX_COLUMN_CURRENT_EXP = 3;
    public static final int INDEX_COLUMN_UPGRADE_EXP = 4;

    private final Context mContext = this;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private MainActivityAdapter mainFragmentAdapter;

    private TabLayout.Tab one;
    private TabLayout.Tab two;
    private TabLayout.Tab three;

    //navigation menu
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView mNavigationView;

    //nav header info
    private View headerLayout;
    private TextView mNickName;
    private RecyclerView mRecyclerView;

    // client info adapter
    private ClientInfoAdapter mClientInfoAdapter;
    private int mPosition = RecyclerView.NO_POSITION;

    // fab
    private FloatingActionMenu mFloatingActionMenu;
    private FloatingActionButton startFly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        initNavMenu();

        setupLoader();

        initToolBar();
        initNavTop();

        initFAB();
        setupSharedPreference();


    }

    public void initFAB() {
        mFloatingActionMenu = findViewById(R.id.go_to_fly_fam);
        startFly = findViewById(R.id.go_to_fly_fab);

        startFly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startStartFlyActivity = new Intent(mContext, StartFlyActivity.class);
                startActivity(startStartFlyActivity);
            }
        });
    }

    public void setupLoader() {
        mRecyclerView = headerLayout.findViewById(R.id.client_info_recycler_view);
        mClientInfoAdapter = new ClientInfoAdapter(this);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setAdapter(mClientInfoAdapter);

        getSupportLoaderManager().initLoader(CLIENT_INFO_LODER_ID, null, this);
    }

    public void setupSharedPreference() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String nickNameDefault = sharedPreferences.getString(getString(R.string.client_nick_name_key), getString(R.string.client_nick_name_default_value));

        Log.d(LOG_TAG, "this is nick name: " + nickNameDefault);
        mNickName.setText(nickNameDefault);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    private void initNavTop() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mainFragmentAdapter = new MainActivityAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mainFragmentAdapter);


        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
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
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mNavigationView = findViewById(R.id.nav_view);

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        headerLayout = mNavigationView.getHeaderView(0);

        mNickName = (TextView) headerLayout.findViewById(R.id.nav_header_nick_name);

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
        Toolbar toolbar = findViewById(R.id.toolbar);
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
        if (mDrawerToggle.onOptionsItemSelected(item))
            return true;
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
        switch (id) {
            case CLIENT_INFO_LODER_ID: {
                Uri clientInfoUri = HurryPushContract.ClientInfoEntry.CLIENT_INFO_URI;

                return new CursorLoader(
                        this,
                        clientInfoUri,
                        TODAY_PROJECTION,
                        null,
                        new String[]{"b0fb3e80-82ed-44c5-aaaa-824bb129efe7"},
                        null
                );
            }
            default:
                throw new RuntimeException("Loader Not Implemented" + id);
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mClientInfoAdapter.swapCursor(data);
        if (mPosition == RecyclerView.NO_POSITION) mPosition = 0;
        mRecyclerView.smoothScrollToPosition(mPosition);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mClientInfoAdapter.swapCursor(null);
    }
}
