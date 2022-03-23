package com.temple.manager.income.repository;

import com.temple.manager.income.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {
    @Query("from Income i join fetch i.believer b join fetch i.code c")
    List<Income> findAll();

    @Query("from Income i join fetch i.believer b join fetch i.code c where i.believer.believerId = :believerId")
    List<Income> findAllByBeliever_BelieverId(long believerId);
}
