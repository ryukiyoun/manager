package com.temple.manager.repository;

import com.temple.manager.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {
    @Query("from Income i join fetch i.believer b join fetch i.code c")
    List<Income> findAll();
}
