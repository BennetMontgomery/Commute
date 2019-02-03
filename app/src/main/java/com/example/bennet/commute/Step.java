package com.example.bennet.commute;

import org.w3c.dom.Text;

public class Step {
    TextVal distance;
    TextVal duration;
    Coordinate end_location;
    String html_instructions;
    OverviewPolyline polyline;
    Coordinate start_location;
    String travel_mode;

    public TextVal getDistance() {
        return distance;
    }

    public TextVal getDuration() {
        return duration;
    }

    public Coordinate getEnd_location() {
        return end_location;
    }

    public String getHtml_instructions() {
        return html_instructions;
    }

    public OverviewPolyline getPolyline() {
        return polyline;
    }

    public Coordinate getStart_location() {
        return start_location;
    }

    public String getTravel_mode() {
        return travel_mode;
    }

    public void setDistance(TextVal distance) {
        this.distance = distance;
    }

    public void setDuration(TextVal duration) {
        this.duration = duration;
    }

    public void setEnd_location(Coordinate end_location) {
        this.end_location = end_location;
    }

    public void setHtml_instructions(String html_instructions) {
        this.html_instructions = html_instructions;
    }

    public void setPolyline(OverviewPolyline polyline) {
        this.polyline = polyline;
    }

    public void setStart_location(Coordinate start_location) {
        this.start_location = start_location;
    }

    public void setTravel_mode(String travel_mode) {
        this.travel_mode = travel_mode;
    }
}
