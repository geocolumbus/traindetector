package com.tallgeorge.pi.project1.services;

import com.tallgeorge.pi.project1.domains.JpaSoundRepository;

public interface LoadSounds {
    void setJpaSoundRepository(JpaSoundRepository jpaSoundRepository);

    void load();
}
