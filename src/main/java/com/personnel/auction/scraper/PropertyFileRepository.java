package com.personnel.auction.scraper;

import com.personnel.auction.entity.PropertyFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyFileRepository extends JpaRepository<PropertyFile, Long> {
}
