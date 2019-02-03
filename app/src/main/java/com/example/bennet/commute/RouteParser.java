package com.example.bennet.commute;

public class RouteParser {
    private String origin;
    private String destination;

    public boolean parse(String val, boolean isOrigin) {
        val = val.replaceAll("[,][ ]*", " ");
        val = val.replaceAll("[,]", " ");
        val = val.trim();
        val = val.replaceAll("\\s{2,}", " ");

        if (val.matches("(-|)[0-9]*(([.][0-9]*)|) (-|)[0-9]*(([.][0-9]*)|)")) {
            if (isOrigin) {
                this.origin = val.substring(0, val.indexOf(" ")) + "," + val.substring(val.indexOf(" ") + 1);
            } else {
                this.destination = val.substring(0, val.indexOf(" ")) + "," + val.substring(val.indexOf(" ") + 1);
            }
        } else if (val.matches("[0-9]*(([.][0-9]*)|)[ ]*(n|N|s|S) [0-9]*(([.][0-9]*)|)[ ]*(e|E|w|W)")) {
            if (val.contains("n") || val.contains("N")) {
                if (val.contains("e") || val.contains("E")) {
                    if (isOrigin) {
                        this.origin = val.replaceAll("[ ]*(n|N|e|E)[ ]*", ",");
                    } else {
                        this.destination = val.replaceAll("[ ]*(n|N|e|E)[ ]*", ",");
                    }
                } else {
                    val = val.replaceAll("[ ]*(n|N|w|W)[ ]*", " ");
                    String[] array = val.split(" ");
                    if (isOrigin) {
                        this.origin = array[0] + ",-" + array[1];
                    } else {
                        this.destination = array[0] + ",-" + array[1];
                    }
                }
            } else {
                if (val.contains("e") || val.contains("E")) {
                    if (isOrigin) {
                        this.origin = "-" + val.replaceAll("[ ]*(s|S|e|E)[ ]*", ",");
                    } else {
                        this.destination = "-" + val.replaceAll("[ ]*(s|S|e|E)[ ]*", ",");
                    }
                } else {
                    val = val.replaceAll("[ ]*(s|S|w|W)[ ]*", " ");
                    String[] array = val.split(" ");
                    if (isOrigin) {
                        this.origin = "-" + array[0] + ",-" + array[1];
                    } else {
                        this.destination = "-" + array[0] + ",-" + array[1];
                    }
                }
            }
        } else if (val.matches("[0-9]*([\\p{Blank}][a-zA-Z]*)*")) {
            if (isOrigin) {
                this.origin = val.replaceAll("[\\p{Blank}]", "+");
            } else {
                this.destination = val.replaceAll("[\\p{Blank}]", "+");
            }
        } else if (val.matches("([a-zA-Z]*[\\p{Blank}]*[a-zA-Z]*)*")) {
            if(isOrigin) {
                this.origin = val.replaceAll("[\\p{Blank}]", "+");
            } else {
                this.destination = val.replaceAll("[\\p{Blank}]", "+");
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