package com.joskiy.arcane.controllers;

import com.joskiy.arcane.models.UsersData;
import com.joskiy.arcane.dto.UserDto;
import com.joskiy.arcane.repo.UsersDataRepository;
import com.joskiy.arcane.services.UsersAPIServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersAPIController {

    private final UsersAPIServices usersApiServices;


    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    public @ResponseBody UsersData getById(@PathVariable("id") String id) throws Exception {
        return usersApiServices.getById(id);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('admin')")
    public @ResponseBody List<UsersData> getAll(Optional<Integer> page, Optional<String> sortBy, Optional<String> sortType) throws Exception {
        return usersApiServices.getAll(page, sortBy, sortType);
    }

    @PostMapping(value = "/delete/{id}")
    public @ResponseBody String delete(@PathVariable("id") String id) throws Exception {

        try {
            usersApiServices.delete(id);
            return "Row with index " + id + " was successfully deleted!";
        }
        catch (NullPointerException e) {
            return "Invalid id";
        }

    }

    @PutMapping(value = "/update/{id}")
    public String update(@PathVariable("id") String id, @RequestBody UserDto userDto) throws Exception {
        usersApiServices.update(id, userDto);
        return "Updated successfully";
    }

    @PutMapping(value = "/create")
    public String create(@RequestBody UserDto userDto) {
        usersApiServices.create(userDto);
        return "Created successfully";
    }
}