package com.huawei.toolbar.ui.windows;

import com.huawei.toolbar.R;

import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;

public class RightWindow extends BaseWindow implements OnTouchListener
{
    private Button mRightBtn;
    
    private int moveX, moveY;
    
    private int mStartX, mStartY;

    public RightWindow(Handler handler)
    {
        super(handler);
        // TODO Auto-generated constructor stub
        mRightBtn = (Button) mWindow.findViewById(R.id.right_btn);
        mRightBtn.setOnTouchListener(this);
    }

    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected int setWindow()
    {
        // TODO Auto-generated method stub
        return R.layout.right_view;
    }

    @Override
    protected LayoutParams setParams()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int setAnimationId()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void create()
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void remove()
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        Boolean isMoved = false;
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                mStartX = (int) event.getRawX();
                break;
            
            case MotionEvent.ACTION_MOVE:
                moveX = (int) event.getRawX() - mStartX;
                break;
            
            case MotionEvent.ACTION_UP:
                if (Math.abs(moveX) > 10)
                {
                    isMoved = true;
                    Log.i("right", "moved");
                }
                moveX = 0;
                
                break;
            
            default:
                break;
        }
        return isMoved;
    }
    
}
