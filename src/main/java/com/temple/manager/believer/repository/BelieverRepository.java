package com.temple.manager.believer.repository;

import com.temple.manager.believer.entity.Believer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BelieverRepository extends JpaRepository<Believer, Long> {
    List<Believer> findAllByBelieverNameContains(String name);
    Optional<Believer> findAllByBelieverNameAndBirthOfYear(String name, String birthOfYear);
}
