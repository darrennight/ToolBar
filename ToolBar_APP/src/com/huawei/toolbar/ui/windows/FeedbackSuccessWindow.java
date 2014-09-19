package com.huawei.toolbar.ui.windows;

import com.huawei.toolbar.GlobleConstants;
import com.huawei.toolbar.R;
import com.huawei.toolbar.ui.params.WindowParamsFill;

import android.os.Handler;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;

public class FeedbackSuccessWindow extends BaseWindow
{
    private static Boolean isWindowAdded = false;
    
    private Button mCloseBtn;
    
    public FeedbackSuccessWindow(Handler handler)
    {
        super(handler);
        
        mCloseBtn = (Button) mWindow.findViewById(R.id.btn_close);
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
        return R.layout.feedback_success_view;
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

            AnimationDown();
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
