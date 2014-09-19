package com.huawei.toolbar.ui.windows;

import android.os.Handler;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;

import com.huawei.toolbar.GlobleConstants;
import com.huawei.toolbar.R;
import com.huawei.toolbar.ui.params.WindowParamsFill;

public class BuySuccessWindow extends BaseWindow
{
    private static Boolean isWindowAdded = false;
    
    private Button mCloseBtn;
    
    public BuySuccessWindow(Handler handler)
    {
        super(handler);
        
        mCloseBtn = (Button) mWindow.findViewById(R.id.buy_success_close);
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
        return R.layout.buy_success_view;
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
        return new WindowParamsFill();
    }
    
    @Override
    protected int setAnimationId()
    {
        return R.id.layout_back;
    }
}
