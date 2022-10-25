package com.personnel.auction.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDate;
import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@Builder
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String serialNumber;
    private String estate;
    private String plotNumber;
    private String categoryAndSize;
    private String area;
    private String numberOfPlots;
    private String far;
    private String reservePrice;
    private String emd;
    private LocalDateTime timestamp;
    private String filename;
    private LocalDate auctionDate;
    private int priceByArea;
}
