package com.huawei.toolbar.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences管理器
 * @author wWX191016
 */
public class PresData
{
    private SharedPreferences mPreferences;
    
    private SharedPreferences.Editor mEditor;
    
    private Context mContext;
    
    private static final String PREFS = "toolbar";
    
    public PresData(Context context)
    {
        this.mContext = context;
    }
    
    private void init()
    {
        mPreferences =
            mContext.getSharedPreferences(PREFS, Activity.MODE_PRIVATE);
    }
    
    public void save(String string, int i)
    {
        init();
        mEditor = mPreferences.edit();
        mEditor.putInt(string, i);
        mEditor.commit();
    }
    
    public int getInt(String string)
    {
        init();
        return mPreferences.getInt(string, 0);
    }
    
    public String getString(String string)
    {
        init();
        return mPreferences.getString(string, "");
    }
}
