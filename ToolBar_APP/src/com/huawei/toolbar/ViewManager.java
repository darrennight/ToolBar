package com.huawei.toolbar;

import android.os.Handler;
import android.os.Message;

import com.huawei.toolbar.ui.MainWindow;
import com.huawei.toolbar.ui.MiniWindow;
import com.huawei.toolbar.ui.ShopWindow;

/**
 * 管理window展示
 * @author wWX191016
 */
public class ViewManager extends Handler
{
    private MiniWindow mMiniWindow = new MiniWindow(this);
    
    private MainWindow mMainWindow = new MainWindow(this);
    
    private ShopWindow mShopWindow = new ShopWindow(this);
    
    public ViewManager()
    {
        
    }
    
    public void handleMessage(Message msg)
    {
        super.handleMessage(msg);
        switch (msg.what)
        {
            case GlobleConstants.WindowType.WINDOW_MINI:
                if (!MainWindow.isMainWindowAdded()
                    && !ShopWindow.isWindowAdded())
                {
                    mMiniWindow.create();
                }
                break;
            
            case GlobleConstants.WindowType.WINDOW_MAIN:
                mMiniWindow.remove();
                mMainWindow.create();
                break;
            
            case GlobleConstants.WindowType.WINDOW_SHOP:
                mMainWindow.remove();
                mShopWindow.create();
                break;
            
            case GlobleConstants.OprationType.WINDOW_BACK:
                
                break;
            
            case GlobleConstants.OprationType.WINDOW_CLOSE:
                mMainWindow.remove();
                mShopWindow.remove();
                break;
            
            case GlobleConstants.OprationType.WINDOW_CLOSEALL:
                mMainWindow.remove();
                mShopWindow.remove();
                mMiniWindow.remove();
                break;
            
            default:
                break;
        }
        
    }
}
