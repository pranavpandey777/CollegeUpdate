package com.example.pranav.pec;

public class Update {
    String Title,Body;


    public Update() {

    }

    public Update(String title, String body) {
        Title = title;
        Body = body;
    }

    public String getTitle() {

        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }
}
