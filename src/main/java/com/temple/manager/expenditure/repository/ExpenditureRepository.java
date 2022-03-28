package com.temple.manager.expenditure.repository;

import com.temple.manager.expenditure.dto.ExpenditureStatisticsDTO;
import com.temple.manager.expenditure.entity.Expenditure;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ExpenditureRepository extends JpaRepository<Expenditure, Long> {
    @Query("from Expenditure e left join fetch e.believer b join fetch e.code")
    List<Expenditure> findAll();

    @Query("from Expenditure e left join fetch e.believer b join fetch e.code where e.believer.believerId = :believerId")
    List<Expenditure> findAllByBeliever_BelieverId(long believerId);

    @Query("from Expenditure e left join fetch e.believer b join fetch e.code order by e.expenditureId desc")
    List<Expenditure> findTop5ByOrderByExpenditureIdDesc(Pageable limit);

    @Query(value = "CALL ExpenditureDailyStatistics(:date);", nativeQuery = true)
    List<ExpenditureStatisticsDTO> getDailyStatistics(@Param("date") LocalDate date);

    @Query(value = "CALL ExpenditureMonthStatistics(:date);", nativeQuery = true)
    List<ExpenditureStatisticsDTO> getMonthStatistics(@Param("date") LocalDate date);

    @Query(value = "CALL ExpenditureYearStatistics(:date);", nativeQuery = true)
    List<ExpenditureStatisticsDTO> getYearStatistics(@Param("date") LocalDate date);
}
