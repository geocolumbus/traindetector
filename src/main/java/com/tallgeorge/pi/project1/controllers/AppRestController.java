package com.tallgeorge.pi.project1.controllers;

import com.tallgeorge.pi.project1.domains.JpaSoundRepository;
import com.tallgeorge.pi.project1.domains.JpaTrainRepository;
import com.tallgeorge.pi.project1.models.DayDistribution;
import com.tallgeorge.pi.project1.models.HourDistribution;
import com.tallgeorge.pi.project1.models.TrainCount;
import com.tallgeorge.pi.project1.models.TimeBetweenTrains;
import com.tallgeorge.pi.project1.models.Train;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@RestController
public class AppRestController {

    private final Logger logger = LoggerFactory.getLogger(AppRestController.class);

    @Autowired
    JpaSoundRepository jpaSoundRepository;

    @Autowired
    JpaTrainRepository jpaTrainRepository;

    @RequestMapping(value = "/sound/trains", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Train> getTrains(@RequestParam(value = "date") String date) {
        return jpaTrainRepository.findAllByDate(date);
    }

    @Cacheable(value = "getLastNDays")
    @RequestMapping(value = "/sound/trains/days", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TrainCount> getLastNDays(@RequestParam(value = "days") Long days) {
        logger.info("**** Executing /sound/trains/days?days={}", days);
        List<Object> queryResults = jpaTrainRepository.findLastDays(days);
        Collections.reverse(queryResults);
        List<TrainCount> dayCount = new ArrayList<>();
        for (Object queryResult : queryResults) {
            Object[] obj = (Object[]) queryResult;
            dayCount.add(new TrainCount((Date) obj[0], (BigInteger) obj[1]));
        }
        return dayCount;
    }

    @Cacheable(value = "getLastNMonths")
    @RequestMapping(value = "/sound/trains/months", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TrainCount> getLastNMonths(@RequestParam(value = "months") Long months) {
        logger.info("**** Executing /sound/trains/months?months={}", months);
        List<Object> queryResults = jpaTrainRepository.findLastMonths(months);
        Collections.reverse(queryResults);
        List<TrainCount> monthCount = new ArrayList<>();
        for (Object queryResult : queryResults) {
            Object[] obj = (Object[]) queryResult;
            monthCount.add(new TrainCount((Date) obj[0], (BigInteger) obj[1]));
        }
        return monthCount;
    }

    @Cacheable(value = "getDayOfWeekDistribution")
    @RequestMapping(value = "sound/trains/daydist", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DayDistribution> getDayOfWeekDistribution() {
        logger.info("**** sound/trains/daydist");
        List<Object> objects = jpaTrainRepository.findDayOfWeekDistribution();
        List<DayDistribution> dayDistributions = new ArrayList<>();
        for (Object object : objects) {
            Object[] obj = (Object[]) object;
            dayDistributions.add(new DayDistribution((String) obj[0], (BigInteger) obj[1]));
        }
        return dayDistributions;
    }

    @Cacheable(value = "getHourOfDayDistribution")
    @RequestMapping(value = "sound/trains/hourdist", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<HourDistribution> getHourOfDayDistribution() {
        logger.info("**** sound/trains/hourdist");
        List<Object> objects = jpaTrainRepository.findHourOfDayDistribution();
        List<HourDistribution> hourDistributions = new ArrayList<>();
        for (Object object : objects) {
            Object[] obj = (Object[]) object;
            hourDistributions.add(new HourDistribution((Integer) obj[0], (BigInteger) obj[1]));
        }
        return hourDistributions;
    }

    @Cacheable(value = "getTimeBetweenTrains")
    @RequestMapping(value = "sound/trains/minutesbetweentrains", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TimeBetweenTrains> getTimeBetweenTrains() {
        logger.info("**** sound/trains/minutesbetweentrains");
        List<TimeBetweenTrains> timeBetweenTrainses = new ArrayList<>();
        Iterable<Train> trains = jpaTrainRepository.findAll();

        // Calculate all the minutes between trains and put them in a hash map
        HashMap<Long, Long> minutesBetweenTrains = new HashMap<>();
        for (Long i = 0L; i < 180L; i++) {
            minutesBetweenTrains.put(i, 0L);
        }

        Train last = null;

        for (Train train : trains) {
            if (last != null) {
                Long diff = (train.getPosixTimeMin() - last.getPosixTimeMin()) / 60L;
                if (minutesBetweenTrains.containsKey(diff)) {
                    minutesBetweenTrains.put(diff, minutesBetweenTrains.get(diff) + 1L);
                } else {
                    minutesBetweenTrains.put(diff, 1L);
                }
            }
            last = train;
        }

        for (int k = 0; k < 180; k++) {
            timeBetweenTrainses.add(new TimeBetweenTrains(minutesBetweenTrains.get((long) k)));
        }
        return timeBetweenTrainses;
    }
}
