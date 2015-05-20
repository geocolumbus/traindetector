package com.tallgeorge.pi.project1.services;

import com.tallgeorge.pi.project1.domains.JpaSoundRepository;
import com.tallgeorge.pi.project1.domains.JpaTrainRepository;
import com.tallgeorge.pi.project1.models.Sound;
import com.tallgeorge.pi.project1.models.Train;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Component
public class LoadSounds {

    private final Logger logger = LoggerFactory.getLogger(LoadSounds.class);

    @Autowired
    private JpaSoundRepository jpaSoundRepository;

    @Autowired
    private JpaTrainRepository jpaTrainRepository;

    public LoadSounds() {
    }

    /**
     * Load the raw files into the sound table
     */
    @Scheduled(cron = "45 */5 * * * *")
    public void load() {
        Path dir = Paths.get("/var/sound");
        FileSystem fileSystem = FileSystems.getDefault();
        PathMatcher pathMatcher = fileSystem.getPathMatcher("glob:**/*.dat");

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path file : stream) {
                if (pathMatcher.matches(file)) {
                    try (BufferedReader br = new BufferedReader(new FileReader(file.toFile()))) {
                        Long seconds = Long.parseLong(file.getFileName().toString().replaceAll("[0-9]*-([^\\.]*).dat", "$1"));
                        Long minuteStamp = Long.parseLong(file.getFileName().toString().replaceAll("([0-9]*)-[^\\.]*.dat", "$1"));

                        Sound sound = new Sound();

                        sound.setPosixTimeSec(minuteStamp + seconds);
                        sound.setPosixTimeMin(minuteStamp);
                        sound.setDate(new Date(1000L * (minuteStamp + seconds)));
                        sound.setTime(new Time(1000L * (minuteStamp + seconds)));
                        sound.setTimeMin(new Time(1000L * minuteStamp));

                        sound.setMagnitude(Double.parseDouble(br.readLine()));
                        sound.setDescription(br.readLine());
                        sound.setBin0100(Double.parseDouble(br.readLine()));
                        sound.setBin0200(Double.parseDouble(br.readLine()));
                        sound.setBin0300(Double.parseDouble(br.readLine()));
                        sound.setBin0400(Double.parseDouble(br.readLine()));
                        sound.setBin0500(Double.parseDouble(br.readLine()));
                        sound.setBin0600(Double.parseDouble(br.readLine()));
                        sound.setBin0700(Double.parseDouble(br.readLine()));
                        sound.setBin0800(Double.parseDouble(br.readLine()));
                        sound.setBin0900(Double.parseDouble(br.readLine()));
                        sound.setBin1000(Double.parseDouble(br.readLine()));
                        sound.setBin1100(Double.parseDouble(br.readLine()));
                        sound.setBin1200(Double.parseDouble(br.readLine()));
                        sound.setProcessed(false);

                        jpaSoundRepository.save(sound);
                        try {
                            Files.delete(file);
                        } catch (IOException e) {
                            logger.warn("Unable to delete {}", file.toString());
                        }
                    } catch (IOException e) {
                        logger.warn("Unable to parse .dat file");
                    }
                }
            }
        } catch (IOException e) {
            logger.warn("Unable to list directory.");
        }
    }

    /**
     * Summarize the train data from the sound table into the train table.
     */
    @Scheduled(cron = "45 */6 * * * *")
    public void summarize() {

        // Select the sounds that have not been processed
        List<Sound> sounds = jpaSoundRepository.findByDescriptionByProcessed(false);

        Long lastPosixTimeMin = null;
        for (Sound sound : sounds) {
            if (lastPosixTimeMin == null || sound.getPosixTimeMin() - lastPosixTimeMin > 180L) {
                String id = sound.getDate().toString() + " " + sound.getTimeMin().toString();
                if (!jpaTrainRepository.exists(id)) {
                    jpaTrainRepository.save(new Train(id, sound.getDate(), sound.getTimeMin(), sound.getMagnitude()));
                }
                lastPosixTimeMin = sound.getPosixTimeMin();
            }
            if (lastPosixTimeMin == null) {
                lastPosixTimeMin = sound.getPosixTimeMin();
            }

            // Mark the sound as having been processed
            sound.setProcessed(true);
            jpaSoundRepository.save(sound);
        }
    }
}
