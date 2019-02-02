package com.example.bennet.commute;

public class RouteParser {
    String origin;
    String destination;

    public boolean parse(String val, boolean isOrigin) {
        val.replaceAll("[\\p{Punct}][\\p{Blank}]", " ");
        val.replaceAll("[\\p{Punct}]", " ");

        if(val.matches("[\\p{Blank}]*[1-9]*([\\p{Blank}]*[a-zA-Z]*)*")) {
            if(isOrigin) {
                this.origin = val;
            } else {
                this.destination = val;
            }

            return true;
        } else {
            return false;
        }
    }

    public String getUrlPrefix() {
        return "https://maps.googleapis.com/maps/api/directions/json?origin=" + origin + "&destination=" + destination + "&key=AIzaSyCTXdNtnh6_yKnLLwHo_efKxOvRLWzxg0k";
    }
}