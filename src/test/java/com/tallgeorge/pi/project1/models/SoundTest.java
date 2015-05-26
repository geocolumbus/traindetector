package com.tallgeorge.pi.project1.models;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;


public class SoundTest {

    private final Logger logger = LoggerFactory.getLogger(SoundTest.class);

    @Test
    public void soundHasProperties() {
        Sound sound = new Sound();

        sound.setId(1L);
        sound.setPosixTimeSec(1432599315L);
        sound.setPosixTimeMin(1432599300L);
        sound.setDate(new Date(1432599315000L));
        sound.setTime(new Time(1432599315000L));
        sound.setTimeMin(new Time(1432599300000L));
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
        assertEquals("Sound{Id=1, posixTimeSec=1432599315, posixTimeMin=1432599300, " +
                        "date=Mon May 25 20:15:15 EDT 2015, time=20:15:15, timeMin=20:15:00, " +
                        "description='Train', magnitude=0.02, bin0100=0.1, bin0200=0.15, bin0300=0.22, " +
                        "bin0400=0.25, bin0500=0.3, bin0600=0.35, bin0700=0.4, bin0800=0.45, bin0900=0.5, " +
                        "bin1000=0.55, bin1100=0.6, bin1200=0.65, processed=true}",
                sound.toString());
    }
}
