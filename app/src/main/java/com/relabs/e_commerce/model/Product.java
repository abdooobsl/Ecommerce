package com.relabs.e_commerce.model;

import java.util.List;

public class Product
{
    public int id;
    public String title;
    public String image;
    public String details;
    public int user_id;
    public int category_id;
    public int price;
    public int status;
    public String created_at;
    public String updated_at;
    public int rating;
    public List<Comment> comments;
    public List<Image> images;
}