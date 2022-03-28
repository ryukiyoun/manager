package com.temple.manager.income.repository;

import com.temple.manager.income.dto.IncomeStatisticsDTO;
import com.temple.manager.income.entity.Income;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {
    @Query("from Income i left join fetch i.believer b join fetch i.code c")
    List<Income> findAll();

    @Query("from Income i left join fetch i.believer b join fetch i.code c where i.believer.believerId = :believerId")
    List<Income> findAllByBeliever_BelieverId(long believerId);

    @Query("from Income i left join fetch i.believer b join fetch i.code c order by i.incomeId desc")
    List<Income> findTop5ByOrderByIncomeIdDesc(Pageable limit);

    @Query(value = "CALL IncomeDailyStatistics(:date);", nativeQuery = true)
    List<IncomeStatisticsDTO> getDailyStatistics(@Param("date") LocalDate date);

    @Query(value = "CALL IncomeMonthStatistics(:date);", nativeQuery = true)
    List<IncomeStatisticsDTO> getMonthStatistics(@Param("date") LocalDate date);

    @Query(value = "CALL IncomeYearStatistics(:date);", nativeQuery = true)
    List<IncomeStatisticsDTO> getYearStatistics(@Param("date") LocalDate date);

    @Query(value = "CALL CompareToDay();", nativeQuery = true)
    List<IncomeStatisticsDTO> getCompareToDay();

    @Query(value = "CALL CompareThisMonth();", nativeQuery = true)
    List<IncomeStatisticsDTO> getCompareThisMonth();

    @Query(value = "CALL CompareThisYear();", nativeQuery = true)
    List<IncomeStatisticsDTO> getCompareThisYear();
}
