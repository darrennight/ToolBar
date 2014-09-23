package com.huawei.toolbar.ui.windows;

import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.huawei.toolbar.GlobleConstants;
import com.huawei.toolbar.R;

public class FeedbackSuccessWindow extends BaseWindow
{
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
    protected int windowLayout()
    {
        return R.layout.feedback_success_view;
    }
    
    @Override
    public void create()
    {
        super.create();
        AnimationDown();
    }
    
    @Override
    protected int paramsType()
    {
        return WINDOW_FILL;
    }
    
    @Override
    protected int animationLayoutId()
    {
        return R.id.layout_back;
    }
}
