package com.huawei.toolbar.ui.windows;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

import com.huawei.toolbar.R;
import com.huawei.toolbar.ToolbarApplication;
import com.huawei.toolbar.ui.params.ToolbarParams;

public abstract class BaseWindow implements OnClickListener
{
    protected WindowManager mManager;
    
    protected Context mContext;
    
    protected Handler mHandler;
    
    protected ToolbarParams mParams;
    
    protected int screenH;
    
    protected int screenW;
    
    protected View mWindow;
    
    private View mAnimationLayout;
    
    private static Boolean isMiniWindowAdded = false;
    
    /**
     * 可拖动的mini悬浮窗属性
     */
    protected static final int WINDOW_MINI = 0;
    
    /**
     * 小悬浮窗属性
     */
    protected static final int WINDOW_SMALL = 1;
    
    /**
     * 全屏悬浮窗属性
     */
    protected static final int WINDOW_FILL = 2;
    
    public BaseWindow(Handler handler)
    {
        mHandler = handler;
        
        mContext = ToolbarApplication.getInstance();
        mManager =
            (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        
        getScreenSize();
        
        mWindow = LayoutInflater.from(mContext).inflate(windowLayout(), null);
        
        mParams = new ToolbarParams();
        
        mAnimationLayout = mWindow.findViewById(animationLayoutId());
    }
    
    /**
     * [一句话功能简述]需要加载的布局<BR>
     * [功能详细描述]
     * @return resource id
     */
    protected abstract int windowLayout();
    
    /**
     * [一句话功能简述]需要加载的params类型<BR>
     * [功能详细描述]
     * @return 类型
     */
    protected abstract int paramsType();
    
    /**
     * [一句话功能简述]需要加载动画的布局<BR>
     * [功能详细描述]
     * @return 动画资源id
     */
    protected abstract int animationLayoutId();
    
    /**
     * [一句话功能简述]创建悬浮窗<BR>
     * [功能详细描述]
     */
    public void create()
    {
        if (isMiniWindowAdded)
        {
            return;
        }
        getScreenSize();
        setParams(paramsType());
        mManager.addView(mWindow, mParams);
        isMiniWindowAdded = true;
    }
    
    /**
     * [一句话功能简述]关闭悬浮窗<BR>
     * [功能详细描述]
     */
    public void remove()
    {
        if (!isMiniWindowAdded)
        {
            return;
        }
        mManager.removeView(mWindow);
        isMiniWindowAdded = false;
    }
    
    /**
     * [一句话功能简述]更新悬浮窗<BR>
     * [功能详细描述]
     */
    public void update()
    {
        if (!isMiniWindowAdded)
        {
            return;
        }
        getScreenSize();
        setParams(paramsType());
        mManager.updateViewLayout(mWindow, mParams);
        Log.i("Window", "screenH=" + screenH + ",screenW=" + screenW);
    }
    
    /**
     * [一句话功能简述]window的出现动画<BR>
     * [功能详细描述]
     */
    protected void AnimationDown()
    {
        if (animationLayoutId() != 0)
        {
            Animation animation =
                AnimationUtils.loadAnimation(mContext, R.anim.window_down);
            animation.setFillAfter(true);
            mAnimationLayout.clearAnimation();
            mAnimationLayout.startAnimation(animation);
        }
    }
    
    /**
     * [一句话功能简述]window的关闭动画，动画结束会发送一个消息<BR>
     * [功能详细描述]
     * @param what 发送message的what值
     */
    protected void AnimationUp(final int what)
    {
        if (animationLayoutId() != 0)
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
     * [功能详细描述]
     */
    private void getScreenSize()
    {
        screenH = mManager.getDefaultDisplay().getHeight();
        screenW = mManager.getDefaultDisplay().getWidth();
    }
    
    private void setParams(int type)
    {
        switch (type)
        {
            case WINDOW_MINI:
                mParams.gravity = Gravity.LEFT | Gravity.TOP;
                mParams.width = LayoutParams.WRAP_CONTENT;
                mParams.height = LayoutParams.WRAP_CONTENT;
                mParams.x = screenW;
                mParams.y = screenH / 3;
                break;
            case WINDOW_SMALL:
                mParams.gravity = Gravity.CENTER;
                mParams.width = LayoutParams.WRAP_CONTENT;
                mParams.height = LayoutParams.WRAP_CONTENT;
                break;
            
            case WINDOW_FILL:
                mParams.gravity = Gravity.TOP;
                mParams.width = screenW;
                mParams.height = screenH - 100;
                mParams.y = 0;
                break;
            
            default:
                break;
        }
    }
}
