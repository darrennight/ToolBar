package com.huawei.toolbar;

/**
 * 全局常量
 * @author wWX191016
 *
 */
public class GlobleConstants
{
    /**
     * 窗口类型
     * @author wWX191016
     *
     */
    public interface WindowType
    {
        int BASIC = 0x10000;
        
        /**
         * 迷你悬浮窗
         */
        int WINDOW_MINI = BASIC + 1;
        
        /**
         * 悬浮窗主页
         */
        int WINDOW_MAIN = BASIC + 2;
        
        /**
         * 商店页面
         */
        int WINDOW_SHOP = BASIC + 3;
    }
    
    /**
     * 操作类型
     * @author wWX191016
     *
     */
    public interface OprationType
    {
        
        int BASIC = 0x20000;
        
        /**
         * 返回
         */
        int WINDOW_BACK = BASIC + 1;
        
        /**
         * 关闭
         */
        int WINDOW_CLOSE = BASIC + 2;
        
        /**
         * 关闭所有窗口
         */
        int WINDOW_CLOSEALL = BASIC + 3;
        
        /**
         * 刷新窗口
         */
        int WINDOW_REFRESH = BASIC + 4;
    }
}
