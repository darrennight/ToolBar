package com.huawei.toolbar.ui.windows;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;

import com.huawei.toolbar.ToolbarApplication;
import com.huawei.toolbar.ui.WindowParams;

public abstract class BaseWindow implements OnClickListener
{
    protected WindowManager mManager;
    
    protected Context mContext;
    
    protected Handler mHandler;
    
    protected WindowParams mParams;
    
    protected int mScreenH;
    
    protected int mScreenW;
    
    protected View mWindow;
    
    public BaseWindow(Handler handler)
    {
        mHandler = handler;
        
        mContext = ToolbarApplication.getInstance();
        mManager =
            (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mScreenH = mManager.getDefaultDisplay().getHeight();
        mScreenW = mManager.getDefaultDisplay().getWidth();
        mParams = new WindowParams();
        
        mWindow = LayoutInflater.from(mContext).inflate(setWindow(), null);
    }
    
    protected abstract int setWindow();
    
    public abstract void create();
    
    public abstract void remove();
}
