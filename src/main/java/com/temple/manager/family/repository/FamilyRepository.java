package com.temple.manager.family.repository;

import com.temple.manager.family.entity.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FamilyRepository extends JpaRepository<Family, Long> {
    @Query("from Family f inner join Believer b on b.believerId = f.believerId where f.believerId = :believerId")
    List<Family> findAllByBeliever_BelieverId(long believerId);

    long deleteByBelieverId(long believerId);
}
