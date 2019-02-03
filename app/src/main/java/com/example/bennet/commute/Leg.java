package com.example.bennet.commute;

import org.w3c.dom.Text;

public class Leg {
    TextVal distance;
    TextVal duration;
    TextVal duration_in_traffic;
    Coordinate end_location;
    Coordinate start_location;
    String end_address;
    String start_address;
    Step[] steps;

    public void setDistance(TextVal distance) {
        this.distance = distance;
    }

    public void setDuration(TextVal duration) {
        this.duration = duration;
    }

    public void setDuration_in_traffic(TextVal duration_in_traffic) {
        this.duration_in_traffic = duration_in_traffic;
    }

    public void setEnd_location(Coordinate end_location) {
        this.end_location = end_location;
    }

    public void setStart_location(Coordinate start_location) {
        this.start_location = start_location;
    }

    public void setEnd_address(String end_address) {
        this.end_address = end_address;
    }

    public void setStart_address(String start_address) {
        this.start_address = start_address;
    }

    public void setSteps(Step[] steps) {
        this.steps = steps;
    }

    public TextVal getDistance() {
        return this.distance;
    }

    public TextVal getDuration() {
        return this.duration;
    }

    public TextVal getDuration_in_traffic() {
        return this.duration_in_traffic;
    }

    public Coordinate getEnd_location() {
        return this.end_location;
    }

    public Coordinate getStart_location() {
        return this.start_location;
    }

    public String getEnd_address() {
        return this.end_address;
    }

    public String getStart_address() {
        return this.start_address;
    }

    public Step[] getSteps() {
        return this.steps;
    }
}
