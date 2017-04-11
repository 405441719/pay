package com.payinxl.common.http;

public class TestPostPojo
{

    private String orderno;
    private String usercode;
    private String mode;
    public String getMode()
    {
        return mode;
    }
    public void setMode(String mode)
    {
        this.mode = mode;
    }
    private int cardno;
    public String getOrderno()
    {
        return orderno;
    }
    public void setOrderno(String orderno)
    {
        this.orderno = orderno;
    }
    public String getUsercode()
    {
        return usercode;
    }
    public void setUsercode(String usercode)
    {
        this.usercode = usercode;
    }
    public int getCardno()
    {
        return cardno;
    }
    public void setCardno(int cardno)
    {
        this.cardno = cardno;
    }
    
    @Override
    public String toString()
    {
        return usercode+" " + orderno;
    }

}
