package com.tallgeorge.pi.project1.models;

import java.math.BigInteger;
import java.sql.Date;

public class TrainCount {
    private Date date;
    private BigInteger count;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigInteger getCount() {
        return count;
    }

    public void setCount(BigInteger count) {
        this.count = count;
    }

    public TrainCount(Date date, BigInteger count) {

        this.date = date;
        this.count = count;
    }
}
