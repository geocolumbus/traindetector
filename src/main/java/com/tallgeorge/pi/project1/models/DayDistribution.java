package com.tallgeorge.pi.project1.models;

import java.math.BigInteger;

/**
 * Created by georgecampbell on 5/26/15.
 */
public class DayDistribution {
    private String day;
    private BigInteger count;

    public DayDistribution(String day, BigInteger count) {
        this.day = day;
        this.count = count;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public BigInteger getCount() {
        return count;
    }

    public void setCount(BigInteger count) {
        this.count = count;
    }
}
