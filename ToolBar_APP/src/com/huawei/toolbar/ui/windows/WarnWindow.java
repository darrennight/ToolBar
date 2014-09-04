package com.huawei.toolbar.ui.windows;

import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.huawei.toolbar.GlobleConstants;
import com.huawei.toolbar.R;

public class WarnWindow extends BaseWindow
{
    private static Boolean isWindowAdded = false;
    
    private Button mCloseBtn;
    
    private Button mShopBtn;
    
    private Button mDraw;
    
    public WarnWindow(Handler handler)
    {
        super(handler);
        
        mCloseBtn = (Button) mWindow.findViewById(R.id.close_btn);
        mCloseBtn.setOnClickListener(this);
        mShopBtn = (Button) mWindow.findViewById(R.id.shop_btn);
        mShopBtn.setOnClickListener(this);
        mDraw = (Button) mWindow.findViewById(R.id.lucky_btn);
        mDraw.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v)
    {
        if (mCloseBtn == v)
        {
            mHandler.sendEmptyMessage(GlobleConstants.OprationType.CLOSE);
        }
        if (mShopBtn == v)
        {
            mHandler.sendEmptyMessage(GlobleConstants.WindowType.SHOP);
        }
        if (mDraw == v)
        {
            int i = (int) (Math.random() * 2);
            if (i == 0)
            {
                mHandler.sendEmptyMessage(GlobleConstants.WindowType.LUCKY);
            }
            else
            {
                mHandler.sendEmptyMessage(GlobleConstants.WindowType.UNLUCKY);
            }
        }
    }
    
    @Override
    protected int setWindow()
    {
        return R.layout.warn_play_view;
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
