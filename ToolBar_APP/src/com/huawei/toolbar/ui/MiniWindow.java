package com.huawei.toolbar.ui;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;

import com.huawei.toolbar.MyParams;
import com.huawei.toolbar.R;
import com.huawei.toolbar.ToolbarApplication;
import com.huawei.toolbar.ToolbarService;

public class MiniWindow implements OnTouchListener, OnClickListener
{
    private static Boolean isMiniWindowAdded = false;
    
    public static Boolean getIsMiniWindowAdded()
    {
        return isMiniWindowAdded;
    }
    
    private WindowManager mManager;
    
    private Context mContext;
    
    private Handler mHandler;
    
    private Button mMiniBtn;
    
    private MyParams mMiniParams;
    
    private int mStartX, mStartY;
    
    private int mParamsX, mParamsY;
    
    private int moveX, moveY;
    
    private int mScreenH, mScreenW;
    
    private View mMiniWindow;
    
    public MiniWindow(Handler handler)
    {
        this.mHandler = handler;
        mContext = ToolbarApplication.getInstance();
        mManager =
            (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        mMiniWindow = inflater.inflate(R.layout.mini_view, null);
        
        mScreenH = mManager.getDefaultDisplay().getHeight();
        mScreenW = mManager.getDefaultDisplay().getWidth();
        
        mMiniParams = new MyParams();
        mMiniParams.gravity = Gravity.LEFT | Gravity.TOP;
        mMiniParams.x = mScreenW;
        mMiniParams.y = mScreenH / 3;
        
        mMiniBtn = (Button) mMiniWindow.findViewById(R.id.mini_btn);
        mMiniBtn.setOnTouchListener(this);
        mMiniBtn.setOnClickListener(this);
    }
    
    public void create()
    {
        if (!isMiniWindowAdded)
        {
            mManager.addView(mMiniWindow, mMiniParams);
            isMiniWindowAdded = true;
        }
    }
    
    public void remove()
    {
        if (isMiniWindowAdded)
        {
            mManager.removeView(mMiniWindow);
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
                    mParamsX = mMiniParams.x;
                    mParamsY = mMiniParams.y;
                    break;
                
                case MotionEvent.ACTION_MOVE:
                    moveX = (int) event.getRawX() - mStartX;
                    moveY = (int) event.getRawY() - mStartY;
                    mMiniParams.x = mParamsX + moveX;
                    mMiniParams.y = mParamsY + moveY;
                    mManager.updateViewLayout(mMiniWindow, mMiniParams);
                    break;
                
                case MotionEvent.ACTION_UP:
                    if (moveX > 10 || moveY > 10)
                    {
                        isMoved = true;
                    }
                    if (mMiniParams.x > mScreenW / 2)
                    {
                        mMiniParams.x = mScreenW;
                    }
                    else
                    {
                        mMiniParams.x = 0;
                    }
                    mManager.updateViewLayout(mMiniWindow, mMiniParams);
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
            mHandler.sendEmptyMessage(ToolbarService.WINDOW_BACK_TAG);
        }
    }
}
