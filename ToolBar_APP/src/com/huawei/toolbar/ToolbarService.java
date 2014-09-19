package com.huawei.toolbar;

import java.util.Timer;
import java.util.TimerTask;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.AudioManager;
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
    
    private AudioManager mAudioManager;
    
    private TimerTask mTask = new TimerTask()
    {
        @Override
        public void run()
        {
            String currentActivity = getTopActivity();
            String currentPackage = getTopPackage();
            if (!mLastActivity.equals(currentActivity))
            {
                mLastActivity = currentActivity;
                if (currentActivity.equals("com.tudou.ui.activity.DetailActivity")
                    || currentActivity.equals("com.baidu.video.player.PlayerActivity")
                    || currentActivity.equals("com.tencent.qqlive.model.videoinfo.VideoDetailActivity")
                    || currentActivity.equals("org.iqiyi.video.activity.PlayerActivity")
                    || currentActivity.equals("com.sohu.sohuvideo.ui.VideoDetailActivity")
                    || currentActivity.equals("com.youku.ui.activity.DetailActivity")
                    || currentActivity.equals("com.pplive.androidphone.ui.detail.ChannelDetailActivity")
                    || currentActivity.equals("com.sohu.sohuvideo.ui.ShortVideoDetailActivity"))
                {
                    handler.sendEmptyMessage(GlobleConstants.WindowType.BEFORE_PLAY);
                }
                else if (currentPackage.equals("com.tudou.android")
                    || currentPackage.equals("com.baidu.video")
                    || currentPackage.equals("com.tencent.qqlive")
                    || currentPackage.equals("com.qiyi.video")
                    || currentPackage.equals("com.sohu.sohuvideo")
                    || currentPackage.equals("com.youku.phone")
                    || currentPackage.equals("com.pplive.androidphone"))
                {
                    handler.sendEmptyMessage(GlobleConstants.WindowType.MINI);
                }
                else if (currentActivity.equals("com.huawei.toolbar.ui.activity.MainActivity"))
                {
                    handler.sendEmptyMessage(GlobleConstants.WindowType.MINI);
                }
                else
                {
                    handler.sendEmptyMessage(GlobleConstants.OprationType.CLOSEALL);
                }
            }
            if (mAudioManager.isMusicActive())
            {
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
        super.onCreate();
        mActivityManager =
            (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        handler = ViewManager.getInstance();
        
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
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
        
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            Intent intent=new Intent();  
            intent.setAction(GlobleConstants.Action.CONFIG_CHANGE);  
            intent.putExtra(GlobleConstants.ExtraName.SCREEN_ORIENTATION, "portrait");
            sendBroadcast(intent);
        }
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            Intent intent=new Intent();  
            intent.setAction(GlobleConstants.Action.CONFIG_CHANGE);  
            intent.putExtra(GlobleConstants.ExtraName.SCREEN_ORIENTATION, "landscape");
            sendBroadcast(intent);
        }
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
        
        handler = null;
        mActivityManager = null;
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
