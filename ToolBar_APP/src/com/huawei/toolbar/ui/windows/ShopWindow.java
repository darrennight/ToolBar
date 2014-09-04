package com.huawei.toolbar.ui.windows;

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
    
    private Button mBuyBtn1, mBuyBtn2, mBuyBtn3;
    
    public ShopWindow(Handler handler)
    {
        super(handler);
        
        mBackBtn = (Button) mWindow.findViewById(R.id.back_btn);
        mBackBtn.setOnClickListener(this);
        mCloseBtn = (Button) mWindow.findViewById(R.id.close_btn);
        mCloseBtn.setOnClickListener(this);
        mBuyBtn1 = (Button) mWindow.findViewById(R.id.btn_shop1);
        mBuyBtn1.setOnClickListener(this);
        mBuyBtn2 = (Button) mWindow.findViewById(R.id.btn_shop2);
        mBuyBtn2.setOnClickListener(this);
        mBuyBtn3 = (Button) mWindow.findViewById(R.id.btn_shop3);
        mBuyBtn3.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v)
    {
        if (mBackBtn == v)
        {
            mHandler.sendEmptyMessage(GlobleConstants.OprationType.BACK);
        }
        if (mCloseBtn == v)
        {
            mHandler.sendEmptyMessage(GlobleConstants.OprationType.CLOSE);
        }
        if (mBuyBtn1 == v)
        {
            mHandler.sendEmptyMessage(GlobleConstants.WindowType.BUY);
        }
        if (mBuyBtn2 == v)
        {
            mHandler.sendEmptyMessage(GlobleConstants.WindowType.BUY);
        }
        if (mBuyBtn3 == v)
        {
            mHandler.sendEmptyMessage(GlobleConstants.WindowType.BUY);
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
