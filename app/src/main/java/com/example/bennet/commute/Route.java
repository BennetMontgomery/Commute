package com.example.bennet.commute;

public class Route {
    private class Bounds {
        Coordinate northeast;
        Coordinate southeast;

        public void setNortheast(Coordinate northeast) {
            this.northeast = northeast;
        }

        public void setSoutheast(Coordinate southeast) {
            this.southeast = southeast;
        }

        public Coordinate getNortheast() {
            return this.northeast;
        }

        public Coordinate getSoutheast() {
            return this.southeast;
        }
    }

    Bounds bounds;
    String copyrights;
    Leg[] legs;
    OverviewPolyline overviewPolyline;
    String summary;
    String warning;
    String waypoint_order;

    public void setLegs(Leg[] legs) {
        this.legs = legs;
    }

    public void setCopyrights(String copyrights) {
        this.copyrights = copyrights;
    }

    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    public void setOverviewPolyline(OverviewPolyline overviewPolyline) {
        this.overviewPolyline = overviewPolyline;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public void setWaypoint_order(String waypoint_order) {
        this.waypoint_order = waypoint_order;
    }

    public OverviewPolyline getOverviewPolyline() {
        return overviewPolyline;
    }

    public Leg[] getLegs() {
        return legs;
    }

    public Bounds getBounds() {
        return bounds;
    }

    public String getCopyrights() {
        return copyrights;
    }

    public String getWarning() {
        return warning;
    }

    public String getWaypoint_order() {
        return waypoint_order;
    }

    public String getSummary() {
        return summary;
    }
}
