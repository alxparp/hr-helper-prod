package com.hrsol.helper.repository;

import com.hrsol.helper.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}
