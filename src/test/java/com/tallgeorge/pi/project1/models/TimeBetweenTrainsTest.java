package com.tallgeorge.pi.project1.models;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TimeBetweenTrainsTest {

    @Test
    public void timeBetweenTrainsHasProperties() {
        TimeBetweenTrains timeBetweenTrains = new TimeBetweenTrains();
        timeBetweenTrains.setMinutes(7L);

        assertEquals("TimeBetweenTrains{minutes=7}", timeBetweenTrains.toString());

        TimeBetweenTrains timeBetweenTrains1 = new TimeBetweenTrains(9L);

        assertEquals("TimeBetweenTrains{minutes=9}",timeBetweenTrains1.toString());
    }

}
