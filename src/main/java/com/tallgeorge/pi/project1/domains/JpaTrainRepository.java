package com.tallgeorge.pi.project1.domains;

import com.tallgeorge.pi.project1.models.Train;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JpaTrainRepository extends CrudRepository<Train, String> {

    @Query(value = "select * from train where date=?1", nativeQuery = true)
    List<Train> findAllByDate(String date);

    @Query(value = "select date, count(*) as 'total' from train where month(date)=?1 and year(date)=?2 group by date", nativeQuery = true)
    List<Object> findDailyTotals(String month, String year);

    @Query(value = "select date, count(*) as 'total' from train where year(date)=?1 group by month(date)", nativeQuery = true)
    List<Object> findMonthlyTotals(String year);

};