package com.example.abhijeet.myapplication;

/**
 * Created by abhijeet on 24/7/16.
 */
public class Data {
    public String item,image,price;

    public Data(String item,String image,String price)
    {
        this.item=item;
        this.image=image;
        this.price=price;
    }
    public  String  getprice()
    {
        return  price;
    }
    public  String getItem()
    {
        return  item;
    }
    public String getImage()
    {
        return image;
    }
}
