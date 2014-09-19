package com.huawei.toolbar.ui.windows;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.media.AudioManager;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.huawei.toolbar.GlobleConstants;
import com.huawei.toolbar.R;
import com.huawei.toolbar.ui.params.WindowParamsSmall;

public class MiniWindow extends BaseWindow implements OnTouchListener
{
    private static Boolean isMiniWindowAdded = false;
    
    public static Boolean getIsMiniWindowAdded()
    {
        return isMiniWindowAdded;
    }
    
    private Handler handler = new Handler();
    
    private Timer mTimer;
    
    private Button mMiniBtn;
    
    private int mStartX, mStartY;
    
    private int mParamsX, mParamsY;
    
    private int moveX, moveY;
    
    private AudioManager mAudioManager;
    
    private TimerTask mTask = new TimerTask()
    {
        @Override
        public void run()
        {
            handler.post(new Runnable()
            {
                public void run()
                {
                    Log.i("11", "111");
                    if (mAudioManager.isMusicActive())
                    {
                        if (mMiniBtn.getVisibility() == View.VISIBLE)
                        {
                            mMiniBtn.setVisibility(View.GONE);
                        }
                    }
                    else
                    {
                        if (mMiniBtn.getVisibility() != View.VISIBLE)
                        {
                            mMiniBtn.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });
            
        }
    };
    
    public MiniWindow(Handler handler)
    {
        super(handler);
        
        mParams.gravity = Gravity.LEFT | Gravity.TOP;
        mParams.x = screenW;
        mParams.y = screenH / 3;
        
        mMiniBtn = (Button) mWindow.findViewById(R.id.mini_btn);
        mMiniBtn.setOnTouchListener(this);
        mMiniBtn.setOnClickListener(this);
        
        mAudioManager =
            (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        
//        if (mTimer == null)
//        {
//            mTimer = new Timer();
//            mTimer.schedule(mTask, 500);
//        }
    }
    
    @Override
    public void create()
    {
        if (!isMiniWindowAdded)
        {
            mManager.addView(mWindow, mParams);
            if (mParams.x > screenW / 2)
            {
                AnimationRight();
            }
            else
            {
                AnimationLeft();
            }
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
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                mMiniBtn.clearAnimation();
                mMiniBtn.bringToFront();
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
                if (Math.abs(moveX) > 10 || Math.abs(moveY) > 10)
                {
                    isMoved = true;
                }
                if (mParams.x > screenW / 2)
                {
                    mParams.x = screenW - mMiniBtn.getWidth();
                    mManager.updateViewLayout(mWindow, mParams);
                    AnimationRight();
                }
                else
                {
                    mParams.x = 0;
                    mManager.updateViewLayout(mWindow, mParams);
                    AnimationLeft();
                }
                moveX = 0;
                moveY = 0;
                break;
            
            default:
                break;
        }
        return isMoved;
    }
    
    @Override
    public void onClick(View v)
    {
        if (mMiniBtn == v)
        {
            mHandler.sendEmptyMessage(GlobleConstants.WindowType.MAIN);
        }
    }
    
    @Override
    protected int setWindow()
    {
        return R.layout.mini_view;
    }
    
    /**
     * MiniWindow 依靠在右边的动画
     */
    private void AnimationRight()
    {
        Animation animation =
            AnimationUtils.loadAnimation(mContext, R.anim.hide_right);
        animation.setFillAfter(true);
        mMiniBtn.clearAnimation();
        mMiniBtn.startAnimation(animation);
    }
    
    /**
     * MiniWindow 依靠在左边的动画
     */
    private void AnimationLeft()
    {
        Animation animation =
            AnimationUtils.loadAnimation(mContext, R.anim.hide_left);
        animation.setFillAfter(true);
        mMiniBtn.clearAnimation();
        mMiniBtn.startAnimation(animation);
    }
    
    @Override
    protected LayoutParams setParams()
    {
        return new WindowParamsSmall();
    }
    
    @Override
    protected int setAnimationId()
    {
        return 0;
    }
}
