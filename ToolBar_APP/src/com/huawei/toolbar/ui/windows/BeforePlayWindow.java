package com.huawei.toolbar.ui.windows;

import com.huawei.toolbar.GlobleConstants;
import com.huawei.toolbar.R;

import android.os.Handler;
import android.view.View;
import android.widget.Button;

public class BeforePlayWindow extends BaseWindow
{
    private static Boolean isWindowAdded = false;
    
    private Button mCloseBtn;
    
    private Button mShopBtn;
    
    public BeforePlayWindow(Handler handler)
    {
        super(handler);
        
        mCloseBtn = (Button) mWindow.findViewById(R.id.close_btn);
        mCloseBtn.setOnClickListener(this);
        mShopBtn = (Button) mWindow.findViewById(R.id.shop_btn);
        mShopBtn.setOnClickListener(this);
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
    }
    
    @Override
    protected int setWindow()
    {
        return R.layout.before_play_view;
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
