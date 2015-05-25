package com.tallgeorge.pi.project1.controllers;

import com.tallgeorge.pi.project1.domains.JpaSoundRepository;
import com.tallgeorge.pi.project1.domains.JpaTrainRepository;
import com.tallgeorge.pi.project1.models.Poisson;
import com.tallgeorge.pi.project1.models.Train;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static java.lang.Math.*;
import static org.apache.commons.math3.util.CombinatoricsUtils.factorial;

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

    @RequestMapping(value = "/sound/trains/days", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Object> getLastNDays(@RequestParam(value = "days") Long days) {
        List<Object> list = jpaTrainRepository.findLastDays(days);
        Collections.reverse(list);
        return list;
    }

    @RequestMapping(value = "sound/trains/daydist", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Object> getDayOfWeekDistribution() {
        return jpaTrainRepository.findDayOfWeekDistribution();
    }

    @RequestMapping(value = "sound/trains/hourdist", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Object> getHourOfDayDistribution() {
        return jpaTrainRepository.findHourOfDayDistribution();
    }

    @RequestMapping(value = "sound/trains/minutesbetweentrains", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Poisson> getTimeBetweenTrains() {
        List<Poisson> poissons = new ArrayList<>();
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

        // Calculate the poisson curve
        //double lambda = 12;
        //double scale = 1;
        for (int k = 0; k < 180; k++) {
            Double poissonCalculation = 0.0;
            /*
            if (k < 21) {
                poissonCalculation = scale * exp(-lambda) * pow(k, lambda) / factorial(k);
            }
            */
            poissons.add(new Poisson(minutesBetweenTrains.get((long) k), poissonCalculation));
        }
        return poissons;
    }

    @RequestMapping(value = "/sound/trains/months", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Object> getLastNMonths(@RequestParam(value = "months") Long months) {
        List<Object> list = jpaTrainRepository.findLastMonths(months);
        Collections.reverse(list);
        return list;
    }

    @RequestMapping(value = "/sound/trains/bymonth", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Object> getTrainsByMonth(@RequestParam(value = "month") String month, @RequestParam(value = "year") String year) {
        return jpaTrainRepository.findDailyTotals(month, year);
    }

    @RequestMapping(value = "/sound/trains/byyear", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Object> getTrainsByMonth(@RequestParam(value = "year") String year) {
        return jpaTrainRepository.findMonthlyTotals(year);
    }

}
