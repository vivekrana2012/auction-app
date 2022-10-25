package com.personnel.auction.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@Builder
public class Property {

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
