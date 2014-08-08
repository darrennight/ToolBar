package com.huawei.toolbar;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.huawei.toolbar.ui.MainWindow;
import com.huawei.toolbar.ui.MiniWindow;

public class ViewManager extends Handler
{
    public static final int WINDOW_MINI = 1;
    
    public static final int WINDOW_BACK = 2;
    
    public static final int WINDOW_CLOSE = 3;
    
    public static final int WINDOW_DESTROY = 4;
    
    private Context context;
    
    private MiniWindow mMiniWindow;
    
    private MainWindow mMainWindow;
    
    private MyParams miniParams;
    
    private MyParams normalParams;
    
    public ViewManager()
    {
        context = ToolbarApplication.getInstance();
        mMiniWindow = new MiniWindow(this);
        mMainWindow = new MainWindow(this);
        
    }
    
    public void handleMessage(Message msg)
    {
        super.handleMessage(msg);
        switch (msg.what)
        {
            case WINDOW_MINI:
                if (!MainWindow.getIsBackWindowAdded())
                {
                    mMiniWindow.create();
                }
                break;
            
            case WINDOW_BACK:
                mMiniWindow.remove();
                mMainWindow.create();
                break;
            
            case WINDOW_CLOSE:
                mMainWindow.remove();
                break;
            
            case WINDOW_DESTROY:
                mMainWindow.remove();
                mMiniWindow.remove();
                break;
            
            default:
                break;
        }
    }
}
