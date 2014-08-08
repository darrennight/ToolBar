package com.huawei.toolbar.ui;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;

import com.huawei.toolbar.MyParams;
import com.huawei.toolbar.R;
import com.huawei.toolbar.ToolbarApplication;
import com.huawei.toolbar.ToolbarService;

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
    
    private int mScreenH;
    
    private Handler mHandler;
    
    private Button mCloseBtn;
    
    public MainWindow(Handler handler)
    {
        this.mHandler = handler;
        mContext = ToolbarApplication.getInstance();
        mManager =
            (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mScreenH = mManager.getDefaultDisplay().getHeight();
    }
    
    public void create()
    {
        if (!isBackWindowAdded)
        {
            mBackWindow =
                LayoutInflater.from(mContext).inflate(R.layout.manager, null);
            
            mCloseBtn = (Button) mBackWindow.findViewById(R.id.close_btn);
            mCloseBtn.setOnClickListener(this);
            
            mBackParams = new MyParams();
            mBackParams.y = mScreenH / 4;
            
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
        if (mCloseBtn == v)
        {
            mHandler.sendEmptyMessage(ToolbarService.WINDOW_CLOSE_TAG);
        }
    }
}
