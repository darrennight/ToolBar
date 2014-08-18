package com.huawei.toolbar;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
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
    
    private ViewManager handler = new ViewManager();
    
    private TimerTask mTask = new TimerTask()
    {
        @Override
        public void run()
        {
            handler.sendEmptyMessage(GlobleConstants.WindowType.WINDOW_MINI);
        }
    };
    
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
    
    @Override
    public void onCreate()
    {
        super.onCreate();
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
        handler.sendEmptyMessage(GlobleConstants.OprationType.WINDOW_CLOSEALL);
        super.onDestroy();
    }
}
