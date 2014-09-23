package com.huawei.toolbar;


public class RandomCountUtil
{
    public RandomCountUtil()
    {
        
    }
    
    public static MyData setCount()
    {
        MyData data = new MyData();
        
        data.setGPRSTotal((int) (Math.random() * 500));
        data.setPackageTotal((int) (Math.random() * 500));
        data.setGPTSUsed((int) (Math.random() * data.getGPRSTotal()));
        data.setPackageUsed((int) (Math.random() * data.getPackageTotal()));
        return data;
    }
}
