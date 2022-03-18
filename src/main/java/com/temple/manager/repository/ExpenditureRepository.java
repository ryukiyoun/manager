package com.temple.manager.repository;

import com.temple.manager.entity.Expenditure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpenditureRepository extends JpaRepository<Expenditure, Long> {
    @Query("from Expenditure e join fetch e.believer b join fetch e.code")
    List<Expenditure> findAll();
}
