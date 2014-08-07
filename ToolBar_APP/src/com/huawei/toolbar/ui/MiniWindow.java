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
    
    private int startX, startY;
    
    private int paramsX, paramsY;
    
    private int moveX, moveY;
    
    private int screenH, screenW;
    
    private View mMiniWindow;
    
    public MiniWindow(Context context, WindowManager manager, Handler handler)
    {
        this.mManager = manager;
        this.mContext = context;
        this.mHandler = handler;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        mMiniWindow = inflater.inflate(R.layout.mini_view, null);
        
        screenH = mManager.getDefaultDisplay().getHeight();
        screenW = mManager.getDefaultDisplay().getWidth();
        
        mMiniParams = new MyParams();
        mMiniParams.gravity = Gravity.LEFT | Gravity.TOP;
        mMiniParams.x = screenW;
        mMiniParams.y = screenH / 3;
        
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
                    startX = (int) event.getRawX();
                    startY = (int) event.getRawY();
                    paramsX = mMiniParams.x;
                    paramsY = mMiniParams.y;
                    break;
                
                case MotionEvent.ACTION_MOVE:
                    moveX = (int) event.getRawX() - startX;
                    moveY = (int) event.getRawY() - startY;
                    mMiniParams.x = paramsX + moveX;
                    mMiniParams.y = paramsY + moveY;
                    mManager.updateViewLayout(mMiniWindow, mMiniParams);
                    break;
                
                case MotionEvent.ACTION_UP:
                    if (moveX > 10 || moveY > 10)
                    {
                        isMoved = true;
                    }
                    if (mMiniParams.x > screenW / 2)
                    {
                        mMiniParams.x = screenW;
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
