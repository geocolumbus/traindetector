package com.tallgeorge.pi.project1.models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Train {
    @Id
    private String id;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(nullable = false)
    @Temporal(TemporalType.TIME)
    private Date timeMin;

    @Column(nullable = false)
    private Double magnitude;

    public Train() {
    }

    public Train(String id, Date date, Date timeMin, Double magnitude) {
        this.id = id;
        this.date = date;
        this.timeMin = timeMin;
        this.magnitude = magnitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getTimeMin() {
        return timeMin;
    }

    public void setTimeMin(Date timeMin) {
        this.timeMin = timeMin;
    }

    public Double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(Double magnitude) {
        this.magnitude = magnitude;
    }
}
