package com.joskiy.arcane.repo;

import com.joskiy.arcane.models.WasteData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.AutoConfigureDataJdbc;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@AutoConfigureDataMongo
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class WasteDataRepositoryTest {
    @Autowired
    private WasteDataRepository wasteDataRepository;

    @BeforeEach
    public void before() {
        wasteDataRepository.deleteAll();
    }


    @Test
    public void createTest() throws Exception {
        WasteData wasteData = new WasteData("Test", "Test", "2022-09-08", "2022-09-11", "mangust", "2022-09-08");
        //System.out.println(wasteDataRepository.findAll());

        wasteDataRepository.save(wasteData);
        assertNotNull(wasteData.getId());
        //System.out.println(wasteDataRepository.findAll());
    }

    @Test
    public void deleteTest() throws Exception {
        WasteData wasteData = new WasteData("Test", "Test", "2022-09-08", "2022-09-11", "mangust", "2022-09-08");

        System.out.println(wasteDataRepository.findAll());
        wasteDataRepository.save(wasteData);
        System.out.println(wasteDataRepository.findAll());
        wasteDataRepository.deleteById(wasteData.getId());
        System.out.println(wasteDataRepository.findAll());
        assertEquals(wasteDataRepository.findById(wasteData.getId()), Optional.empty());
    }

    @Test
    public void updateTest() {
        WasteData wasteData1 = new WasteData("Test", "Test", "2022-09-08", "2022-09-11", "mangust", "2022-09-08");

        wasteDataRepository.save(wasteData1);

        WasteData wasteData = new WasteData();

        wasteData.setId(wasteData1.getId());
        wasteData.setWasteType("TestType");
        wasteData.setWasteAmount("TestAmount");
        wasteData.setCreationDate("9999-01-01");
        wasteData.setExportDate("9999-01-01");
        wasteData.setOwner("TestOwner");
        wasteData.setRequestCreatedDate("1111-11-11 00:00:00");

        wasteDataRepository.save(wasteData);

        assertEquals(wasteData, wasteDataRepository.findById(wasteData1.getId()).get());
    }
}