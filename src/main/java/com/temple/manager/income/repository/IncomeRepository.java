package com.temple.manager.income.repository;

import com.temple.manager.income.dto.IncomeDailyStatisticsDTO;
import com.temple.manager.income.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {
    @Query("from Income i join fetch i.believer b join fetch i.code c")
    List<Income> findAll();

    @Query("from Income i join fetch i.believer b join fetch i.code c where i.believer.believerId = :believerId")
    List<Income> findAllByBeliever_BelieverId(long believerId);

    @Query(value = "CALL IncomeDailyStatistics(:date);", nativeQuery = true)
    List<IncomeDailyStatisticsDTO> getDailyStatistics(@Param("date") LocalDate date);
}
