package com.huawei.toolbar.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.Region;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.View;

import com.huawei.toolbar.util.PresDataUtil;

/**
 * [一句话功能简述]流量展示的水波纹效果<BR>
 * [功能详细描述]
 * @author wWX191016
 * @version [RCS Client V100R001C03, 2014-9-4] 
 */
public class WaterView extends View
{
    private final String WaterViewData = "WaterView";
    
    /**
     * 上层水纹path
     */
    private Path mAboveWavePath = new Path();
    
    /**
     * 下层水纹path
     */
    private Path mBelowWavePath = new Path();
    
    /**
     * 切割画板path
     */
    private Path mClipPath = new Path();
    
    /**
     * 上层水纹paint
     */
    private Paint mAboveWavePaint = new Paint();
    
    /**
     * 下层水纹paint
     */
    private Paint mBelowWavePaint = new Paint();
    
    private Paint mNumberPaint = new Paint();
    
    /**
     * 上层水纹透明度
     */
    private final int above_alpha = 255;
    
    /**
     * 下层水纹透明度
     */
    private final int below_alpha = 120;
    
    /**
     * 所绘制的起点与区域顶端的距离
     */
    private int mWaveToTop;
    
    private int mBackColor = Color.WHITE;
    
    private int mOldProgress = 0;
    
    private int mNewProgress = 0;
    
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
     * 每次刷新偏移
     */
    private final float offset = 0.5f;
    
    private final float mMaxRight = mZoomX * offset;
    
    /**
     * above波形起点
     */
    private float aboveOffset = 0.0f;
    
    /**
     * below波形起点
     */
    private float belowOffset = -1.0f;
    
    private float animOffset = 0.15f;
    
    private RefreshProgressRunnable mRefreshProgressRunnable;
    
    /**
     * 文字的y轴位置
     */
    private float mTextBaseY;
    
    /**
     * 展示的文字
     */
    private String mText;
    
    private SensorManager mSensorManager;
    
    private Sensor mSensor;
    
    private float x, y, z;
    
    private float degrees;
    
    public WaterView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setProgress(PresDataUtil.getInt(WaterViewData));
        initializePainters();
        mSensorManager =
            (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }
    
    /**
     * [一句话功能简述]注册重力感应监听<BR>
     * [功能详细描述]
     */
    public void registerSersor()
    {
        mSensorManager.registerListener(sensorListener,
            mSensor,
            SensorManager.SENSOR_DELAY_GAME);
    }
    
    /**
     * [一句话功能简述]注销重力感应监听<BR>
     * [功能详细描述]
     */
    public void unregisterSersor()
    {
        mSensorManager.unregisterListener(sensorListener);
    }
    
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
            | Paint.FILTER_BITMAP_FLAG));
        // 将图形切割为圆形
        canvas.clipPath(mClipPath, Region.Op.REPLACE);
        // 背景色
        canvas.drawColor(mBackColor);
        // 下层波浪
        canvas.drawPath(mBelowWavePath, mBelowWavePaint);
        // 下层波浪
        canvas.drawPath(mAboveWavePath, mAboveWavePaint);
        // 百分比
        canvas.drawText(mText, getWidth() / 2, mTextBaseY, mNumberPaint);
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
        
        mNumberPaint.setColor(Color.BLUE);
        mNumberPaint.setTextSize(30);
        mNumberPaint.setTextAlign(Align.CENTER);
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
        for (float i = 0; mZoomX * i <= getRight() + mMaxRight; i += offset)
        {
            mAboveWavePath.lineTo((mZoomX * i),
                (float) (mZoomY * Math.cos(i + aboveOffset)) + mWaveToTop);
        }
        mAboveWavePath.lineTo(getRight(), getHeight());
        
        mBelowWavePath.moveTo(getLeft(), getHeight());
        for (float i = 0; mZoomX * i <= getRight() + mMaxRight; i += offset)
        {
            mBelowWavePath.lineTo((mZoomX * i),
                (float) (mZoomY * Math.cos(i + belowOffset)) + mWaveToTop);
        }
        mBelowWavePath.lineTo(getRight(), getHeight());
        
        int i = getWidth() / 2;
        mClipPath.addCircle(i, i, i, Path.Direction.CCW);
    }
    
    public void setProgress(int progress)
    {
        mNewProgress = progress > 100 ? 100 : progress;
        PresDataUtil.save(WaterViewData, mNewProgress);
        
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
        if (belowOffset > Float.MAX_VALUE - 100)
        {
            belowOffset = 0;
        }
        else
        {
            belowOffset += animOffset;
        }
        
        if (aboveOffset > Float.MAX_VALUE - 100)
        {
            aboveOffset = 0;
        }
        else
        {
            aboveOffset += animOffset;
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
                
                FontMetrics fontMetrics = mNumberPaint.getFontMetrics();
                float fontHeight = fontMetrics.bottom - fontMetrics.top;
                mTextBaseY =
                    getHeight() - (getHeight() - fontHeight) / 2
                        - fontMetrics.bottom;
                
                mWaveToTop = (int) (getHeight() * (1f - i / 100f));
                mText = (i < 0 ? 0 : i) + "%";
                calculatePath();
                invalidate();
                getHandler().postDelayed(this, 60);
            }
        }
    }
    
    SensorEventListener sensorListener = new SensorEventListener()
    {
        
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy)
        {
            
        }
        
        @Override
        public void onSensorChanged(SensorEvent event)
        {
            x = event.values[SensorManager.DATA_X];
            y = event.values[SensorManager.DATA_Y];
            z = event.values[SensorManager.DATA_Z];
            
        }
    };
}
