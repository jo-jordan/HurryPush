package com.lzjlxebr.hurrypush.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;

import com.lzjlxebr.hurrypush.R;
import com.lzjlxebr.hurrypush.entity.Client;

public class HurryPushPreferences  {
    private Client client;

    private final Context mContext;

    public HurryPushPreferences(Context context){
        mContext = context;
    }

    public Client getClient(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        client = new Client();

        String nickNameKey = context.getString(R.string.client_nick_name_key);
        String nickNameDefault = context.getString(R.string.client_nick_name_default_value);

        Log.d("nickName: ", prefs.getString(nickNameKey, nickNameDefault));

        client.setNickName(prefs.getString(nickNameKey, nickNameDefault));
        return client;
    }
}
