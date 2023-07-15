package com.hrsol.helper.repository;

import com.hrsol.helper.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
