package com.huawei.toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener
{
    private Button startService;
    
    private Button stopService;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        startService = (Button) findViewById(R.id.start_service);
        startService.setOnClickListener(this);
        stopService = (Button) findViewById(R.id.stop_service);
        stopService.setOnClickListener(this);
        
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
                break;
            
            case R.id.stop_service:
                Intent stopToolbarService =
                    new Intent(this, ToolbarService.class);
                stopService(stopToolbarService);
                
            default:
                break;
        }
    }
}
