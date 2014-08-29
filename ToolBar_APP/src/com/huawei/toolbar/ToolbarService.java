package com.huawei.toolbar;

import java.util.Timer;
import java.util.TimerTask;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;
import android.util.Log;

public class ToolbarService extends Service
{
    /**
     * 定时检测页面状态
     */
    private Timer mTimer;
    
    private ViewManager handler;
    
    private ActivityManager mActivityManager;
    
    private String mLastActivity = "";
    
    private TimerTask mTask = new TimerTask()
    {
        @Override
        public void run()
        {
            String currentActivity = getTopActivity();
            getTopPackage();
            if (!mLastActivity.equals(currentActivity))
            {
                mLastActivity = currentActivity;
                if (currentActivity.equals("com.tencent.mobileqq.activity.SplashActivity"))
                {
                    handler.sendEmptyMessage(GlobleConstants.WindowType.MINI);
                }
                else if (currentActivity.equals("com.tencent.mobileqq.activity.PublicAccountChatActivity"))
                {
                    handler.sendEmptyMessage(GlobleConstants.WindowType.AFTER_PLAY);
                }
                else if (currentActivity.equals("com.tencent.mobileqq.activity.ChatActivity"))
                {
                    handler.sendEmptyMessage(GlobleConstants.WindowType.BEFORE_PLAY);
                }
                else if (currentActivity.equals("com.huawei.toolbar.MainActivity"))
                {
                    handler.sendEmptyMessage(GlobleConstants.WindowType.WARN);
                }
                else
                {
                    handler.sendEmptyMessage(GlobleConstants.OprationType.CLOSEALL);
                }
            }
        }
    };
    
    //com.huawei.toolbar.MainActivity
    //com.tencent.mobileqq.activity.SplashActivity
    //com.tencent.mobileqq.activity.ChatActivity
    //com.tencent.mobileqq.activity.PublicAccountChatActivity
    
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
    
    @Override
    public void onCreate()
    {
        super.onCreate();
        mActivityManager =
            (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        handler = new ViewManager();
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.i("Service", "onStartCommand");
        
        if (mTimer == null)
        {
            mTimer = new Timer();
            mTimer.scheduleAtFixedRate(mTask, 0, 500);
        }
        
        return super.onStartCommand(intent, flags, startId);
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
    }
    
    @Override
    public void onDestroy()
    {
        if (mTimer != null)
        {
            mTimer.cancel();
            mTimer = null;
        }
        handler.sendEmptyMessage(GlobleConstants.OprationType.CLOSEALL);
        super.onDestroy();
    }
    
    /**
     * 获取ComponentName
     * @return ComponentName
     */
    private ComponentName getComponentName()
    {
        return mActivityManager.getRunningTasks(1).get(0).topActivity;
    }
    
    /**
     * 获取系统最上层Activity
     * @return 系统最上层Activity
     */
    private String getTopActivity()
    {
        if (getComponentName().getClassName() != null)
        {
            Log.i("ToolbarService", "TopActivity = "
                + getComponentName().getClassName());
            return getComponentName().getClassName();
        }
        return null;
    }
    
    /**
     * 获取当前显示的APP包名
     * @return 当前显示的APP包名
     */
    private String getTopPackage()
    {
        if (getComponentName().getPackageName() != null)
        {
            Log.i("ToolbarService", "TopAPP = "
                + getComponentName().getPackageName());
            return getComponentName().getPackageName();
        }
        return null;
    }
}
