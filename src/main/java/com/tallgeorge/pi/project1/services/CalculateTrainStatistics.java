package com.tallgeorge.pi.project1.services;

import com.tallgeorge.pi.project1.domains.JpaSoundRepository;
import com.tallgeorge.pi.project1.domains.JpaTrainRepository;
import com.tallgeorge.pi.project1.models.Sound;
import com.tallgeorge.pi.project1.models.Train;

import java.util.List;

public class CalculateTrainStatistics implements CalculateStatistics {

    private JpaSoundRepository jpaSoundRepository;

    private JpaTrainRepository jpaTrainRepository;

    @Override
    public void setJpaSoundRepository(JpaSoundRepository jpaSoundRepository) {
        this.jpaSoundRepository = jpaSoundRepository;
    }

    @Override
    public void setJpaTrainRepository(JpaTrainRepository jpaTrainRepository) {
        this.jpaTrainRepository = jpaTrainRepository;
    }

    public CalculateTrainStatistics() {
    }

    /**
     * Summarize the train data from the sound table into the train table.
     */
    @Override
    public void summarize() {

        // Select the sounds that have not been processed
        List<Sound> sounds = jpaSoundRepository.findByDescriptionByProcessed(false);

        Long lastPosixTimeMin = null;
        for (Sound sound : sounds) {
            if (lastPosixTimeMin == null || sound.getPosixTimeMin() - lastPosixTimeMin > 180L) {
                String id = sound.getDate().toString() + " " + sound.getTimeMin().toString();
                if (sound.getProcessed()==false && !jpaTrainRepository.exists(id)) {
                    jpaTrainRepository.save(
                            new Train(
                                    id,
                                    sound.getDate(),
                                    sound.getTimeMin(),
                                    sound.getMagnitude(),
                                    sound.getPosixTimeMin()
                            )
                    );
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
