package ru.rosatom.oilspill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rosatom.oilspill.model.entity.Spill;

public interface SpillRepository extends JpaRepository<Spill, Long> {
}
