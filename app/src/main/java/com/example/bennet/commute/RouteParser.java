package com.example.bennet.commute;

public class RouteParser {
    private String origin;
    private String destination;

    public boolean parse(String val, boolean isOrigin) {
        val = val.replaceAll("[\\p{Punct}][\\p{Blank}]", " ");
        val = val.replaceAll("[\\p{Punct}]", " ");

        if(val.matches("[\\p{Blank}]*[1-9]*([\\p{Blank}]*[a-zA-Z]*)*")) {
            if(isOrigin) {
                this.origin = this.formatForUrl(val);
            } else {
                this.destination = this.formatForUrl(val);
            }

            return true;
        } else {
            return false;
        }
    }

    private String formatForUrl(String toFormat) {
        while(toFormat.charAt(0) == ' ') {
            toFormat = toFormat.substring(1);
        }

        while(toFormat.charAt(toFormat.length() - 1) == ' ') {
            toFormat = toFormat.substring(0, toFormat.length() - 1);
        }

        toFormat = toFormat.replaceAll("[\\p{Blank}]", "+");

        return toFormat;
    }

    public String getUrlPrefix() {
        return "https://maps.googleapis.com/maps/api/directions/json?origin=" + origin + "&destination=" + destination + "&key=AIzaSyCTXdNtnh6_yKnLLwHo_efKxOvRLWzxg0k";
    }
}