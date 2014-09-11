package com.huawei.toolbar.ui.windows;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.huawei.toolbar.GlobleConstants;
import com.huawei.toolbar.R;
import com.huawei.toolbar.ui.view.WaterView;

public class MainWindow extends BaseWindow
{
    private static Boolean isWindowAdded = false;
    
    private Button mCloseBtn;
    
    private Button mShowBtn;
    
    private Button mShopBtn;
    
    private Button mMessageBtn;
    
    private Button mAboutBtn;
    
    private Button mJumpBtn;
    
    private ImageView mUpImage;
    
    private ImageView mDownImage;
    
    private Button mWaterBtn;
    
    private RelativeLayout mFlowLayout;
    
    /**
     * 记录点击展开流量展示的次数
     */
    private int btn_clickNum;
    
    public MainWindow(Handler handler)
    {
        super(handler);
        
        mCloseBtn = (Button) mWindow.findViewById(R.id.close_btn);
        mCloseBtn.setOnClickListener(this);
        mShowBtn = (Button) mWindow.findViewById(R.id.showbtn);
        mShowBtn.setOnClickListener(this);
        mShopBtn = (Button) mWindow.findViewById(R.id.btn_store);
        mShopBtn.setOnClickListener(this);
        mMessageBtn = (Button) mWindow.findViewById(R.id.btn_message);
        mMessageBtn.setOnClickListener(this);
        mAboutBtn = (Button) mWindow.findViewById(R.id.btn_about);
        mAboutBtn.setOnClickListener(this);
        mUpImage = (ImageView) mWindow.findViewById(R.id.up_img);
        mDownImage = (ImageView) mWindow.findViewById(R.id.down_img);
        mFlowLayout = (RelativeLayout) mWindow.findViewById(R.id.layout_view);
        mWaterBtn = (Button) mWindow.findViewById(R.id.water_btn);
        mWaterBtn.setOnClickListener(this);
        mJumpBtn = (Button) mWindow.findViewById(R.id.jump_btn);
        mJumpBtn.setOnClickListener(this);
    }
    
    @Override
    public void create()
    {
        if (!isWindowAdded)
        {
            mManager.addView(mWindow, mParams);
            btn_clickNum = 0;
            isWindowAdded = true;
            WaterView.getInstance().registerSersor();
        }
    }
    
    @Override
    public void remove()
    {
        if (isWindowAdded)
        {
            mManager.removeView(mWindow);
            isWindowAdded = false;
            WaterView.getInstance().unregisterSersor();
        }
    }
    
    @Override
    public void onClick(View v)
    {
        if (mCloseBtn == v)
        {
            mHandler.sendEmptyMessage(GlobleConstants.OprationType.CLOSE);
        }
        if (mShowBtn == v)
        {
            btn_clickNum++;
            if (btn_clickNum % 2 == 1)
            {
                Animation animationDown =
                    AnimationUtils.loadAnimation(mContext, R.anim.scale_down);
                animationDown.setFillAfter(true);
                mFlowLayout.clearAnimation();
                mFlowLayout.startAnimation(animationDown);
                mDownImage.setVisibility(View.GONE);
                mUpImage.setVisibility(View.VISIBLE);
                mMessageBtn.setClickable(false);
                mAboutBtn.setClickable(false);
                mShopBtn.setClickable(false);
                
            }
            else if (btn_clickNum % 2 == 0)
            {
                Animation animationUp =
                    AnimationUtils.loadAnimation(mContext, R.anim.scale_up);
                animationUp.setFillAfter(true);
                mFlowLayout.clearAnimation();
                mFlowLayout.startAnimation(animationUp);
                mDownImage.setVisibility(View.VISIBLE);
                mUpImage.setVisibility(View.GONE);
                mMessageBtn.setClickable(true);
                mAboutBtn.setClickable(true);
                mShopBtn.setClickable(true);
            }
        }
        if (mShopBtn == v)
        {
            mHandler.sendEmptyMessage(GlobleConstants.WindowType.SHOP);
        }
        if (mMessageBtn == v)
        {
            mHandler.sendEmptyMessage(GlobleConstants.WindowType.MESSAGE);
        }
        if (mAboutBtn == v)
        {
            mHandler.sendEmptyMessage(GlobleConstants.WindowType.ABOUT);
        }
        if (mWaterBtn == v)
        {
            WaterView.getInstance().setProgress((int) (Math.random() * 100));
        }
        if (mJumpBtn == v)
        {
            mHandler.sendEmptyMessage(GlobleConstants.ActivityType.SUB_MAIN);
        }
    }
    
    @Override
    protected int setWindow()
    {
        return R.layout.manager;
    }
}
