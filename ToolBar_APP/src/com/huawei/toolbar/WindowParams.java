package com.huawei.toolbar;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

/**
 * params通用属性
 * @author wWX191016
 *
 */
public class WindowParams extends WindowManager.LayoutParams
{
    public WindowParams()
    {
        type = LayoutParams.TYPE_SYSTEM_ALERT;
        format = PixelFormat.RGBA_8888;
        flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        width = LayoutParams.WRAP_CONTENT;
        height = LayoutParams.WRAP_CONTENT;
        gravity = Gravity.TOP;
        
        Context context = ToolbarApplication.getInstance();
        WindowManager manager =
            (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int screenH = manager.getDefaultDisplay().getHeight();
        y = screenH / 4;
    }
}
