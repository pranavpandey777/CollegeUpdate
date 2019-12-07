package com.example.pranav.pec;

public class User {
    public String name;
    public String email;
    public String num;
    public String imageUrl;
    public String designation;

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDesignation() {

        return designation;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {

        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public User() {


    }

    public User(String name, String email, String num ,String imageUrl ,String designation) {
        this.name = name;
        this.email = email;
        this.num = num;
        this.imageUrl=imageUrl;
        this.designation=designation;
    }


}
