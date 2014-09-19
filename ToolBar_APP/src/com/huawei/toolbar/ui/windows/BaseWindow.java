package com.huawei.toolbar.ui.windows;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

import com.huawei.toolbar.R;
import com.huawei.toolbar.ToolbarApplication;

public abstract class BaseWindow implements OnClickListener
{
    protected WindowManager mManager;
    
    protected Context mContext;
    
    protected Handler mHandler;
    
    protected WindowManager.LayoutParams mParams;
    
    protected int screenH;
    
    protected int screenW;
    
    protected View mWindow;
    
    private View mAnimationLayout;
    
    public BaseWindow(Handler handler)
    {
        mHandler = handler;
        
        mContext = ToolbarApplication.getInstance();
        mManager =
            (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        
        getScreenSize();
        
        mWindow = LayoutInflater.from(mContext).inflate(setWindow(), null);
        
        mParams = setParams();
        
        mAnimationLayout = mWindow.findViewById(setAnimationId());
    }
    
    /**
     * [一句话功能简述]需要加载的布局<BR>
     * [功能详细描述]
     * @return resource id
     */
    protected abstract int setWindow();
    
    /**
     * [一句话功能简述]需要加载的params类型<BR>
     * [功能详细描述]
     * @return 类实例
     */
    protected abstract WindowManager.LayoutParams setParams();
    
    /**
     * [一句话功能简述]需要加载的动画<BR>
     * [功能详细描述]
     * @return 动画资源id
     */
    protected abstract int setAnimationId();
    
    public abstract void create();
    
    public abstract void remove();
    
    public void update()
    {
        
    }
    
    /**
     * [一句话功能简述]window的出现动画<BR>
     * [功能详细描述]
     */
    protected void AnimationDown()
    {
        if (setAnimationId() != 0)
        {
            Animation animation =
                AnimationUtils.loadAnimation(mContext, R.anim.window_down);
            animation.setFillAfter(true);
            mAnimationLayout.clearAnimation();
            mAnimationLayout.startAnimation(animation);
        }
    }
    
    /**
     * [一句话功能简述]window的关闭动画<BR>
     * [功能详细描述]
     * @param what 发送message的what值
     */
    protected void AnimationUp(final int what)
    {
        if (setAnimationId() != 0)
        {
            Animation animation =
                AnimationUtils.loadAnimation(mContext, R.anim.window_up);
            animation.setFillAfter(true);
            animation.setAnimationListener(new AnimationListener()
            {
                
                @Override
                public void onAnimationStart(Animation animation)
                {
                    
                }
                
                @Override
                public void onAnimationRepeat(Animation animation)
                {
                    
                }
                
                @Override
                public void onAnimationEnd(Animation animation)
                {
                    mHandler.sendEmptyMessage(what);
                }
            });
            mAnimationLayout.clearAnimation();
            mAnimationLayout.startAnimation(animation);
        }
    }
    
    /**
     * [一句话功能简述]获取屏幕高宽<BR>
     * [功能详细描述]用于处理横竖屏切换以及设置window位置
     */
    private void getScreenSize()
    {
        screenH = mManager.getDefaultDisplay().getHeight();
        screenW = mManager.getDefaultDisplay().getWidth();
    }
}
