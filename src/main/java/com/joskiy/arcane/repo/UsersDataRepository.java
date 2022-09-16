package com.joskiy.arcane.repo;

import com.joskiy.arcane.models.UsersData;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Document
public interface UsersDataRepository extends MongoRepository<UsersData, String> {
    List<UsersData> findAll();
    Optional<UsersData> findByUsername(String username);

    Boolean existsByUsername(String login);

    Boolean existsByEmail(String email);
}