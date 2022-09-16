package com.joskiy.arcane.services;

import com.joskiy.arcane.dto.UserDto;
import com.joskiy.arcane.dto.WasteDto;
import com.joskiy.arcane.models.UsersData;
import com.joskiy.arcane.repo.UsersDataRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DataMongoTest
class UsersAPIServicesTest {

    @Mock
    private UsersDataRepository usersDataRepository;

    @Autowired
    private ModelMapper mapper = new ModelMapper();

    @InjectMocks
    private UsersAPIServices usersAPIServices = new UsersAPIServices(usersDataRepository, mapper);

    @BeforeEach
    public void before() {
    }

    @Test
    void getAll() throws Exception {

        List<UsersData> allUsersData = usersAPIServices.getAll(Optional.empty(), Optional.empty(), Optional.empty());

        Assertions.assertEquals(usersDataRepository.findAll(), allUsersData);
    }

    @Test
    void getById() throws Exception {
        String id = "id";

        Assertions.assertEquals(new UsersData(), usersAPIServices.getById(id));
    }

    @Test
    void delete() throws Exception {
        String id = "id";
        UsersData usersData = new UsersData(id, "TestDelete", "TestDelete", "joshua@gmail.com", "TestDelete", "TestDelete", "admin");

        Assertions.assertEquals(usersAPIServices.delete(id), HttpStatus.NO_CONTENT);

    }

    @Test
    void update() throws Exception {
        UsersData usersData = new UsersData("TestUpdate", "TestUpdate", "joshua@gmail.com", "TestUpdate", "TestUpdate", "admin");


        Assertions.assertEquals(usersAPIServices.update(usersData.getId(), mapper.map(usersData, UserDto.class)), HttpStatus.OK);
    }

    @Test
    void create() {
        UsersData usersData = new UsersData("TestCreate2", "TestCreate2", "joshua@gmail.com", "TestCreate", "TestCreate", "admin");

        // given(usersAPIServices.create(mapper.map(usersData, UserDto.class))).willReturn(HttpStatus.ACCEPTED);

        Assertions.assertEquals(usersAPIServices.create(mapper.map(usersData, UserDto.class)), HttpStatus.CREATED);
    }
}