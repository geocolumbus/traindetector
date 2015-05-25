package com.tallgeorge.pi.project1.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
public class Sound {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @Column
    private Long posixTimeSec;

    @Column
    private Long posixTimeMin;

    @Column
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column
    @Temporal(TemporalType.TIME)
    private Date time;

    @Column
    @Temporal(TemporalType.TIME)
    private Date timeMin;

    @Column
    private String description;

    @Column
    private Double magnitude;

    @Column
    private Double bin0100;

    @Column
    private Double bin0200;

    @Column
    private Double bin0300;

    @Column
    private Double bin0400;

    @Column
    private Double bin0500;

    @Column
    private Double bin0600;

    @Column
    private Double bin0700;

    @Column
    private Double bin0800;

    @Column
    private Double bin0900;

    @Column
    private Double bin1000;

    @Column
    private Double bin1100;

    @Column
    private Double bin1200;

    @Column()
    private Boolean processed;

    public Sound() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Long getPosixTimeSec() {
        return posixTimeSec;
    }

    public void setPosixTimeSec(Long posixTimeSec) {
        this.posixTimeSec = posixTimeSec;
    }

    public Long getPosixTimeMin() {
        return posixTimeMin;
    }

    public void setPosixTimeMin(Long posixTimeMin) {
        this.posixTimeMin = posixTimeMin;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getTimeMin() {
        return timeMin;
    }

    public void setTimeMin(Date timeMin) {
        this.timeMin = timeMin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(Double magnitude) {
        this.magnitude = magnitude;
    }

    public Double getBin0100() {
        return bin0100;
    }

    public void setBin0100(Double bin0100) {
        this.bin0100 = bin0100;
    }

    public Double getBin0200() {
        return bin0200;
    }

    public void setBin0200(Double bin0200) {
        this.bin0200 = bin0200;
    }

    public Double getBin0300() {
        return bin0300;
    }

    public void setBin0300(Double bin0300) {
        this.bin0300 = bin0300;
    }

    public Double getBin0400() {
        return bin0400;
    }

    public void setBin0400(Double bin0400) {
        this.bin0400 = bin0400;
    }

    public Double getBin0500() {
        return bin0500;
    }

    public void setBin0500(Double bin0500) {
        this.bin0500 = bin0500;
    }

    public Double getBin0600() {
        return bin0600;
    }

    public void setBin0600(Double bin0600) {
        this.bin0600 = bin0600;
    }

    public Double getBin0700() {
        return bin0700;
    }

    public void setBin0700(Double bin0700) {
        this.bin0700 = bin0700;
    }

    public Double getBin0800() {
        return bin0800;
    }

    public void setBin0800(Double bin0800) {
        this.bin0800 = bin0800;
    }

    public Double getBin0900() {
        return bin0900;
    }

    public void setBin0900(Double bin0900) {
        this.bin0900 = bin0900;
    }

    public Double getBin1000() {
        return bin1000;
    }

    public void setBin1000(Double bin1000) {
        this.bin1000 = bin1000;
    }

    public Double getBin1100() {
        return bin1100;
    }

    public void setBin1100(Double bin1100) {
        this.bin1100 = bin1100;
    }

    public Double getBin1200() {
        return bin1200;
    }

    public void setBin1200(Double bin1200) {
        this.bin1200 = bin1200;
    }

    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    @Override
    public String toString() {
        return "Sound{" +
                "Id=" + Id +
                ", posixTimeSec=" + posixTimeSec +
                ", posixTimeMin=" + posixTimeMin +
                ", date=" + date +
                ", time=" + time +
                ", timeMin=" + timeMin +
                ", description='" + description + '\'' +
                ", magnitude=" + magnitude +
                ", bin0100=" + bin0100 +
                ", bin0200=" + bin0200 +
                ", bin0300=" + bin0300 +
                ", bin0400=" + bin0400 +
                ", bin0500=" + bin0500 +
                ", bin0600=" + bin0600 +
                ", bin0700=" + bin0700 +
                ", bin0800=" + bin0800 +
                ", bin0900=" + bin0900 +
                ", bin1000=" + bin1000 +
                ", bin1100=" + bin1100 +
                ", bin1200=" + bin1200 +
                ", processed=" + processed +
                '}';
    }
}
