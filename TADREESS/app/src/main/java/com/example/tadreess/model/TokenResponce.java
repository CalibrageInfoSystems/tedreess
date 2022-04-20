package com.example.tadreess.model;

public class TokenResponce {
    private String ResponseCode;

    private String ResponseData;

    public String getResponseCode ()
    {
        return ResponseCode;
    }

    public void setResponseCode (String ResponseCode)
    {
        this.ResponseCode = ResponseCode;
    }

    public String getResponseData ()
    {
        return ResponseData;
    }

    public void setResponseData (String ResponseData)
    {
        this.ResponseData = ResponseData;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ResponseCode = "+ResponseCode+", ResponseData = "+ResponseData+"]";
    }
}
