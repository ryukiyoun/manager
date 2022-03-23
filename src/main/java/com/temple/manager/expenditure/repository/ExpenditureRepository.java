package com.temple.manager.expenditure.repository;

import com.temple.manager.expenditure.entity.Expenditure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpenditureRepository extends JpaRepository<Expenditure, Long> {
    @Query("from Expenditure e join fetch e.believer b join fetch e.code")
    List<Expenditure> findAll();

    @Query("from Expenditure e join fetch e.believer b join fetch e.code where e.believer.believerId = :believerId")
    List<Expenditure> findAllByBeliever_BelieverId(long believerId);
}
