package com.lzjlxebr.hurrypush.ui.base;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceCategory;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.util.Log;

import com.lzjlxebr.hurrypush.R;
import com.lzjlxebr.hurrypush.service.HurryPushSyncUtils;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String LOG_TAG = SettingsFragment.class.getSimpleName();

    private void setPreferenceSummary(Preference preference, Object value) {
        String stringValue = value.toString();
        //String key = preference.getKey();

        if (preference instanceof ListPreference) {
            /* For list preferences, look up the correct display value in */
            /* the preference's 'entries' list (since they have separate labels/values). */
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else {
            // For other preferences, set the summary to the value's simple string representation.

            preference.setSummary(stringValue);
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        Log.d(LOG_TAG, "onCreatePreferences");

        setPreferencesFromResource(R.xml.pref_general, rootKey);
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        SharedPreferences sharedPreferences = preferenceScreen.getSharedPreferences();

        int count = preferenceScreen.getPreferenceCount();

        for (int i = 0; i < count; i++) {
            Preference p = preferenceScreen.getPreference(i);

            Log.d(LOG_TAG, "Preference is a: " + p.getClass().getSimpleName());
            if (p instanceof EditTextPreference) {
                String value = sharedPreferences.getString(p.getKey(), getString(R.string.client_nick_name_default_value));
                setPreferenceSummary(p, value);
                Log.d(LOG_TAG, "on create preference str value: " + value);
            }
            if (p instanceof PreferenceCategory) {
                int preferenceCount = ((PreferenceCategory) p).getPreferenceCount();
                Log.d(LOG_TAG, "preferenceCountcount: " + preferenceCount);
                for (int x = 0; x < preferenceCount; x++) {
                    Preference preference = ((PreferenceCategory) p).getPreference(x);
                    if (preference instanceof EditTextPreference) {
                        String value = sharedPreferences.getString(preference.getKey(), getString(R.string.client_nick_name_default_value));
                        setPreferenceSummary(preference, value);
                    }else if (preference.getKey().equals(getString(R.string.open_sync_service_key))) {
                        preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                            @Override
                            public boolean onPreferenceClick(Preference preference) {
                                HurryPushSyncUtils.startImmediateUpdateSync(getActivity());
                                return false;
                            }
                        });
                    }
                }
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.d(LOG_TAG, "onSharedPreferenceChanged");
        Preference preference = findPreference(key);
        if (null != preference) {
            if (!(preference instanceof CheckBoxPreference)) {
                setPreferenceSummary(preference, sharedPreferences.getString(key, getString(R.string.client_nick_name_default_value)));
            }
        }
    }


    @Override
    public void onStart() {
        Log.d(LOG_TAG, "onStart");
        super.onStart();
        /* Register the preference change listener */
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStop() {
        Log.d(LOG_TAG, "onStop");
        super.onStop();
        /* Unregister the preference change listener */
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}
