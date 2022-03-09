package com.temple.manager.repository;

import com.temple.manager.entity.Code;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CodeRepository extends JpaRepository<Code, Long> {
    List<Code> findAllByParentCodeValue(String parentValue);
}
