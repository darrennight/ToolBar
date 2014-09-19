package com.huawei.toolbar.ui.windows;

import com.huawei.toolbar.GlobleConstants;
import com.huawei.toolbar.R;
import com.huawei.toolbar.ui.params.WindowParamsFill;
import com.huawei.toolbar.ui.params.WindowParamsSmall;

import android.os.Handler;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;

public class LuckyWindow extends BaseWindow
{
    private static Boolean isWindowAdded = false;
    
    private Button mCloseBtn;
    
    public LuckyWindow(Handler handler)
    {
        super(handler);
        
        mCloseBtn = (Button) mWindow.findViewById(R.id.close_btn);
        mCloseBtn.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v)
    {
        if (mCloseBtn == v)
        {
            AnimationUp(GlobleConstants.OprationType.CLOSE);
        }
    }
    
    @Override
    protected int setWindow()
    {
        return R.layout.lucky_view;
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
