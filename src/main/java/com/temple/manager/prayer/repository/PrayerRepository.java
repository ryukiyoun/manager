package com.temple.manager.prayer.repository;

import com.temple.manager.prayer.entity.Prayer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrayerRepository extends JpaRepository<Prayer, Long> {
}
