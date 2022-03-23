package com.temple.manager.code.repository;

import com.temple.manager.code.entity.Code;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CodeRepository extends JpaRepository<Code, Long> {
    List<Code> findAllByParentCodeValue(String parentValue);
}
