package com.huawei.toolbar.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.huawei.toolbar.GlobleConstants;
import com.huawei.toolbar.R;
import com.huawei.toolbar.ToolbarService;
import com.huawei.toolbar.ViewManager;

/**
 * 启动页面
 * @author wWX191016
 *
 */
public class MainActivity extends Activity implements OnClickListener
{
    private Button startService;
    
    private Button stopService;
    
    private Button beforeView;
    
    private Button afterView;
    
    private Button warnView;
    
    private ViewManager viewManager;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        startService = (Button) findViewById(R.id.start_service);
        startService.setOnClickListener(this);
        stopService = (Button) findViewById(R.id.stop_service);
        stopService.setOnClickListener(this);
        beforeView = (Button) findViewById(R.id.before_btn);
        beforeView.setOnClickListener(this);
        afterView = (Button) findViewById(R.id.after_btn);
        afterView.setOnClickListener(this);
        warnView = (Button) findViewById(R.id.warn_btn);
        warnView.setOnClickListener(this);
        
        viewManager = new ViewManager();
    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.start_service:
                Intent startToolbarService =
                    new Intent(this, ToolbarService.class);
                startService(startToolbarService);
                
                beforeView.setVisibility(View.VISIBLE);
                afterView.setVisibility(View.VISIBLE);
                warnView.setVisibility(View.VISIBLE);
                break;
            
            case R.id.stop_service:
                Intent stopToolbarService =
                    new Intent(this, ToolbarService.class);
                stopService(stopToolbarService);
                
                beforeView.setVisibility(View.GONE);
                afterView.setVisibility(View.GONE);
                warnView.setVisibility(View.GONE);
                break;
            
            case R.id.before_btn:
                viewManager.sendEmptyMessage(GlobleConstants.WindowType.BEFORE_PLAY);
                break;
            
            case R.id.after_btn:
                viewManager.sendEmptyMessage(GlobleConstants.WindowType.AFTER_PLAY);
                break;
            
            case R.id.warn_btn:
                viewManager.sendEmptyMessage(GlobleConstants.WindowType.WARN);
                break;
            
            default:
                break;
        }
    }
}
