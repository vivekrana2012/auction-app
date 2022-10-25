package com.personnel.auction.pdf;

import com.personnel.auction.entity.Property;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {

    private final PropertyRepository repository;

    public PropertyService(PropertyRepository repository) {
        this.repository = repository;
    }

    void saveAll(List<Property> properties) {
        repository.saveAll(properties);
    }
}
