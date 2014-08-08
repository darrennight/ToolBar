package com.huawei.toolbar;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.huawei.toolbar.ui.MainWindow;
import com.huawei.toolbar.ui.MiniWindow;

public class ViewManager extends Handler
{
    public static final int WINDOW_MINI_TAG = 1;
    
    public static final int WINDOW_BACK_TAG = 2;
    
    private Context context;
    
    private MiniWindow miniWindow;
    
    private MainWindow mainWindow;
    
    private MyParams miniParams;
    
    private MyParams normalParams;
    
    public ViewManager()
    {
        context = ToolbarApplication.getInstance();
        miniWindow = new MiniWindow(this);
        mainWindow = new MainWindow(this);
        
    }
    
//    @Override
//    public void handleMessage(Message msg)
//    {
//        // TODO Auto-generated method stub
//        super.handleMessage(msg);
//        switch (msg.what)
//        {
//            case WINDOW_MINI_TAG:
//                if (!MainWindow.getIsBackWindowAdded())
//                {
//                    miniWindow.create();
//                }
//                break;
//            
//            case WINDOW_BACK_TAG:
//                miniWindow.remove();
//                mainWindow.create();
//                break;
//            
//            default:
//                break;
//        }
//    }
}
