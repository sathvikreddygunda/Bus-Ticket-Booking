package com.model;

public class Booking {
    private int id;

    private String passengerName;

    private String source;

    private String destination;

    private double fareAmount;

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getPassengerName(){
        return passengerName;
    }
    public void setPassengerName(String passengerName){
        this.passengerName = passengerName;
    }
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getFareAmount() {
        return fareAmount;
    }

    public void setFareAmount(double fareAmount) {
        this.fareAmount = fareAmount;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", passengerName='" + passengerName + '\'' +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", fareAmount=" + fareAmount +
                '}';
    }
}
