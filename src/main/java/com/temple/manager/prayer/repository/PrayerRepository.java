package com.temple.manager.prayer.repository;

import com.temple.manager.prayer.entity.Prayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PrayerRepository extends JpaRepository<Prayer, Long> {
    @Query("from Prayer p join fetch p.believer join fetch p.code")
    List<Prayer> findAll();

    @Query("from Prayer p join fetch p.believer join fetch p.code where p.believer.believerId = :believerId")
    List<Prayer> findAllByBeliever_BelieverId(long believerId);
}
