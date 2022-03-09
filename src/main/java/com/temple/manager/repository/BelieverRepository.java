package com.temple.manager.repository;

import com.temple.manager.entity.Believer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BelieverRepository extends JpaRepository<Believer, Long> {
    List<Believer> findAllByActive(String active);

    List<Believer> findAllByActiveAndBelieverNameContains(String active, String name);
}
