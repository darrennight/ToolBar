package com.huawei.toolbar;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;

import com.huawei.toolbar.ui.MainWindow;
import com.huawei.toolbar.ui.MiniWindow;

public class ToolbarService extends Service
{
    private static final int WINDOW_MINI_TAG = 1;
    
    public static final int WINDOW_BACK_TAG = 2;
    
    public static final int WINDOW_CLOSE_TAG = 3;
    
    private Timer mTimer;
    
    private WindowManager mManager;
    
    private MiniWindow mMiniWindow;
    
    private MainWindow mMainWindow;
    
    private TimerTask mTask = new TimerTask()
    {
        @Override
        public void run()
        {
            handler.sendEmptyMessage(WINDOW_MINI_TAG);
        }
    };
    
    public Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case WINDOW_MINI_TAG:
                    if (!MainWindow.getIsBackWindowAdded())
                    {
                        mMiniWindow.create();
                    }
                    break;
                
                case WINDOW_BACK_TAG:
                    mMiniWindow.remove();
                    mMainWindow.create();
                    break;
                    
                case WINDOW_CLOSE_TAG:
                    mMainWindow.remove();
                    break;
                    
                default:
                    break;
            }
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
        Log.i("Service", "onCreate");
        mManager =
            (WindowManager) ToolbarApplication.getInstance()
                .getSystemService(Context.WINDOW_SERVICE);
        
        mMiniWindow = new MiniWindow(handler);
        mMainWindow = new MainWindow(handler);
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
        mMainWindow.remove();
        mMiniWindow.remove();
        
        super.onDestroy();
    }
}
