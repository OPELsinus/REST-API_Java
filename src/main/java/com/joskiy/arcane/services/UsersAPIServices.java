package com.joskiy.arcane.services;

import com.joskiy.arcane.models.UsersData;
import com.joskiy.arcane.repo.UsersDataRepository;
import com.joskiy.arcane.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class UsersAPIServices {

    @Autowired
    private UsersDataRepository usersDataRepository;

    @Autowired
    private ModelMapper mapper = new ModelMapper();

    public UsersAPIServices(UsersDataRepository usersDataRepository) {
        this.usersDataRepository = usersDataRepository;
    }

    public UsersAPIServices(ModelMapper mapper) {
        this.mapper = mapper;
    }


    public List<UsersData> getAll(Optional<Integer> page, Optional<String> sortBy, Optional<String> sortType) throws Exception {

        int rowsPerPage = 5;
        Iterable<UsersData> usersData = usersDataRepository.findAll();

        Integer page1 = 0;

        if (page.isEmpty()) {
            page1 = 0;
        }
        else page1 = page.get();

        if (sortType.isEmpty()) {
            sortType = Optional.of("asc");
        }

        if(sortBy.isEmpty()) {
            sortBy = Optional.of("id");
        }

        if (sortType.get().equals("asc")) {
            Sort sort = Sort.by(Sort.Direction.ASC, sortBy.get());
            Pageable pageable = PageRequest.of(page1, rowsPerPage, sort);
            usersData = usersDataRepository.findAll(pageable);
        }

        if (sortType.get().equals("desc")) {
            Sort sort = Sort.by(Sort.Direction.DESC, sortBy.get());
            Pageable pageable = PageRequest.of(page.orElse(0), rowsPerPage, sort);
            usersData = usersDataRepository.findAll(pageable);
        }

        List<UsersData> usersData1 = new ArrayList<>();
        if (usersData != null) usersData.forEach(usersData1::add);

        return usersData1;
    }

    public UsersData getById(String id) throws Exception {
        if (usersDataRepository.findById(id).isPresent())
            return usersDataRepository.findById(id).get();

        else return new UsersData();
    }

    public HttpStatus create(UserDto userDto) {
        UsersData usersData = mapper.map(userDto, UsersData.class);
        usersDataRepository.save(usersData);
        return HttpStatus.CREATED;
    }

    public HttpStatus delete(String id) throws Exception {

        if (usersDataRepository.findById(id).isEmpty()) return HttpStatus.NO_CONTENT;

        usersDataRepository.deleteById(id);

        return HttpStatus.NO_CONTENT;
    }

    public HttpStatus update(String id, UserDto userDto) throws Exception {
        userDto.setId(id);
        UsersData usersData = mapper.map(userDto, UsersData.class);
        usersDataRepository.save(usersData);

        return HttpStatus.OK;
    }
}