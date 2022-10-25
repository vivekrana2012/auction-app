package com.personnel.auction.scraper;

import com.personnel.auction.entity.PropertyFile;
import org.springframework.stereotype.Service;

@Service
public class PropertyFileService {

    private final PropertyFileRepository repository;

    public PropertyFileService(PropertyFileRepository repository) {
        this.repository = repository;
    }

    void save(PropertyFile propertyFile) {
        repository.save(propertyFile);
    }
}
