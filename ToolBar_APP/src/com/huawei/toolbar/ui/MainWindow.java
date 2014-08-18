package com.huawei.toolbar.ui;

import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.huawei.toolbar.GlobleConstants;
import com.huawei.toolbar.R;

public class MainWindow extends BaseWindow
{
    private static Boolean isWindowAdded = false;
    
    public static Boolean isMainWindowAdded()
    {
        return isWindowAdded;
    }
    
    private Button mCloseBtn;
    
    private Button mShowBtn;
    
    private Button mShopBtn;
    
    public MainWindow(Handler handler)
    {
        super(handler);
        
        mCloseBtn = (Button) mWindow.findViewById(R.id.close_btn);
        mShowBtn = (Button) mWindow.findViewById(R.id.showbtn);
        mShopBtn = (Button) mWindow.findViewById(R.id.btn_store);
        mCloseBtn.setOnClickListener(this);
        mShowBtn.setOnClickListener(this);
        mShopBtn.setOnClickListener(this);
    }
    
    @Override
    public void create()
    {
        if (!isWindowAdded)
        {
            mManager.addView(mWindow, mParams);
            isWindowAdded = true;
        }
    }
    
    @Override
    public void remove()
    {
        if (isWindowAdded)
        {
            mManager.removeView(mWindow);
            isWindowAdded = false;
        }
    }
    
    @Override
    public void onClick(View v)
    {
        if (mCloseBtn == v)
        {
            mHandler.sendEmptyMessage(GlobleConstants.OprationType.WINDOW_CLOSE);
        }
        if (mShowBtn == v)
        {
            
        }
        if (mShopBtn == v)
        {
            mHandler.sendEmptyMessage(GlobleConstants.WindowType.WINDOW_SHOP);
        }
    }

    @Override
    protected int setWindow()
    {
        return R.layout.manager;
    }
}
