package ru.rosatom.oilspill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rosatom.oilspill.model.entity.City;

public interface CityRepository extends JpaRepository<City, Long> {
}
