package com.huawei.toolbar.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;

import com.huawei.toolbar.MyParams;
import com.huawei.toolbar.R;

public class MainWindow implements OnClickListener
{
    private static Boolean isBackWindowAdded = false;
    
    public static Boolean getIsBackWindowAdded()
    {
        return isBackWindowAdded;
    }
    
    private WindowManager mManager;
    
    private Context mContext;
    
    private View mBackWindow;
    
    private MyParams mBackParams;
    
    private int screenH, screenW;
    
    public MainWindow(Context context, WindowManager manager)
    {
        this.mContext = context;
        this.mManager = manager;
        screenH = mManager.getDefaultDisplay().getHeight();
        screenW = mManager.getDefaultDisplay().getWidth();
    }
    
    public void create()
    {
        if (!isBackWindowAdded)
        {
            mBackWindow =
                LayoutInflater.from(mContext)
                    .inflate(R.layout.manager, null);
            
            mBackParams = new MyParams();
            mBackParams.y = screenH / 4;
            
            mManager.addView(mBackWindow, mBackParams);
            isBackWindowAdded = true;
        }
    }
    
    public void remove()
    {
        if (isBackWindowAdded)
        {
            mManager.removeView(mBackWindow);
            mBackWindow = null;
            mBackParams = null;
            isBackWindowAdded = false;
        }
    }

    @Override
    public void onClick(View v)
    {
        
    }
}
