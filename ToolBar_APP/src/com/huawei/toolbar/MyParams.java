package com.huawei.toolbar;

import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

public class MyParams extends WindowManager.LayoutParams
{
    public MyParams()
    {
        type = LayoutParams.TYPE_SYSTEM_ALERT;
        format = PixelFormat.RGBA_8888;
        flags =
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        width = LayoutParams.WRAP_CONTENT;
        height = LayoutParams.WRAP_CONTENT;
        gravity = Gravity.TOP;
    }
}
