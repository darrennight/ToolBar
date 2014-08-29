package com.huawei.toolbar.ui;

import com.huawei.toolbar.util.PresData;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;

public class WaterView extends View
{
    private static WaterView instance;
    
    private PresData data;
    
    private final String WaterViewData = "WaterView";
    
    private Path mAboveWavePath = new Path();
    
    private Path mBelowWavePath = new Path();
    
    private Path mClipPath = new Path();
    
    private Paint mAboveWavePaint = new Paint();
    
    private Paint mBelowWavePaint = new Paint();
    
    private final int above_alpha = 255;
    
    private final int below_alpha = 120;
    
    private int mWaveToTop;
    
    private int mBackColor = Color.WHITE;
    
    private int mOldProgress = 0;
    
    private int mNewProgress = 0;
    
    private final int color_green = 0x99CC66;
    
    private final int color_yellow = 0xFFFF66;
    
    private final int color_red = 0xFF6666;
    
    private Boolean isRefresh = false;
    
    /**
     * 波长
     */
    private final int mZoomX = 150;
    
    /**
     * 波峰
     */
    private final int mZoomY = 5;
    
    /**
     * 每次刷新偏移角
     */
    private final float mOffset = 0.5f;
    
    private final float mMaxRight = mZoomX * mOffset;
    
    /**
     * above波形起点
     */
    private float mAboveOffset = 0.0f;
    
    /**
     * below波形起点
     */
    private float mBelowOffset = -1.0f;
    
    private float mAnimOffset = 0.15f;
    
    private RefreshProgressRunnable mRefreshProgressRunnable;
    
    public WaterView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        instance = this;
        data = new PresData(getContext());
        setProgress(data.getInt(WaterViewData));
        initializePainters();
    }
    
    /**
     * 获取正在运行的waterview实例
     * @return instance
     */
    public static WaterView getInstance()
    {
        return instance;
    }
    
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.clipPath(mClipPath, Region.Op.REPLACE);
        canvas.drawColor(mBackColor);
        canvas.drawPath(mBelowWavePath, mBelowWavePaint);
        canvas.drawPath(mAboveWavePath, mAboveWavePaint);
        
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        setMeasuredDimension(measure(widthMeasureSpec, true),
            measure(heightMeasureSpec, false));
    }
    
    private int measure(int measureSpec, boolean isWidth)
    {
        int result;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        int padding =
            isWidth ? getPaddingLeft() + getPaddingRight() : getPaddingTop()
                + getPaddingBottom();
        if (mode == MeasureSpec.EXACTLY)
        {
            result = size;
        }
        else
        {
            result =
                isWidth ? getSuggestedMinimumWidth()
                    : getSuggestedMinimumHeight();
            result += padding;
            if (mode == MeasureSpec.AT_MOST)
            {
                if (isWidth)
                {
                    result = Math.max(result, size);
                }
                else
                {
                    result = Math.min(result, size);
                }
            }
        }
        return result;
    }
    
    private void initializePainters()
    {
        mAboveWavePaint.setColor(0x99CC66);
        mAboveWavePaint.setAlpha(above_alpha);
        mAboveWavePaint.setStyle(Paint.Style.FILL);
        mAboveWavePaint.setAntiAlias(true);
        
        mBelowWavePaint.setColor(0x99CC66);
        mBelowWavePaint.setAlpha(below_alpha);
        mBelowWavePaint.setStyle(Paint.Style.FILL);
        mBelowWavePaint.setAntiAlias(true);
    }
    
    /**
     * calculate wave track
     */
    private void calculatePath()
    {
        mAboveWavePath.reset();
        mBelowWavePath.reset();
        mClipPath.reset();
        
        getWaveOffset();
        
        mAboveWavePath.moveTo(getLeft(), getHeight());
        for (float i = 0; mZoomX * i <= getRight() + mMaxRight; i += mOffset)
        {
            mAboveWavePath.lineTo((mZoomX * i),
                (float) (mZoomY * Math.cos(i + mAboveOffset)) + mWaveToTop);
        }
        mAboveWavePath.lineTo(getRight(), getHeight());
        
        mBelowWavePath.moveTo(getLeft(), getHeight());
        for (float i = 0; mZoomX * i <= getRight() + mMaxRight; i += mOffset)
        {
            mBelowWavePath.lineTo((mZoomX * i),
                (float) (mZoomY * Math.cos(i + mBelowOffset)) + mWaveToTop);
        }
        mBelowWavePath.lineTo(getRight(), getHeight());
        
        int i = getWidth() / 2;
        mClipPath.addCircle(i, i, i, Path.Direction.CCW);
    }
    
    public void setProgress(int progress)
    {
        mNewProgress = progress > 100 ? 100 : progress;
        data.save(WaterViewData, mNewProgress);
        
        isRefresh = true;
    }
    
    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        mRefreshProgressRunnable = new RefreshProgressRunnable();
        getHandler().post(mRefreshProgressRunnable);
    }
    
    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        getHandler().removeCallbacks(mRefreshProgressRunnable);
    }
    
    private void getWaveOffset()
    {
        if (mBelowOffset > Float.MAX_VALUE - 100)
        {
            mBelowOffset = 0;
        }
        else
        {
            mBelowOffset += mAnimOffset;
        }
        
        if (mAboveOffset > Float.MAX_VALUE - 100)
        {
            mAboveOffset = 0;
        }
        else
        {
            mAboveOffset += mAnimOffset;
        }
    }
    
    private class RefreshProgressRunnable implements Runnable
    {
        int i = mOldProgress;
        
        public void run()
        {
            synchronized (WaterView.this)
            {
                if (isRefresh)
                {
                    if (i > 0)
                    {
                        i = (i == 1) ? 0 : (i - 3);
                    }
                    else
                    {
                        isRefresh = false;
                    }
                }
                else
                {
                    if (i < mNewProgress)
                    {
                        i = (i == mNewProgress - 1) ? mNewProgress : (i + 2);
                    }
                }
                
                if (i <= 50)
                {
                    mBelowWavePaint.setColor(color_green);
                    mAboveWavePaint.setColor(color_green);
                }
                else if (i > 50 && i <= 80)
                {
                    mBelowWavePaint.setColor(color_yellow);
                    mAboveWavePaint.setColor(color_yellow);
                }
                else
                {
                    mBelowWavePaint.setColor(color_red);
                    mAboveWavePaint.setColor(color_red);
                }
                mBelowWavePaint.setAlpha(below_alpha);
                mAboveWavePaint.setAlpha(above_alpha);
                
                mWaveToTop = (int) (getHeight() * (1f - i / 100f));
                calculatePath();
                invalidate();
                getHandler().postDelayed(this, 60);
            }
        }
    }
}
