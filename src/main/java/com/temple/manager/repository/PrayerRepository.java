package com.temple.manager.repository;

import com.temple.manager.entity.Prayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PrayerRepository extends JpaRepository<Prayer, Long> {
    @Query("from Prayer p join fetch p.believer join fetch p.code")
    List<Prayer> findAll();
}
