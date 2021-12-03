package ru.rosatom.oilspill.service.local.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.rosatom.oilspill.model.entity.City;
import ru.rosatom.oilspill.repository.CityRepository;
import ru.rosatom.oilspill.service.local.CityService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Cacheable("findAllCity")
    @Override
    public List<City> findAll() {
        return cityRepository.findAll();
    }
}
