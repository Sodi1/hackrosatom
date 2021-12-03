package ru.rosatom.oilspill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.rosatom.oilspill.model.entity.Issue;
import ru.rosatom.oilspill.model.enums.IssueStatus;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {
    List<Issue> findAllByIssueStatus(IssueStatus issueStatus);

    @Query("select i from Issue i WHERE i.plantId = ?1 Order by i.createdAt ASC, i.issueStatus ASC")
    List<Issue> findByPlantIdOrderByCreatedAtAsc(@Param("plantId") Long plantId);
}
