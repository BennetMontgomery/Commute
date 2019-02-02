package com.example.bennet.commute;

public class RouteParser {
    private String origin;
    private String destination;

    public boolean parse(String val, boolean isOrigin) {
        val = val.replaceAll("[,][ ]*", " ");
        val = val.replaceAll("[,]", " ");
        val = val.trim();
        val = val.replaceAll("\\s{2,}", " ");

        if(val.matches("(-|)[0-9]*(([.][0-9]*)|) (-|)[0-9]*(([.][0-9]*)|)")) {
            if (isOrigin) {
                this.origin = "latlong " + val;
            } else {
                this.destination = "latlong " + val;
            }
        } else if(val.matches("[0-9]*(([.][0-9]*)|)[ ]*(n|N|s|S) [0-9]*(([.][0-9]*)|)[ ]*(e|E|w|W)")){
            if(val.contains("n") || val.contains("N")) {
                if(val.contains("e") || val.contains("E")) {
                    if(isOrigin) {
                        this.origin = "latlong " + val.replaceAll("[ ]*(n|N|e|E)[ ]*", " ");
                    } else {
                        this.destination = "latlong " + val.replaceAll("[ ]*(n|N|e|E)[ ]*", " ");
                    }
                } else {
                    val = val.replaceAll("[ ]*(n|N|w|W)[ ]*", " ");
                    String[] array = val.split(" ");
                    if(isOrigin) {
                        this.origin = "latlong " + array[0] + " -" + array[1];
                    } else {
                        this.destination = "latlong " + array[0] + " -" + array[1];
                    }
                }
            } else {
                if(val.contains("e") || val.contains("E")) {
                    if(isOrigin) {
                        this.origin = "latlong -" + val.replaceAll("[ ]*(s|S|e|E)[ ]*", " ");
                    } else {
                        this.destination = "latlong -" + val.replaceAll("[ ]*(s|S|e|E)[ ]*", " ");
                    }
                } else {
                    val = val.replaceAll("[ ]*(s|S|w|W)[ ]*", " ");
                    String[] array = val.split(" ");
                    if(isOrigin) {
                        this.origin = "latlong -" + array[0] + " -" + array[1];
                    } else {
                        this.destination = "latlong -" + array[0] + " -" + array[1];
                    }
                }
            }
        } else if (val.matches("[0-9]*([\\p{Blank}][a-zA-Z]*)*")) {
            if (isOrigin) {
                this.origin = "address " + val;
            } else {
                this.destination = "address " + val;
            }
        } else {
            return false;
        }

        return true;
    }

    public String getOrigin() {
        return this.origin;
    }

    public String getDestination() {
        return this.destination;
    }
}