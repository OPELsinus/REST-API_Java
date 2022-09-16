package com.joskiy.arcane.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@RequiredArgsConstructor
public class WasteData {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String id;
    private String wasteType;
    private String wasteAmount;
    private String creationDate;
    private String exportDate;
    private String owner;
    private String requestCreatedDate;

    public WasteData(String waste_type, String waste_amount, String creation_date, String export_date, String owner, String request_created_date) {
        this.wasteType = waste_type;
        this.wasteAmount = waste_amount;
        this.creationDate = creation_date;
        this.exportDate = export_date;
        this.owner = owner;
        this.requestCreatedDate = request_created_date;
    }
}
