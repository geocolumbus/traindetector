package com.tallgeorge.pi.project1.scheduled;

import com.tallgeorge.pi.project1.services.CalculateStatistics;
import com.tallgeorge.pi.project1.services.LoadSounds;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * The apps scheduler interface
 */
public interface Scheduler {
    void loadSounds();

    void calculateStats();
}
