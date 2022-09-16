package com.joskiy.arcane.controllers;

import com.joskiy.arcane.dto.WasteDto;
import com.joskiy.arcane.models.WasteData;
import com.joskiy.arcane.repo.WasteDataRepository;
import com.joskiy.arcane.services.WasteAPIServices;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/wastes")
@RequiredArgsConstructor
public class WasteAPIController {

    private final WasteDataRepository wasteDataRepository;

    private final WasteAPIServices wasteApiServices;


    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    public @ResponseBody WasteData getById(@PathVariable("id") String id) throws Exception {
        return wasteApiServices.getById(id);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public @ResponseBody List<WasteData> getAll(Optional<Integer> page, Optional<String> sortBy, Optional<String> sortType) {
        return wasteApiServices.getAll(page, sortBy, sortType);
    }

    @PostMapping(value = "/delete/{id}")
    public @ResponseBody String delete(@PathVariable("id") String id) throws Exception {
        wasteApiServices.delete(id);

        return "Row with index " + id + " was successfully deleted!";
    }

    @PutMapping(value = "/update/{id}")
    public String update(@PathVariable("id") String id, @RequestBody WasteDto wasteDto) throws Exception {
        wasteApiServices.update(id, wasteDto);
        return "Updated successfully";
    }

    @PutMapping(value = "/create")
    public String create(@RequestBody WasteDto wasteDto) {
        wasteApiServices.create(wasteDto);
        return "Saved successfully";
    }
}