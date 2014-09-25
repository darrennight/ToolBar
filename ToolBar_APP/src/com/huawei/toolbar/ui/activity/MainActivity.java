package com.huawei.toolbar.ui.activity;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.Header;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.huawei.toolbar.GlobleConstants;
import com.huawei.toolbar.MyData;
import com.huawei.toolbar.MyWindowManager;
import com.huawei.toolbar.R;
import com.huawei.toolbar.ToolbarService;
import com.huawei.toolbar.util.LogUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * 启动页面
 * @author wWX191016
 *
 */
public class MainActivity extends Activity implements OnClickListener
{
    private Button mStartService;
    
    private Button mStopService;
    
    private Button mBeforeView;
    
    private Button mAfterView;
    
    private Button mWarnView;
    
    private Button mHttpBtn;
    
    private LinearLayout mWindowControlLayout;
    
    private MyWindowManager mViewManager;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
        
        mStartService = (Button) findViewById(R.id.start_service);
        mStartService.setOnClickListener(this);
        mStopService = (Button) findViewById(R.id.stop_service);
        mStopService.setOnClickListener(this);
        mBeforeView = (Button) findViewById(R.id.before_btn);
        mBeforeView.setOnClickListener(this);
        mAfterView = (Button) findViewById(R.id.after_btn);
        mAfterView.setOnClickListener(this);
        mWarnView = (Button) findViewById(R.id.warn_btn);
        mWarnView.setOnClickListener(this);
        mWindowControlLayout = (LinearLayout) findViewById(R.id.window_layout);
        
        mHttpBtn = (Button) findViewById(R.id.send_http_request);
        mHttpBtn.setOnClickListener(this);
        
        mViewManager = MyWindowManager.getInstance();
    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.start_service:
                if (!isServiceRun())
                {
                    Intent startToolbarService =
                        new Intent(this, ToolbarService.class);
                    startService(startToolbarService);
                }
                
                mWindowControlLayout.setVisibility(View.VISIBLE);
                break;
            
            case R.id.stop_service:
                if (isServiceRun())
                {
                    Intent stopToolbarService =
                        new Intent(this, ToolbarService.class);
                    stopService(stopToolbarService);
                }
                
                mWindowControlLayout.setVisibility(View.GONE);
                break;
            
            case R.id.before_btn:
                mViewManager.sendEmptyMessage(GlobleConstants.WindowType.BEFORE_PLAY);
                break;
            
            case R.id.after_btn:
                mViewManager.sendEmptyMessage(GlobleConstants.WindowType.AFTER_PLAY);
                break;
            
            case R.id.warn_btn:
                mViewManager.sendEmptyMessage(GlobleConstants.WindowType.WARN);
                break;
            
            case R.id.send_http_request:
                AsyncHttpClient client = new AsyncHttpClient();
                client.get("http://172.17.182.49:9001/myservice/getdata.do?id=10000",
                    new AsyncHttpResponseHandler()
                    {
                        
                        @Override
                        public void onStart()
                        {
                            super.onStart();
                            LogUtil.i("onStart()", "start");
                        }
                        
                        @Override
                        public void onSuccess(int arg0, Header[] arg1,
                            byte[] arg2)
                        {
                            LogUtil.i("onSuccess", arg0 + "");
                            if (arg1 != null)
                            {
                                for (int i = 0; i < arg1.length; i++)
                                {
                                    LogUtil.i("onSuccess", arg1[i].toString());
                                }
                            }
                            if (arg2 != null)
                            {
                                String string = new String(arg2);
                                LogUtil.i("onSuccess", string);
                                
                                InputStream stream =
                                    new ByteArrayInputStream(arg2);
                                XmlPullParser parser = Xml.newPullParser();
                                try
                                {
                                    MyData data = null;
                                    
                                    parser.setInput(stream, "UTF-8");
                                    int event = parser.getEventType();
                                    
                                    while (event != XmlPullParser.END_DOCUMENT)
                                    {
                                        switch (event)
                                        {
                                            case XmlPullParser.START_TAG:
                                                if ("total".equals(parser.getName()))
                                                {
                                                    data = new MyData();
                                                    data.setTotal(Integer.parseInt(parser.getAttributeValue(0)));
                                                }
                                                
                                                
                                                break;
                                            
                                            default:
                                                break;
                                        }
                                    }
                                }
                                catch (XmlPullParserException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                            
                        }
                        
                        @Override
                        public void onFailure(int arg0, Header[] arg1,
                            byte[] arg2, Throwable arg3)
                        {
                            LogUtil.i("onFailure", arg0 + "");
                            if (arg1 != null)
                            {
                                for (int i = 0; i < arg1.length; i++)
                                {
                                    LogUtil.i("onFailure", arg1[i].toString());
                                }
                            }
                            if (arg2 != null)
                            {
                                String string = new String(arg2);
                                LogUtil.i("onFailure", string);
                            }
                            
                            if (arg3 != null)
                            {
                                LogUtil.i("onFailure", arg3.toString());
                            }
                        }
                    });
                
            default:
                break;
        }
    }
    
    public boolean isServiceRun()
    {
        ActivityManager myManager =
            (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<RunningServiceInfo> runningService =
            (ArrayList<RunningServiceInfo>) myManager.getRunningServices(1000);
        for (int i = 0; i < runningService.size(); i++)
        {
            if (runningService.get(i).service.getClassName()
                .toString()
                .equals("com.huawei.toolbar.ToolbarService"))
            {
                return true;
            }
        }
        return false;
    }
}
