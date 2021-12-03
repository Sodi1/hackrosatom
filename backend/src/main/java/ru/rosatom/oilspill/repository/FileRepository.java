package ru.rosatom.oilspill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rosatom.oilspill.model.entity.FileImage;

import java.util.List;

public interface FileRepository extends JpaRepository<FileImage, Long> {
    List<FileImage> findByIssueId(Long issueId);
}
