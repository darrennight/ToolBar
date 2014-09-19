package com.huawei.toolbar.ui.windows;

import com.huawei.toolbar.GlobleConstants;
import com.huawei.toolbar.R;
import com.huawei.toolbar.ui.params.WindowParamsFill;

import android.os.Handler;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;

public class MessageWindow extends BaseWindow
{
    private static Boolean isWindowAdded = false;
    
    private Button mBackBtn;
    
    private Button mCloseBtn;
    
    private Button mPullUpBtn;
    
    public MessageWindow(Handler handler)
    {
        super(handler);
        
        mBackBtn = (Button) mWindow.findViewById(R.id.back_btn);
        mCloseBtn = (Button) mWindow.findViewById(R.id.close_btn);
        mBackBtn.setOnClickListener(this);
        mCloseBtn.setOnClickListener(this);
        mPullUpBtn = (Button) mWindow.findViewById(R.id.pull_btn);
        mPullUpBtn.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v)
    {
        if (mBackBtn == v)
        {
            AnimationUp(GlobleConstants.OprationType.BACK);
        }
        if (mCloseBtn == v)
        {
            AnimationUp(GlobleConstants.OprationType.CLOSE);
        }
        if (mPullUpBtn == v)
        {
            AnimationUp(GlobleConstants.OprationType.BACK);
        }
    }
    
    @Override
    protected int setWindow()
    {
        return R.layout.message_view;
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
