package com.tallgeorge.pi.project1.models;


import java.math.BigInteger;

public class HourDistribution {
    private Integer hour;
    private BigInteger count;

    public HourDistribution(Integer hour, BigInteger count) {
        this.hour = hour;
        this.count = count;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public BigInteger getCount() {
        return count;
    }

    public void setCount(BigInteger count) {
        this.count = count;
    }
}
