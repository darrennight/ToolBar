package com.huawei.toolbar.ui;

import android.os.Handler;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;

import com.huawei.toolbar.GlobleConstants;
import com.huawei.toolbar.R;

public class MiniWindow extends BaseWindow implements OnTouchListener
{
    private static Boolean isMiniWindowAdded = false;
    
    public static Boolean getIsMiniWindowAdded()
    {
        return isMiniWindowAdded;
    }
    
    private Button mMiniBtn;
    
    private int mStartX, mStartY;
    
    private int mParamsX, mParamsY;
    
    private int moveX, moveY;
    
    public MiniWindow(Handler handler)
    {
        super(handler);
        
        mParams.gravity = Gravity.LEFT | Gravity.TOP;
        mParams.x = mScreenW;
        mParams.y = mScreenH / 3;
        
        mMiniBtn = (Button) mWindow.findViewById(R.id.mini_btn);
        mMiniBtn.setOnTouchListener(this);
        mMiniBtn.setOnClickListener(this);
    }
    
    @Override
    public void create()
    {
        if (!isMiniWindowAdded)
        {
            mManager.addView(mWindow, mParams);
            isMiniWindowAdded = true;
        }
    }
    
    @Override
    public void remove()
    {
        if (isMiniWindowAdded)
        {
            mManager.removeView(mWindow);
            isMiniWindowAdded = false;
        }
    }
    
    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        Boolean isMoved = false;
        if (mMiniBtn == v)
        {
            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    mStartX = (int) event.getRawX();
                    mStartY = (int) event.getRawY();
                    mParamsX = mParams.x;
                    mParamsY = mParams.y;
                    break;
                
                case MotionEvent.ACTION_MOVE:
                    moveX = (int) event.getRawX() - mStartX;
                    moveY = (int) event.getRawY() - mStartY;
                    mParams.x = mParamsX + moveX;
                    mParams.y = mParamsY + moveY;
                    mManager.updateViewLayout(mWindow, mParams);
                    break;
                
                case MotionEvent.ACTION_UP:
                    if (moveX > 10 || moveY > 10)
                    {
                        isMoved = true;
                    }
                    if (mParams.x > mScreenW / 2)
                    {
                        mParams.x = mScreenW;
                    }
                    else
                    {
                        mParams.x = 0;
                    }
                    mManager.updateViewLayout(mWindow, mParams);
                    break;
                
                default:
                    break;
            }
        }
        return isMoved;
    }
    
    @Override
    public void onClick(View v)
    {
        if (mMiniBtn == v)
        {
            mHandler.sendEmptyMessage(GlobleConstants.WindowType.WINDOW_MAIN);
        }
    }
    
    @Override
    protected int setWindow()
    {
        return R.layout.mini_view;
    }
}
