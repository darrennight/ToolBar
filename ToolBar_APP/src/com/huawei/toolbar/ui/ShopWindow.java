package com.huawei.toolbar.ui;

import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.huawei.toolbar.GlobleConstants;
import com.huawei.toolbar.R;

public class ShopWindow extends BaseWindow
{
    private static Boolean isWindowAdded = false;
    
    private Button mBackBtn;
    
    private Button mCloseBtn;
    
    public static Boolean isWindowAdded()
    {
        return isWindowAdded;
    }
    
    public ShopWindow(Handler handler)
    {
        super(handler);
        
        mBackBtn = (Button) mWindow.findViewById(R.id.back_btn);
        mCloseBtn = (Button) mWindow.findViewById(R.id.close_btn);
        mBackBtn.setOnClickListener(this);
        mCloseBtn.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v)
    {
        if (mBackBtn == v)
        {
            mHandler.sendEmptyMessage(GlobleConstants.OprationType.WINDOW_BACK);
        }
        if (mCloseBtn == v)
        {
            mHandler.sendEmptyMessage(GlobleConstants.OprationType.WINDOW_CLOSE);
        }
    }
    
    @Override
    protected int setWindow()
    {
        return R.layout.shop_view;
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
    
}
