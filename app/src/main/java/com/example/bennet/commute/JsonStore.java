package com.example.bennet.commute;


public class JsonStore {
    private class GCWaypoint {
        String geocoder_status;
        String placed_id;
        String[] types;

        public String getGeocoder_status() {
            return this.geocoder_status;
        }

        public String getPlaced_id() {
            return this.placed_id;
        }

        public String[] getTypes() {
            return this.types;
        }

        public void setGeocoder_status(String geocoder_status) {
            this.geocoder_status = geocoder_status;
        }

        public void setPlaced_id(String placed_id) {
            this.placed_id = placed_id;
        }

        public void setTypes(String[] types) {
            this.types = types;
        }
    }

    String status;
    GCWaypoint[] geocoded_waypoints;
    Route[] routes;

    public String getStatus() {
        return this.status;
    }

    public GCWaypoint[] getGeocoded_waypoints() {
        return this.geocoded_waypoints;
    }

    public Route[] getRoutes() {
        return this.routes;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setGeocoded_waypoints(GCWaypoint[] geocoded_waypoints) {
        this.geocoded_waypoints = geocoded_waypoints;
    }

    public void setRoutes(Route[] routes) {
        this.routes = routes;
    }
}
