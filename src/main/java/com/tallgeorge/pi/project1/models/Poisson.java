package com.tallgeorge.pi.project1.models;

/**
 * A data point representing the minutes between trains and the expected poisson calculated value.
 */
public class Poisson {
    private Long minutes;
    private Double poisson;

    public Poisson(Long minutes, Double poisson) {
        this.minutes = minutes;
        this.poisson = poisson;
    }

    public Long getMinutes() {
        return minutes;
    }

    public void setMinutes(Long minutes) {
        this.minutes = minutes;
    }

    public Double getPoisson() {
        return poisson;
    }

    public void setPoisson(Double poisson) {
        this.poisson = poisson;
    }
}
