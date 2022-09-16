package com.joskiy.arcane.repo;

import com.joskiy.arcane.models.UsersData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
@AutoConfigureDataMongo
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UsersDataRepositoryTest {

    @Autowired
    private UsersDataRepository usersDataRepository;

    @BeforeEach
    public void before() {
        usersDataRepository.deleteAll();
    }

    @Test
    public void createTest() throws Exception {
        UsersData usersData = new UsersData("Pole", "123456", "joshua@gmail.com", "Banana", "Mango", "admin");

        System.out.println(usersDataRepository.findAll());
        usersDataRepository.save(usersData);
        assertNotNull(usersData.getId());
        System.out.println(usersDataRepository.findAll());
    }

    @Test
    public void deleteTest() throws Exception {
        System.out.println(usersDataRepository.findAll());
        String id = usersDataRepository.findAll().get(0).getId();
        usersDataRepository.deleteById(id);

        assertEquals(usersDataRepository.findById(id), Optional.empty());

        System.out.println(usersDataRepository.findAll());
    }

    @Test
    public void updateTest() {
        UsersData usersData1 = new UsersData("Pole", "123456", "joshua@gmail.com", "Banana", "Mango", "admin");

        usersDataRepository.save(usersData1);
        System.out.println(usersDataRepository.findAll());

        String id = usersDataRepository.findAll().get(0).getId();
        UsersData usersData = usersDataRepository.findById(id).get();
        usersData.setId(id);
        usersData.setUsername("carrot");
        usersData.setFirst_name("Doge");
        usersData.setLast_name("Valera");
        usersData.setPassword("00000");
        usersData.setRole("dog");
        System.out.println(usersDataRepository.findAll());

        usersDataRepository.save(usersData);
        System.out.println(usersDataRepository.findAll());

        assertEquals(usersData, usersDataRepository.findById(id).get());
    }
}