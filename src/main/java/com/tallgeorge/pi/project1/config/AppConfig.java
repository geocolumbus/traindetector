package com.tallgeorge.pi.project1.config;

import net.sf.ehcache.CacheManager;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.tallgeorge.pi.project1.domains.JpaSoundRepository;
import com.tallgeorge.pi.project1.domains.JpaTrainRepository;
import com.tallgeorge.pi.project1.scheduled.Scheduler;
import com.tallgeorge.pi.project1.scheduled.SoundScheduler;
import com.tallgeorge.pi.project1.services.CalculateStatistics;
import com.tallgeorge.pi.project1.services.CalculateTrainStatistics;
import com.tallgeorge.pi.project1.services.LoadSounds;
import com.tallgeorge.pi.project1.services.LoadTrainSounds;
import org.springframework.beans.factory.annotation.Autowired;


@Configuration
@EnableCaching
public class AppConfig {

    @Autowired
    JpaSoundRepository jpaSoundRepository;

    @Autowired
    JpaTrainRepository jpaTrainRepository;

    @Bean
    CalculateStatistics getStatisticsCalculator() {
        CalculateTrainStatistics calculateTrainStatistics = new CalculateTrainStatistics();
        calculateTrainStatistics.setJpaSoundRepository(jpaSoundRepository);
        calculateTrainStatistics.setJpaTrainRepository(jpaTrainRepository);
        return calculateTrainStatistics;
    }

    @Bean
    LoadSounds getSoundLoader() {
        LoadTrainSounds loadTrainSounds = new LoadTrainSounds();
        loadTrainSounds.setJpaSoundRepository(jpaSoundRepository);
        return loadTrainSounds;
    }

    @Bean
    Scheduler getLoadScheduler() {
        SoundScheduler scheduler = new SoundScheduler();
        scheduler.setSoundLoader(getSoundLoader());
        scheduler.setStatCalculator(getStatisticsCalculator());
        return scheduler;
    }

    // Caching
    @Bean
    public EhCacheCacheManager cacheManager(CacheManager cm) {
        return new EhCacheCacheManager(cm);
    }

    @Bean
    public EhCacheManagerFactoryBean ehcache() {
        EhCacheManagerFactoryBean ehCacheFactoryBean =
                new EhCacheManagerFactoryBean();
        ehCacheFactoryBean.setConfigLocation(
                new ClassPathResource("cache/ehcache.xml"));
        return ehCacheFactoryBean;
    }
}