package com.temple.manager.repository;

import com.temple.manager.entity.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FamilyRepository extends JpaRepository<Family, Long> {
    @Query("from Family f join fetch f.believer b where b.believerId = :believerId")
    List<Family> findAllByBeliever_BelieverId(long believerId);
}
