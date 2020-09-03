package com.relabs.e_commerce.model;

 public class Comment {
    public int id;
    public int user_id;
    public int product_id;
    public String comment;
    public String created_at;
    public String updated_at;
    public User user;
}
