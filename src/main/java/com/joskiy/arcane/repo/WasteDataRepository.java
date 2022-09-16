package com.joskiy.arcane.repo;

import com.joskiy.arcane.dto.WasteDto;
import com.joskiy.arcane.models.WasteData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Document
public interface WasteDataRepository extends MongoRepository<WasteData, String> {
    Page<WasteData> findAll(Pageable pageable);
}