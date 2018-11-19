package com.androidproject.ya.clientapp.model.entities;


import android.location.Location;

import java.sql.Time;

/*
represents client details
 */
public class Client {
    /*
    client details
     */
    private ClientRequestStatus status;


    private Long id;
    private String name;
    private String phone;
    private String eMail;

    private Location startPoint;//location
    private Location destinationPoint;//location




    private Time startTime;
    private Time endTime;

    /*
    gets and sets
     */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClientRequestStatus getStatus() {
        return status;
    }

    public void setStatus(ClientRequestStatus status) {
        this.status = status;
    }


    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public Location getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Location startPoint) {
        this.startPoint = startPoint;
    }

    public Location getDestinationPoint() {
        return destinationPoint;
    }

    public void setDestinationPoint(Location destinationPoint) {
        this.destinationPoint = destinationPoint;
    }


}
