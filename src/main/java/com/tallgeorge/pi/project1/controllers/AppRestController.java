package com.tallgeorge.pi.project1.controllers;

import com.tallgeorge.pi.project1.domains.JpaSoundRepository;
import com.tallgeorge.pi.project1.domains.JpaTrainRepository;
import com.tallgeorge.pi.project1.models.Sound;
import com.tallgeorge.pi.project1.models.Train;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "/sound/trains/bymonth", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Object> getTrainsByMonth(@RequestParam(value = "month") String month, @RequestParam(value = "year") String year) {
        return jpaTrainRepository.findDailyTotals(month, year);
    }

    @RequestMapping(value = "/sound/trains/byyear", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Object> getTrainsByMonth(@RequestParam(value = "year") String year) {
        return jpaTrainRepository.findMonthlyTotals(year);
    }

    @RequestMapping(value = "/sounds", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Sound> getSounds() {
        return jpaSoundRepository.findAll();
    }

    @RequestMapping(value = "/sound/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Sound getSound(@RequestParam(value = "id") Long id) {
        return jpaSoundRepository.findOne(id);
    }
}
