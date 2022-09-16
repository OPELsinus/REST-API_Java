package com.joskiy.arcane.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@RequiredArgsConstructor
public class WasteDto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String id;
    private String wasteType;
    private String wasteAmount;
    private String creationDate;
    private String exportDate;
    private String owner;
    private String requestCreatedDate;
}
