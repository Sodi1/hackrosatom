package ru.rosatom.oilspill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.rosatom.oilspill.model.entity.Issue;
import ru.rosatom.oilspill.model.enums.IssueStatus;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {
    List<Issue> findAllByIssueStatus(IssueStatus issueStatus);

    @Query("select i from Issue i Order by i.createdAt ASC, i.issueStatus ASC")
    List<Issue> findByPlantIdOrderByCreatedAtAsc(Long plantId);
}
