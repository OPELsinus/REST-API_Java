package com.joskiy.arcane.repo;

import com.joskiy.arcane.dto.WasteDto;
import com.joskiy.arcane.models.WasteData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface WasteRepository extends MongoRepository<WasteData, String> {
    List<WasteData> findAll();
}
