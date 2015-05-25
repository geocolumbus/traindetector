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

    @Column(nullable = false)
    private Long posixTimeMin;

    public Train() {
    }

    public Train(String id, Date date, Date timeMin, Double magnitude, Long posixTimeMin) {
        this.id = id;
        this.date = date;
        this.timeMin = timeMin;
        this.magnitude = magnitude;
        this.posixTimeMin = posixTimeMin;
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

    public Long getPosixTimeMin() {
        return posixTimeMin;
    }

    public void setPosixTimeMin(Long posixTimeMin) {
        this.posixTimeMin = posixTimeMin;
    }

    @Override
    public String toString() {
        return "Train{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", timeMin=" + timeMin +
                ", magnitude=" + magnitude +
                ", posixTimeMin=" + posixTimeMin +
                '}';
    }
}
