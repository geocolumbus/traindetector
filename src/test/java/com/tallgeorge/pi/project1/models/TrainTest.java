package com.tallgeorge.pi.project1.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TrainTest {

    private Logger logger = LoggerFactory.getLogger(TrainTest.class);

    @Test
    public void trainHasProperties() {
        Train train = new Train();
        train.setId("2015-05-23 14:25:15");

        Date date = new Date();
        try {
            SimpleDateFormat format =
                    new SimpleDateFormat("yyyy-MM-dd");
            date = format.parse("2015-05-23");
        } catch (ParseException pe) {
            logger.info("{}", pe);
        }

        Date timeMin = new Date();
        try {
            SimpleDateFormat format =
                    new SimpleDateFormat("hh:mm:ss");
            timeMin = format.parse("14:16:00");
        } catch (ParseException pe) {
            logger.info("{}", pe);
        }

        train.setDate(date);
        train.setTimeMin(timeMin);
        train.setPosixTimeMin(1432405703L);
        train.setMagnitude(0.3051);

        assertEquals("Train{id='null', date=Sat May 23 00:00:00 EDT 2015, timeMin=Thu Jan 01 14:16:00 EST 1970, " +
                        "magnitude=0.3051, posixTimeMin=1432405703}",
                train.toString());
    }
}
