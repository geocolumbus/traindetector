package com.tallgeorge.pi.project1.scheduled;

import com.tallgeorge.pi.project1.services.CalculateStatistics;
import com.tallgeorge.pi.project1.services.LoadSounds;
import org.springframework.scheduling.annotation.Scheduled;

public class SoundScheduler implements Scheduler {

    private LoadSounds soundLoader;

    private CalculateStatistics statCalculator;

    public void setSoundLoader(LoadSounds soundLoader) {
        this.soundLoader = soundLoader;
    }

    public void setStatCalculator(CalculateStatistics statCalculator) {
        this.statCalculator = statCalculator;
    }

    @Override
    @Scheduled(cron = "30 */4 * * * *")
    public void loadSounds() {
        soundLoader.load();
    }

    @Override
    @Scheduled(cron = "30 */5 * * * *")
    public void calculateStats() {
        statCalculator.summarize();
    }
}
