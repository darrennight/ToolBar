package com.huawei.toolbar.ui.windows;

import com.huawei.toolbar.GlobleConstants;
import com.huawei.toolbar.R;
import com.huawei.toolbar.ui.params.WindowParamsFill;

import android.os.Handler;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;

public class AboutWindow extends BaseWindow
{
    private static Boolean isWindowAdded = false;
    
    private Button mBackBtn;
    
    private Button mCloseBtn;
    
    private Button mFeedbackBtn;
    
    private Button mPullUpBtn;
    
    public AboutWindow(Handler handler)
    {
        super(handler);
        
        mBackBtn = (Button) mWindow.findViewById(R.id.back_btn);
        mBackBtn.setOnClickListener(this);
        mCloseBtn = (Button) mWindow.findViewById(R.id.close_btn);
        mCloseBtn.setOnClickListener(this);
        mFeedbackBtn = (Button) mWindow.findViewById(R.id.feedback_about);
        mFeedbackBtn.setOnClickListener(this);
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
        if (mFeedbackBtn == v)
        {
            AnimationUp(GlobleConstants.WindowType.FEEDBACK);
        }
        if (mPullUpBtn == v)
        {
            AnimationUp(GlobleConstants.OprationType.BACK);
        }
    }
    
    @Override
    protected int setWindow()
    {
        return R.layout.about_view;
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
