package com.huawei.toolbar.ui.activity;

import com.huawei.toolbar.R;

import android.app.Activity;
import android.os.Bundle;

/**
 * [一句话功能简述]二级主页面，用于windows向activity跳转<BR>
 * [功能详细描述]
 * @author wWX191016
 * @version [RCS Client V100R001C03, 2014-9-10] 
 */
public class SubMainActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_main);
    }
}
