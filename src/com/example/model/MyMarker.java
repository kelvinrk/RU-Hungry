package com.example.model;

public class MyMarker
{
    private int m_area_id;
    private int mId;
    private String mTitle;
    private Double mLatitude;
    private Double mLongitude;

    public MyMarker(int area_id, int Id, String Title, Double Latitude, Double Longitude)
    {
        this.m_area_id = area_id;
        this.mId =Id;
        this.mTitle = Title;
        this.mLatitude = Latitude;
        this.mLongitude = Longitude;
        
    }

    public int getm_area_id()
    {
        return m_area_id;
    }

    public void setm_area_id(int m_area_id)
    {
        this.m_area_id = m_area_id;
    }

    public int getmId()
    {
        return mId;
    }

    public void setmId(int Id)
    {
        this.mId = Id;
    }

    public String getmTitle()
    {
        return mTitle;
    }

    public void setmTitle(String mTitle)
    {
        this.mTitle = mTitle;
    }

    public double getmLatitude()
    {
        return mLatitude;
    }

    public void setmLatitude(Double mLatitude)
    {
        this.mLatitude = mLatitude;
    }
    
    public double getmLongitude(){
    	return mLongitude;
    }
    
    public void setid(Double mLongitude){
    	this.mLongitude = mLongitude;
    }
    
    
}