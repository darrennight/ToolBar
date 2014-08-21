package com.huawei.toolbar.ui;

import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.huawei.toolbar.GlobleConstants;
import com.huawei.toolbar.R;

public class MainWindow extends BaseWindow
{
    private static Boolean isWindowAdded = false;
    
    private Button mCloseBtn;
    
    private Button mShowBtn;
    
    private Button mShopBtn;
    
    private Button mMessageBtn;
    
    private Button mAboutBtn;
    
    public MainWindow(Handler handler)
    {
        super(handler);
        
        mCloseBtn = (Button) mWindow.findViewById(R.id.close_btn);
        mCloseBtn.setOnClickListener(this);
        mShowBtn = (Button) mWindow.findViewById(R.id.showbtn);
        mShowBtn.setOnClickListener(this);
        mShopBtn = (Button) mWindow.findViewById(R.id.btn_store);
        mShopBtn.setOnClickListener(this);
        mMessageBtn = (Button) mWindow.findViewById(R.id.btn_message);
        mMessageBtn.setOnClickListener(this);
        mAboutBtn = (Button) mWindow.findViewById(R.id.btn_about);
        mAboutBtn.setOnClickListener(this);
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
            mHandler.sendEmptyMessage(GlobleConstants.OprationType.CLOSE);
        }
        if (mShowBtn == v)
        {
            
        }
        if (mShopBtn == v)
        {
            mHandler.sendEmptyMessage(GlobleConstants.WindowType.SHOP);
        }
        if (mMessageBtn == v)
        {
            mHandler.sendEmptyMessage(GlobleConstants.WindowType.MESSAGE);
        }
        if (mAboutBtn == v)
        {
            mHandler.sendEmptyMessage(GlobleConstants.WindowType.ABOUT);
        }
    }
    
    @Override
    protected int setWindow()
    {
        return R.layout.manager;
    }
}
