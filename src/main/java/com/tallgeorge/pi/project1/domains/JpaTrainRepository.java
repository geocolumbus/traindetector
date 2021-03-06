package com.tallgeorge.pi.project1.domains;

import com.tallgeorge.pi.project1.models.Train;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JpaTrainRepository extends CrudRepository<Train, String> {

    @Query(value = "select * from train where date=?1", nativeQuery = true)
    List<Train> findAllByDate(String date);

    @Query(value = "select date, count(*) from train group by date order by date desc limit ?1", nativeQuery = true)
    List<Object> findLastDays(Long days);

    @Query(value = "select date, count(*) from train group by month(date) order by month(date) desc limit ?1", nativeQuery = true)
    List<Object> findLastMonths(Long months);

    @Query(value = "select dayname(date), count(*) from train group by dayname(date) order by dayname(date)", nativeQuery = true)
    List<Object> findDayOfWeekDistribution();

    @Query(value = "select hour(time_min), count(*) from train group by hour(time_min) order by hour(time_min)", nativeQuery = true)
    List<Object> findHourOfDayDistribution();
}