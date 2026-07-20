package com.sekvojak.trailplanner.repository;

import com.sekvojak.trailplanner.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
