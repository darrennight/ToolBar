package com.huawei.toolbar;

import android.app.Application;

public class ToolbarApplication extends Application
{
    private static ToolbarApplication instance;
    
    public static ToolbarApplication getInstance()
    {
        return instance;
    }
    
    @Override
    public void onCreate()
    {
        // TODO Auto-generated method stub
        super.onCreate();
        instance = this;
    }
}
