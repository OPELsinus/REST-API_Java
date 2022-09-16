package com.joskiy.arcane.services;

import com.joskiy.arcane.dto.WasteDto;
import com.joskiy.arcane.models.WasteData;
import com.joskiy.arcane.repo.WasteDataRepository;
import com.joskiy.arcane.repo.WasteRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class WasteAPIServices {

    @Autowired
    private WasteDataRepository wasteDataRepository;

    private ModelMapper mapper = new ModelMapper();

    public WasteAPIServices(WasteDataRepository wasteDataRepository) {
        this.wasteDataRepository = wasteDataRepository;
    }

    public List<WasteData> getAll(Optional<Integer> page, Optional<String> sortBy, Optional<String> sortType){

        int rowsPerPage = wasteDataRepository.findAll().size();
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

        List<WasteData> wasteData = new ArrayList<>();
        waste_data.forEach(wasteData::add);

        return wasteData;
    }

    public WasteData getById(String id) throws Exception {
        if (wasteDataRepository.findById(id).isPresent())
            return wasteDataRepository.findById(id).get();
        else
            throw new Exception("Error");
    }

    public void delete(String id) {
        wasteDataRepository.deleteById(id);
    }

    public void update(String id, WasteDto wasteDto) throws Exception {
        wasteDto.setId(id);
        WasteData wasteData = mapper.map(wasteDto, WasteData.class);
        wasteDataRepository.save(wasteData);
    }

    public String create(WasteDto wasteDto) {
        WasteData wasteData = mapper.map(wasteDto, WasteData.class);
        wasteDataRepository.save(wasteData);

        return "Created successfully";
    }
}
