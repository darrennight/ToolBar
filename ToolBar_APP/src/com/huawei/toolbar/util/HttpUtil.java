package com.huawei.toolbar.util;

import com.huawei.toolbar.GlobleConstants;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * [一句话功能简述]异步http处理工具类<BR>
 * [功能详细描述]
 * @author wWX191016
 * @version [RCS Client V100R001C03, 2014-9-25] 
 */
public class HttpUtil
{
    private static AsyncHttpClient mHttpClient = new AsyncHttpClient();
    
    static
    {
        // 初始化超时时间
        mHttpClient.setTimeout(GlobleConstants.Http.TIMEOUT);
    }
    
    /**
     * [一句话功能简述]使用完整url请求一个string对象<BR>
     * [功能详细描述]
     * @param urlString
     * @param res
     */
    public static void get(String urlString, AsyncHttpResponseHandler res)
    {
        
        mHttpClient.get(urlString, res);
        
    }
    
    /**
     * [一句话功能简述]<BR>
     * [功能详细描述]
     * @param urlString
     * @param params
     * @param res
     */
    public static void get(String urlString, RequestParams params,
        AsyncHttpResponseHandler res)
    {
        
        mHttpClient.get(urlString, params, res);
        
    }
}
