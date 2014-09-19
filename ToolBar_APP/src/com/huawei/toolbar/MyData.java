package com.huawei.toolbar;

public class MyData
{
    private static MyData instance;
    
    public MyData()
    {
        instance = this;
    }
    
    public static MyData getInstance()
    {
        return instance;
    }
    
    private int GPRSTotal;
    
    public int getGPRSTotal()
    {
        return GPRSTotal;
    }
    
    public void setGPRSTotal(int gPRSTotal)
    {
        GPRSTotal = gPRSTotal;
    }
    
    public int getGPTSUsed()
    {
        return GPTSUsed;
    }
    
    public void setGPTSUsed(int gPTSUsed)
    {
        GPTSUsed = gPTSUsed;
    }
    
    public int getPackageTotal()
    {
        return packageTotal;
    }
    
    public void setPackageTotal(int packageTotal)
    {
        this.packageTotal = packageTotal;
    }
    
    public int getPackageUsed()
    {
        return packageUsed;
    }
    
    public void setPackageUsed(int packageUsed)
    {
        this.packageUsed = packageUsed;
    }
    
    private int GPTSUsed;
    
    private int packageTotal;
    
    private int packageUsed;
}
