package com.tallgeorge.pi.project1.models;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;


public class SoundTest {

    Logger logger = LoggerFactory.getLogger(SoundTest.class);

    @Test
    public void soundHasProperties() {
        Sound sound = new Sound();
        sound.setPosixTimeSec(12345L);
        sound.setPosixTimeMin(123456L);

        Date date = new Date();
        try {
            SimpleDateFormat format =
                    new SimpleDateFormat("yyyy-MM-dd");
            date = format.parse("2015-05-23");
        } catch (ParseException pe) {
            logger.info("{}", pe);
        }

        Date time = new Date();
        try {
            SimpleDateFormat format =
                    new SimpleDateFormat("hh:mm:ss");
            time = format.parse("14:16:15");
        } catch (ParseException pe) {
            logger.info("{}", pe);
        }

        Date timeMin = new Date();
        try {
            SimpleDateFormat format =
                    new SimpleDateFormat("hh:mm:ss");
            timeMin = format.parse("14:16:15");
        } catch (ParseException pe) {
            logger.info("{}", pe);
        }

        sound.setId(1L);
        sound.setDate(date);
        sound.setTime(time);
        sound.setTimeMin(timeMin);
        sound.setDescription("Train");
        sound.setMagnitude(0.02);
        sound.setBin0100(0.1);
        sound.setBin0200(0.15);
        sound.setBin0300(0.22);
        sound.setBin0400(0.25);
        sound.setBin0500(0.3);
        sound.setBin0600(0.35);
        sound.setBin0700(0.4);
        sound.setBin0800(0.45);
        sound.setBin0900(0.5);
        sound.setBin1000(0.55);
        sound.setBin1100(0.6);
        sound.setBin1200(0.65);
        sound.setProcessed(true);
        assertEquals("Sound{Id=1, posixTimeSec=12345, posixTimeMin=123456, " +
                        "date=Sat May 23 00:00:00 EDT 2015, time=Thu Jan 01 14:16:15 EST 1970, " +
                        "timeMin=Thu Jan 01 14:16:15 EST 1970, description='Train', magnitude=0.02, bin0100=0.1, " +
                        "bin0200=0.15, bin0300=0.22, bin0400=0.25, bin0500=0.3, bin0600=0.35, bin0700=0.4, bin0800=0.45, " +
                        "bin0900=0.5, bin1000=0.55, bin1100=0.6, bin1200=0.65, processed=true}",
                sound.toString());
    }
}
