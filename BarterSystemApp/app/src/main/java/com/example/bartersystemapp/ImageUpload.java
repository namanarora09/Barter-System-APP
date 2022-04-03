package com.example.bartersystemapp;

import java.util.Random;

public class ImageUpload {
    private String mImageUrl;

    public ImageUpload()
    {
        //empty constructor needed
    }

    public ImageUpload(String name,String imageUrl)
    {
        mImageUrl=imageUrl;
    }

    public String getImageUrl()
    {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        mImageUrl=imageUrl;
    }
}
