package com.joskiy.arcane.services;

import com.joskiy.arcane.dto.UserDto;
import com.joskiy.arcane.dto.WasteDto;
import com.joskiy.arcane.models.UsersData;
import com.joskiy.arcane.models.WasteData;
import com.joskiy.arcane.repo.UsersDataRepository;
import com.joskiy.arcane.repo.WasteDataRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class MainServices {

    private WasteDataRepository wasteDataRepository;

    private UsersDataRepository usersDataRepository;

    private List<String> user = new ArrayList<>();

    private ModelMapper mapper;

    public MainServices(WasteDataRepository wasteDataRepository, UsersDataRepository usersDataRepository, ModelMapper mapper) {
        this.wasteDataRepository = wasteDataRepository;
        this.usersDataRepository = usersDataRepository;
        this.mapper = mapper;
    }


    @Autowired
    private JdbcTemplate jdbcTemplate;


    public String info(Model model) {
        Iterable<WasteData> waste_data = wasteDataRepository.findAll();
        model.addAttribute("wastes", waste_data);
        return "info";
    }

    public Model show(Optional<Integer> page, Optional<String> sortBy, Optional<String> sortType, Model model) {

        Integer rowsPerPage = 10;
        Iterable<WasteData> waste_data = wasteDataRepository.findAll();

        if (page.isEmpty()) {
            page = Optional.of(0);
        }

        if (sortType.isEmpty()) {
            sortType = Optional.of("asc");
        }

        if(sortBy.isEmpty()) {
            sortBy = Optional.of("id");
        }


        if (sortType.get().equals("asc")) {
            Sort sort = Sort.by(Sort.Direction.ASC, sortBy.get());
            Pageable pageable = PageRequest.of(page.orElse(0), rowsPerPage, sort);
            waste_data = wasteDataRepository.findAll(pageable);
        }

        if (sortType.get().equals("desc")) {
            Sort sort = Sort.by(Sort.Direction.DESC, sortBy.get());
            Pageable pageable = PageRequest.of(page.orElse(0), rowsPerPage, sort);
            waste_data = wasteDataRepository.findAll(pageable);
        }

        Iterable<WasteData> wasteData = wasteDataRepository.findAll();

        List<String> uniqueWasteType = new ArrayList<>();

        List<String> uniqueWasteAmount = new ArrayList<>();

        wasteData.forEach(wasteData1 -> {
            if (!uniqueWasteAmount.contains(wasteData1.getWasteAmount()))
                uniqueWasteAmount.add(wasteData1.getWasteAmount());
            if (!uniqueWasteType.contains(wasteData1.getWasteType()))
                uniqueWasteType.add(wasteData1.getWasteType());
        });

        model.addAttribute("wastes", waste_data);
        model.addAttribute("uniqueWasteType", uniqueWasteType);
        model.addAttribute("uniqueWasteAmount", uniqueWasteAmount);
        model.addAttribute("totalPages", getTotalPages(rowsPerPage));
        return model;
    }

    public String showUpdate(WasteDto wasteDto, String id, Model model) throws Exception {

        WasteData wasteData = mapper.map(wasteDto, WasteData.class);
        wasteDataRepository.save(wasteData);

        return "redirect:/show";
    }

    public String delete(WasteDto wasteDto, Model model) {
        wasteDataRepository.deleteById(wasteDto.getId());
        return "redirect:/show";
    }

    public List<String> unlogin(List<String> user, Model model) {

        return user;
    }

    public String addData(Model model) {
        Iterable<WasteData> waste_data = wasteDataRepository.findAll();
        model.addAttribute("wastes", waste_data);

        return "add";
    }

    public String postAddData(WasteDto wasteDto, Model model) {

        WasteData wasteData = mapper.map(wasteDto, WasteData.class);

        wasteDataRepository.save(wasteData);

        return "redirect:/add";
    }

    public List<String> userLogin(@RequestParam String username, @RequestParam String password, Model model) {
        List<UsersData> usersData = usersDataRepository.findAll();
        System.out.println(usersData);
        for (UsersData usersDatum : usersData) {
            System.out.println(usersDatum.getPassword());
            if (usersDatum.getUsername().equals(username) && usersDatum.getPassword().equals(password)) {
                user = new ArrayList<>();
                user.add(usersDatum.getUsername());
                user.add(usersDatum.getRole());
                user.add(usersDatum.getFirst_name());
                user.add(usersDatum.getLast_name());

                model.addAttribute("user", user);
                break;
            }
        }
        System.out.println(user);
        return user;
    }

    public String userReg(UserDto userDto, Model model) {
        List<UsersData> usersData = usersDataRepository.findAll();

        boolean match = false;

        for (UsersData usersDatum: usersData) {
            if (usersDatum.getUsername().equals(userDto.getUsername())) {
                match = true;
                break;
            }
        }

        if (match) {
            return "false";
        }

        userDto.setRole("inspector");
        UsersData usersData1 = mapper.map(userDto, UsersData.class);

        usersDataRepository.save(usersData1);
        return "redirect:/home";

    }

    public Integer getTotalPages(Integer rowsPerPage) {

        Integer totalRows = wasteDataRepository.findAll().size();
        System.out.println((int) Math.ceil(totalRows / (float) rowsPerPage));
        return (int) Math.ceil(totalRows / (float) rowsPerPage);
    }

}
