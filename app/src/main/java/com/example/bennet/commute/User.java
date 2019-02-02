package com.example.bennet.commute;

public class User {

    public String emails;
    public int points;

    public User(String emails, int points){
        this.emails=emails;
        this.points=points;

    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
