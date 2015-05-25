package com.tallgeorge.pi.project1.services;

import com.tallgeorge.pi.project1.domains.JpaSoundRepository;
import com.tallgeorge.pi.project1.domains.JpaTrainRepository;

public interface CalculateStatistics {
    void setJpaSoundRepository(JpaSoundRepository jpaSoundRepository);

    void setJpaTrainRepository(JpaTrainRepository jpaTrainRepository);

    void summarize();
}
