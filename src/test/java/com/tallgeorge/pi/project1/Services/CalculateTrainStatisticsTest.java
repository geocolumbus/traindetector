package com.tallgeorge.pi.project1.services;

import com.tallgeorge.pi.project1.domains.JpaSoundRepository;
import com.tallgeorge.pi.project1.domains.JpaTrainRepository;
import com.tallgeorge.pi.project1.models.Sound;
import com.tallgeorge.pi.project1.models.Train;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.sql.Time;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;

public class CalculateTrainStatisticsTest {

    private JpaSoundRepository mockSoundRepo;

    private JpaTrainRepository mockTrainRepo;

    @Before
    public void setUp() {
        // Set up sound repo

        mockSoundRepo = mock(JpaSoundRepository.class);
        List<Sound> sounds = new ArrayList<>();

        Date date = new Date(1432599315000L);
        Time time = new Time(1432599315000L);
        Time timeMin = new Time(1432599300000L);

        Date date1 = new Date(1432599315000L+180*1000);
        Time time1 = new Time(1432599315000L+180*1000);
        Time timeMin1 = new Time(1432599300000L+180*1000);

        Date date2 = new Date(1432599315000L+181*1000);
        Time time2 = new Time(1432599315000L+181*1000);
        Time timeMin2 = new Time(1432599300000L+181*1000);

        Sound sound = new Sound();
        sound.setId(1L);
        sound.setPosixTimeSec(1432599315L);
        sound.setPosixTimeMin(1432599300L);
        sound.setDate(date);
        sound.setTime(time);
        sound.setTimeMin(timeMin);
        sound.setDescription("Train");
        sound.setMagnitude(0.02);
        sound.setProcessed(false);
        sounds.add(sound);

        Sound sound1 = new Sound();
        sound1.setId(2L);
        sound1.setPosixTimeSec(1432599316L+180);
        sound1.setPosixTimeMin(1432599300L+180);
        sound1.setDate(date1);
        sound1.setTime(time1);
        sound1.setTimeMin(timeMin1);
        sound1.setDescription("Train");
        sound1.setMagnitude(0.03);
        sound1.setProcessed(false);
        sounds.add(sound1);

        Sound sound2 = new Sound();
        sound2.setId(3L);
        sound2.setPosixTimeSec(1432599316L+181);
        sound2.setPosixTimeMin(1432599300L+181);
        sound2.setDate(date2);
        sound2.setTime(time2);
        sound2.setTimeMin(timeMin2);
        sound2.setDescription("Train");
        sound2.setMagnitude(0.04);
        sound2.setProcessed(false);
        sounds.add(sound2);

        when(mockSoundRepo.findByDescriptionByProcessed(false)).thenReturn(sounds);

        // Set up train repo
        mockTrainRepo = mock(JpaTrainRepository.class);
    }

    /**
     * Three sound records at time=0, 180 and 300 seconds apart. The first should be processed, the
     * second ignored because it is too close to the first, and the third should be processed.
     */
    @Test
    public void shouldProcessASoundRecordIntoATrainRecord() {
        CalculateTrainStatistics calculateTrainStatistics = new CalculateTrainStatistics();
        calculateTrainStatistics.setJpaSoundRepository(mockSoundRepo);
        calculateTrainStatistics.setJpaTrainRepository(mockTrainRepo);
        calculateTrainStatistics.summarize();
        ArgumentCaptor<Train> trainCaptor = ArgumentCaptor.forClass(Train.class);
        verify(mockTrainRepo,times(2)).save(trainCaptor.capture());

        // The sound record was processed into a train record
        assertEquals("Train{id='2015-05-25 20:15:00', date=2015-05-25, timeMin=20:15:00, " +
                "magnitude=0.02, posixTimeMin=1432599300}", trainCaptor.getAllValues().get(0).toString());

        assertEquals("Train{id='2015-05-25 20:18:01', date=2015-05-25, timeMin=20:18:01, " +
                "magnitude=0.04, posixTimeMin=1432599481}", trainCaptor.getAllValues().get(1).toString());
    }
}
