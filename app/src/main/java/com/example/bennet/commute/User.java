package com.example.bennet.commute;

public class User {
    public String names;
    public String emails;
    public Double points;

    public User(){

    }

    public User(String names, String emails, Double points){
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

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }
}
