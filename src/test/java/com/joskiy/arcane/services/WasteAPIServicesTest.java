package com.joskiy.arcane.services;

import com.joskiy.arcane.dto.WasteDto;
import com.joskiy.arcane.models.UsersData;
import com.joskiy.arcane.models.WasteData;
import com.joskiy.arcane.repo.WasteDataRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;
import java.util.Optional;

@DataMongoTest
class WasteAPIServicesTest {

    @Autowired
    private WasteDataRepository wasteDataRepository;

    private WasteAPIServices wasteAPIServices;

    private ModelMapper mapper = new ModelMapper();

    @BeforeEach
    public void before() {
        wasteDataRepository.deleteAll();
        wasteAPIServices = new WasteAPIServices(wasteDataRepository);
    }


    @Test
    void getAll() {
        for (int i = 0; i < 10; i++) {
            WasteData wasteData = new WasteData("TestGetById" + String.valueOf(i), "TestGetById" + String.valueOf(i), "1999-01-01", "1999-01-01", "admin", "1999-01-01 00:00:00");
            wasteDataRepository.save(wasteData);
        }

        List<WasteData> allWasteData = wasteAPIServices.getAll(Optional.empty(), Optional.empty(), Optional.empty());

        Assertions.assertEquals(wasteDataRepository.findAll(), allWasteData);
    }

    @Test
    void getById() throws Exception {

        WasteData wasteData = new WasteData("TestGetById", "TestGetById", "1999-01-01", "1999-01-01", "admin", "1999-01-01 00:00:00");
        wasteDataRepository.save(wasteData);

        String id = wasteDataRepository.findAll().get(0).getId();

        Assertions.assertEquals(wasteData, wasteAPIServices.getById(id));

    }

    @Test
    void delete() {
        WasteData wasteData = new WasteData("TestGetById", "TestGetById", "1999-01-01", "1999-01-01", "admin", "1999-01-01 00:00:00");
        wasteDataRepository.save(wasteData);
        String id = wasteDataRepository.findAll().get(0).getId();

        // Для наглядного примера
        // Thread.sleep(4000);

        wasteAPIServices.delete(id);

        Optional<WasteData> wasteData1 = wasteDataRepository.findById(id);
        Assertions.assertEquals(wasteData1, Optional.empty());
    }

    @Test
    void update() throws Exception {
        WasteData wasteData = new WasteData("TestUpdate", "TestUpdate", "1999-01-01", "1999-01-01", "ponchik", "2000-01-01 00:00:00");
        wasteDataRepository.save(wasteData);
        wasteData.setWasteType("TestUpdateEdit");
        wasteData.setWasteAmount("TestUpdateEdit");
        wasteAPIServices.update(wasteData.getId(), mapper.map(wasteData, WasteDto.class));

        Assertions.assertEquals(wasteDataRepository.findById(wasteData.getId()).get(), wasteData);
    }

    @Test
    void create() {
        WasteData wasteData = new WasteData("TestCreate", "TestCreate", "1999-01-01", "1999-01-01", "ponchik", "2000-01-01 00:00:00");

        wasteAPIServices.create(mapper.map(wasteData, WasteDto.class));

        Assertions.assertEquals(wasteDataRepository.findAll().size(), 1);
    }
}