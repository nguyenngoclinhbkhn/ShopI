package com.example.nguyenngoclinh.shopi.model;

public class User {
    private String id;
    private String name;
    private String userName;
    private String pass;
    private String phone;
    private String image;
    private int level;

    public User(){

    }

    public User(String id, String name, String userName, String pass, String phone, String image, int level) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.pass = pass;
        this.phone = phone;
        this.image = image;
        this.level = level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    public int getLevel(){
        return level;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
