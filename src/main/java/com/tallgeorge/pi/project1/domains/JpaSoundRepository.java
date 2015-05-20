package com.tallgeorge.pi.project1.domains;

import com.tallgeorge.pi.project1.models.Sound;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JpaSoundRepository extends CrudRepository<Sound, Long> {

    @Query(value = "select * from sound where description='Train' and date=?1 group by posix_time_min", nativeQuery = true)
    List<Sound> findTrains(String date);

    @Query(value = "select * from sound where description='Train' and processed=?1 order by posix_time_sec", nativeQuery = true)
    List<Sound> findByDescriptionByProcessed(Boolean processed);
}
