package com.example.bennet.commute;

public class User {
    public String names;
    public String emails;
    public int points;

    public User(String names, String emails, int points){
        this.names = names;
        this.emails=emails;
        this.points=points;

    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
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
