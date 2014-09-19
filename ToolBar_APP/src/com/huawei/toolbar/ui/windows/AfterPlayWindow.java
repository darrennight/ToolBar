package com.huawei.toolbar.ui.windows;

import android.os.Handler;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;

import com.huawei.toolbar.GlobleConstants;
import com.huawei.toolbar.R;
import com.huawei.toolbar.ui.params.WindowParamsSmall;
import com.huawei.toolbar.ui.view.WaterView;

public class AfterPlayWindow extends BaseWindow
{
    private static Boolean isWindowAdded = false;
    
    private Button mCloseBtn;
    
    private Button mShopBtn;
    
    private Button mWaterBtn;
    
    private WaterView mWaterView;
    
    public AfterPlayWindow(Handler handler)
    {
        super(handler);
        
        mCloseBtn = (Button) mWindow.findViewById(R.id.close_btn);
        mCloseBtn.setOnClickListener(this);
        mShopBtn = (Button) mWindow.findViewById(R.id.shop_btn);
        mShopBtn.setOnClickListener(this);
        mWaterBtn = (Button) mWindow.findViewById(R.id.water_btn);
        mWaterBtn.setOnClickListener(this);
        mWaterView = (WaterView) mWindow.findViewById(R.id.water);
    }
    
    @Override
    public void onClick(View v)
    {
        if (mCloseBtn == v)
        {
            AnimationUp(GlobleConstants.OprationType.CLOSE);
        }
        if (mShopBtn == v)
        {
            AnimationUp(GlobleConstants.WindowType.SHOP);
        }
        if (mWaterBtn == v)
        {
            mWaterView.setProgress((int) (Math.random() * 100));
        }
    }
    
    @Override
    protected int setWindow()
    {
        return R.layout.after_play_view;
    }
    
    @Override
    public void create()
    {
        if (!isWindowAdded)
        {
            mManager.addView(mWindow, mParams);
            isWindowAdded = true;
            
            AnimationDown();
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
    protected LayoutParams setParams()
    {
        return new WindowParamsSmall();
    }
    
    @Override
    protected int setAnimationId()
    {
        return R.id.layout_back;
    }
    
}
