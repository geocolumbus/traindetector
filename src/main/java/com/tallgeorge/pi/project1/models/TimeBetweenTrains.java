package com.tallgeorge.pi.project1.models;

/**
 * A data point representing the minutes between trains and the expected poisson calculated value.
 */
public class TimeBetweenTrains {
    private Long minutes;

    public TimeBetweenTrains() {
    }

    public TimeBetweenTrains(Long minutes) {
        this.minutes = minutes;
    }

    public Long getMinutes() {
        return minutes;
    }

    public void setMinutes(Long minutes) {
        this.minutes = minutes;
    }

    @Override
    public String toString() {
        return "TimeBetweenTrains{" +
                "minutes=" + minutes +
                '}';
    }
}
